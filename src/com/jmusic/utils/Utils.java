package com.jmusic.utils;

import com.jmusic.segment.Complex;

/**
 * Class containing helper functions
 * 
 * @author Deepankar Agrawal
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

}
