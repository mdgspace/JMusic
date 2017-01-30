package com.jmusic;

import com.musicg.wave.Wave;
import com.sun.media.sound.FFT;


public class Test {
    public static void main(String[] args) {
    	
		String filename = "audio_work/pop.00000.wav";
		String outFolder="out";

		// create a wave object
		Wave wave = new Wave(filename);

		// print the wave header and info
		System.out.println(wave);
		
		short arr[] = wave.getSampleAmplitudes();
		for(int i = 0;i<100;i+=2)
		{
			System.out.println( arr[i] );
		}

		// trim the wav
//		wave.leftTrim(1);
//		wave.rightTrim(0.5F);
			
//		FFT fft = new FFT()
		
//		byte[] arr = wave.getBytes();
//		System.out.println(arr.length/2);
//		int pw = 32768; 
//		for(int i = 34;i<50;i+=2)
//		{
//			int a = arr[i] & 0xFF;
//			int b = arr[i+1] & 0xFF;
//			int c = (short)( ((int)b)<<8 | (int)a);
//			System.out.println( c );
//		}
		
		// save the trimmed wav
//		WaveFileManager waveFileManager=new WaveFileManager(wave);
//		waveFileManager.saveWaveAsFile(outFolder+"/out.wav");	
    }
}