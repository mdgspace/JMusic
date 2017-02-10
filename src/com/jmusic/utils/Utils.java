package com.jmusic.utils;

import com.jmusic.Segment;
import com.jmusic.math.Complex;
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
