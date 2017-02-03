package com.jmusic.utils;

import com.jmusic.segment.Complex;
import com.jmusic.segment.Segment;
import com.jmusic.wave.Wave;

/**
 * Class containing helper functions
 * 
 * @author Deepankar Agrawal,Anshul Shah
 *
 */
public class Utils {
	
	public static double[] complexToAbs(Complex[] frequencies){
		double[] absFrequencies = new double[frequencies.length];
		
		for(int i=0; i<frequencies.length; i++){
			absFrequencies[i] = frequencies[i].abs(); 
		}
		
		return absFrequencies;
		
	}
	
	/*
	 * @param wave: Wave object 
	 * @param segmentSize: size of one Segment
	 * @param overlapFactor: overlap fraction
	 * 
	 * @return Array of segments
	 */
	public static Segment[] getSegments(Wave wave, int segmentSize, int overlapFactor, String windowType){
		double[] amplitudes=wave.getAmplitudes();
		int numSamples = amplitudes.length;
		
		int pointer=0;
		// overlapping
		if (overlapFactor>1){
			int numOverlappedSamples=numSamples*overlapFactor;
			int backSamples=segmentSize*(overlapFactor-1)/overlapFactor;
			int fftSampleSize_1=segmentSize-1;
			double[] overlapAmp= new double[numOverlappedSamples];
			pointer=0;
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
		WindowFunction window = new WindowFunction();
		window.setWindowType(windowType);
		double[] win=window.generate(segmentSize);
	
		double[][] signals=new double[numFrames][];
		Segment[] segmentArray = new Segment[numFrames];
		for(int f=0; f<numFrames; f++) {
			signals[f]=new double[segmentSize];
			int startSample=f*segmentSize;
			for (int n=0; n<segmentSize; n++){
				signals[f][n]=amplitudes[startSample+n]*win[n];							
			}
			segmentArray[f] = new Segment(signals[f],wave.getWaveHeader().getSampleRate());
		}
		return segmentArray;
	}

}
