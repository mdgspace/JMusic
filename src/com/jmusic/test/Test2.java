package com.jmusic.test;

import com.jmusic.Segment;
import com.jmusic.utils.Utils;
import com.jmusic.utils.WindowFunction;
import com.jmusic.wave.Wave;

public class Test2 {

	public static void main(String[] args) {
		String filename = "audio_work/pop.00000.wav";
		
		Wave wave = new Wave(filename);
		
		Segment[] seg = wave.getSegments(8, 0, WindowFunction.HAMMING);
		
		double[] d = seg[1].amplitude;
		Utils.printArray(d, "\n");
	}

}
