/**
 * 
 */
package com.jmusic.test;

import com.jmusic.feature.Chroma;

/**
 * @author Anshul Shah
 *
 */
public class ChromaTest {
	public static void main(String[] args) {
		double input[] = { 5524.72     ,   3163.57731592  , 125.58};
		
		Chroma sc = new Chroma(input,22050);
		
		/*
		 * 
		 * 1.0
		 * 0.0
		 * 0.0
		 * 0.0
		 * 0.0
		 * 0.0
		 * 0.0
		 * 0.0
		 * 0.0
		 * 0.0
		 * 0.5953527628404697
		 * 0.0
		 */
		double chroma[] = sc.evaluate();
		for(int i = 0;i<chroma.length;i++)
		{
			System.out.println(chroma[i]);
		}
	}
}
