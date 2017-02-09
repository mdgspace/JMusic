/**
 * 
 */
package com.jmusic.test;

import com.jmusic.feature.ZeroCrossingRate;

/**
 * @author Anshul Shah
 *
 */
public class ZCRTest {
	public static void main(String[] args) {
		double input[] = {1, 4, -3, 5,-7 };
		
		ZeroCrossingRate sc = new ZeroCrossingRate(input,input.length);
		
		// must be 3.0
		System.out.println(sc.evaluate());
	}
}
