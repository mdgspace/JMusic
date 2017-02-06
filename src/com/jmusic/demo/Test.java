package com.jmusic.demo;

import com.jmusic.Segment;
import com.jmusic.utils.Utils;
import com.jmusic.utils.WindowFunction;
import com.jmusic.wave.Wave;


public class Test {
    public static void main(String[] args) {
    	
		String filename = "audio_work/pop.00000.wav";

//		String outFolder="out";

		// create a wave object
		Wave wave = new Wave(filename);

		// print the wave header and info
//		System.out.println(wave);
		
		Segment[] segments = Utils.getSegments(wave, 512, 0, 
				WindowFunction.HAMMING);
		
		System.out.println(segments.length);
		System.out.println(segments[0].amplitude.length);
		
		double[] mfcc = segments[0].mfcc();
		
		for(int i=0;i<mfcc.length;i++){
			System.out.println(mfcc[i]);
		}
		
		
		// save the trimmed wav
//		WaveFileManager waveFileManager=new WaveFileManager(wave);
//		waveFileManager.saveWaveAsFile(outFolder+"/out.wav");
    }
}