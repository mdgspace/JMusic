/**
 * 
 */
package com.jmusic.demo;

import com.jmusic.feature.Rolloff;

/**
 * @author Anshul Shah
 *
 */
public class RolloffTest {
	public static void main(String[] args) {
		 double[] absRFFT = {5524.72, 3163.57731592, 125.58};
		 Rolloff rf = new Rolloff(absRFFT, 22050);
		 // must be 3675.0
		 System.out.println(rf.evaluate());
	}
}
