package com.jmusic.demo;

import com.jmusic.feature.SpectralMean;
import com.jmusic.feature.SpectralVariance;

/**
 * @author Anshul Shah
 *
 */
public class VarianceTest {
	public static void main(String[] args) {
		double[] absRFFT = {5524.72, 3163.57731592, 125.58};
		 SpectralVariance rf = new SpectralVariance(absRFFT, 22050);
		 // must be 4883903.911746848
		 System.out.println(rf.evaluate());
		 
		 SpectralMean mn = new SpectralMean(absRFFT);
		 // must be 2937.959105306667
		 System.out.println(mn.evaluate());
	}
}
