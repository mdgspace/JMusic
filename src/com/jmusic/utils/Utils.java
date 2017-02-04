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
	
	/**
	 * @param totalFrames: Number of frames
	 * @param segmentCount: Number of segments needed 
	 * @return nearest power of 2 >= segment size
	 */
	public static int getSegmentSize(int totalFrames, int segmentCount){
		int segmentSize = totalFrames / segmentCount;
		int lg2 = (int) (Math.log(segmentSize)/Math.log(2));
		if( (1<<lg2)*segmentCount < totalFrames )
			return (1<<(lg2+1));
		else return (1<<lg2);
	}
	
	/*
	 * @param wave: Wave object 
	 * @param segmentSize: size of one Segment
	 * @param overlapFactor: overlap fraction
	 * @param windowType: Hamming window
	 * 
	 * @return Array of segments
	 */
	public static Segment[] getSegments(Wave wave, int segmentSize, 
			int overlapFactor, int windowType){
		
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
	
	public static void printArray(double[] data, String delimiter){
		System.out.println("length is "+ data.length);
		for(int i=0; i< data.length; i++){
			System.out.print(data[i] + delimiter);
		}
	}
	
	public static void printArray(int[] data, String delimiter){
		System.out.println("length is "+ data.length);
		for(int i=0; i< data.length; i++){
			System.out.print(data[i] + delimiter);
		}
	}

}
