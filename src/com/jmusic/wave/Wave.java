/*
 * Copyright (C) 2011 Jacquet Wong
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.jmusic.wave;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

import com.jmusic.Segment;
import com.jmusic.wave.extension.NormalizedSampleAmplitudes;
import com.musicg.fingerprint.FingerprintManager;
import com.musicg.fingerprint.FingerprintSimilarity;
import com.musicg.fingerprint.FingerprintSimilarityComputer;

/**
 * Read WAVE headers and data from wave input stream
 * 
 * @author Jacquet Wong
 */
public class Wave implements Serializable{

	private static final long serialVersionUID = 1L;
	private WaveHeader waveHeader;
	private byte[] data;	// little endian
	private byte[] fingerprint;

	/**
	 * Constructor
	 * 
	 * @param filename Wave file
	 * 
	 */
	public Wave(String filename) {
		try {
			InputStream inputStream = new FileInputStream(filename);
			initWaveWithInputStream(inputStream);
			inputStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Constructor
	 * 
	 * @param inputStream Wave file input stream
	 */
	public Wave(InputStream inputStream) {
		initWaveWithInputStream(inputStream);
	}

	/**
	 * Constructor
	 * 
	 * @param WaveHeader waveHeader
	 * @param byte[] data
	 */
	public Wave(WaveHeader waveHeader, byte[] data) {
		this.waveHeader = waveHeader;
		this.data = data;
	}
	
	private void initWaveWithInputStream(InputStream inputStream) {
		// reads the first 44 bytes for header
		waveHeader = new WaveHeader(inputStream);

		if (waveHeader.isValid()) {
			// load data
			try {
				data = new byte[inputStream.available()];
				inputStream.read(data);
			} catch (IOException e) {
				e.printStackTrace();
			}
			// end load data
		} else {
			System.err.println("Invalid Wave Header");
		}
	}
	
	/*
	 * @param segmentSize: size of one Segment
	 * @param overlapFactor: overlap fraction
	 * @param windowType: default- Hamming window
	 * 
	 * @return Array of segments
	 */
	public Segment[] getSegments(int segmentSize, int overlapFactor, int windowType){
		
		double[] amplitudes= getAmplitudes();
		int numSamples = amplitudes.length;
		
		int pointer=0;
		
		// overlapping
		if (overlapFactor>1){
			int numOverlappedSamples=numSamples*overlapFactor;
			int backSamples=segmentSize*(overlapFactor-1)/overlapFactor;
			int fftSampleSize_1=segmentSize-1;
			double[] overlapAmp= new double[numOverlappedSamples];

			for (int i=0; i<amplitudes.length; i++){
				overlapAmp[pointer++]=amplitudes[i];
				if (pointer%segmentSize==fftSampleSize_1){
					// overlap
					i-=backSamples;
				}
			}
			numSamples=numOverlappedSamples;
			amplitudes=overlapAmp;
		}
		// end overlapping
			
		int numFrames=numSamples/segmentSize;	
			
		// Segmentation of Amplitude Data 
		
		Segment[] segmentArray = new Segment[numFrames];
		
		for(int f=0; f<numFrames; f++) {
			double [] signals=new double[segmentSize];
			int startSample = f*segmentSize;
			for (int n=0; n<segmentSize; n++){
				signals[n]=amplitudes[startSample+n];							
			}
			segmentArray[f] = new Segment(signals, getWaveHeader().getSampleRate(), 
					overlapFactor, windowType);
		}
		
		return segmentArray;
	}

	/**
	 * Trim the wave data
	 * 
	 * @param leftTrimNumberOfSample
	 *            Number of sample trimmed from beginning
	 * @param rightTrimNumberOfSample
	 *            Number of sample trimmed from ending
	 */
	public void trim(int leftTrimNumberOfSample, int rightTrimNumberOfSample) {

		long chunkSize = waveHeader.getChunkSize();
		long subChunk2Size = waveHeader.getSubChunk2Size();

		long totalTrimmed = leftTrimNumberOfSample + rightTrimNumberOfSample;

		if (totalTrimmed > subChunk2Size) {
			leftTrimNumberOfSample = (int) subChunk2Size;
		}

		// update wav info
		chunkSize -= totalTrimmed;
		subChunk2Size -= totalTrimmed;
		
		if (chunkSize>=0 && subChunk2Size>=0){
			waveHeader.setChunkSize(chunkSize);
			waveHeader.setSubChunk2Size(subChunk2Size);
	
			byte[] trimmedData = new byte[(int) subChunk2Size];
			System.arraycopy(data, (int) leftTrimNumberOfSample, trimmedData, 0,
					(int) subChunk2Size);
			data = trimmedData;
		}
		else{
			System.err.println("Trim error: Negative length");
		}
	}

	/**
	 * Trim the wave data
	 * 
	 * @param leftTrimSecond
	 *            Seconds trimmed from beginning
	 * @param rightTrimSecond
	 *            Seconds trimmed from ending
	 */
	public void trim(double leftTrimSecond, double rightTrimSecond) {

		int sampleRate = waveHeader.getSampleRate();
		int bitsPerSample = waveHeader.getBitsPerSample();
		int channels = waveHeader.getChannels();

		int leftTrimNumberOfSample = (int) (sampleRate * bitsPerSample / 8
				* channels * leftTrimSecond);
		int rightTrimNumberOfSample = (int) (sampleRate * bitsPerSample / 8
				* channels * rightTrimSecond);

		trim(leftTrimNumberOfSample, rightTrimNumberOfSample);
	}

	/**
	 * Get the wave header
	 * 
	 * @return waveHeader
	 */
	public WaveHeader getWaveHeader() {
		return waveHeader;
	}
	
	
	/**
	 * Get the wave data in bytes
	 * 
	 * @return wave data
	 */
	public byte[] getBytes() {
		return data;
	}

	/**
	 * Data byte size of the wave excluding header size
	 * 
	 * @return byte size of the wave
	 */
	public int size() {
		return data.length;
	}
	
	/**
	 * Length of the wave in second
	 * 
	 * @return length in second
	 */
	public float length() {
		return (float) waveHeader.getSubChunk2Size() / waveHeader.getByteRate();
	}

	/**
	 * Timestamp of the wave length
	 * 
	 * @return timestamp
	 */
	public String timestamp() {
		float totalSeconds = this.length();
		float second = totalSeconds % 60;
		int minute = (int) totalSeconds / 60 % 60;
		int hour = (int) (totalSeconds / 3600);

		StringBuffer sb = new StringBuffer();
		if (hour > 0) {
			sb.append(hour + ":");
		}
		if (minute > 0) {
			sb.append(minute + ":");
		}
		sb.append(second);

		return sb.toString();
	}

	/**
	 * Get the amplitudes of the wave samples (depends on the header)
	 * 
	 * @return amplitudes array (signed 16-bit)
	 */
	public double[] getAmplitudes(){
		int bytePerSample = waveHeader.getBitsPerSample() / 8;
		int numSamples = data.length / bytePerSample;
		double[] amplitudes = new double[numSamples];
		
		int pointer = 0;
		for (int i = 0; i < numSamples; i++) {
			short amplitude = 0;
			for (int byteNumber = 0; byteNumber < bytePerSample; byteNumber++) {
				// little endian
				amplitude |= (short) ((data[pointer++] & 0xFF) << (byteNumber * 8));
			}
			amplitudes[i] = amplitude;
		}
		
		return amplitudes;
	}
	
	public String toString(){
		StringBuffer sb=new StringBuffer(waveHeader.toString());
		sb.append("\n");
		sb.append("length: " + timestamp());
		return sb.toString();
	}

	public double[] getNormalizedAmplitudes() {
		NormalizedSampleAmplitudes amplitudes=new NormalizedSampleAmplitudes(this);
		return amplitudes.getNormalizedAmplitudes();
	}
	
	public byte[] getFingerprint(){		
		if (fingerprint==null){
			FingerprintManager fingerprintManager=new FingerprintManager();
			fingerprint=fingerprintManager.extractFingerprint(this);
		}
		return fingerprint;
	}
	
	public FingerprintSimilarity getFingerprintSimilarity(Wave wave){		
		FingerprintSimilarityComputer fingerprintSimilarityComputer=new FingerprintSimilarityComputer(this.getFingerprint(),wave.getFingerprint());
		return fingerprintSimilarityComputer.getFingerprintsSimilarity();
	}
}