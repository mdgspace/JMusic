package com.jmusic.demo;

import com.jmusic.feature.SpectralCentroid;
import com.jmusic.math.FFT;

public class CentroidTest {

	public static void main(String[] args) {
		double input[] = { 5524.72     ,   3163.57731592  , 125.58};
		
		SpectralCentroid sc = new SpectralCentroid(input,22050);
		
		// must be about 1423.7955880483125
		System.out.println(sc.evaluate());
	}
}
