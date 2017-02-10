package com.jmusic.test;

import com.jmusic.wave.Wave;

public class WaveDemo {

	public static void main(String[] args) {

		String filename = "audio_work/cock_a_1.wav";
//		String outFolder="out";

		// create a wave object
		Wave wave = new Wave(filename);

		// print the wave header and info
		System.out.println(wave);
		
		wave.trim(0.1, 0);

	}
}