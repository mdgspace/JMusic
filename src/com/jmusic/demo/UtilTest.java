package com.jmusic.demo;

import com.jmusic.segment.Segment;
import com.jmusic.utils.Utils;
import com.jmusic.wave.Wave;

/**
 * @author Anshul Shah
 *
 */
public class UtilTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String filename = "audio_work/pop.00000.wav";
		
		Wave wave = new Wave(filename);
		
		Segment[] segment = Utils.getSegments(wave, 1024, 0);
		for(int i =0;i<segment.length;i++){
			System.out.println(i + " : " + segment[i].absFrequencies.length);
		}
		for(int i = 0;i<segment[0].absFrequencies.length;i++){
			System.out.println(segment[0].absFrequencies[i]);
		}
	}
}
