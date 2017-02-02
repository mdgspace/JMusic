package com.jmusic.demo;

import com.jmusic.wave.Wave;
import com.jmusic.wave.extension.Spectrogram;


public class Test {
    public static void main(String[] args) {
    	
		String filename = "audio_work/pop.00000.wav";

		String outFolder="out";

		// create a wave object
		Wave wave = new Wave(filename);

		// print the wave header and info
		System.out.println(wave);
		
		short amplitudes[] = wave.getSampleAmplitudes();
		System.out.println("song length "+ amplitudes.length);
//		for(int i = 0;i<100;i+=2){
//			System.out.println(arr[i]);
//		}
		
		Spectrogram s = new Spectrogram(wave);
		double[][] data = s.getAbsoluteSpectrogramData();
		
		System.out.println("Nmber of frames - "+s.getNumFrames());
		System.out.println(s.getNumFrequencyUnit());
		
//		for(int i=0; i< data[0].length;i++){
//			System.out.println(data[0][i]);
//		}
		System.out.println(s.getFramesPerSecond());
		
		
		// save the trimmed wav
//		WaveFileManager waveFileManager=new WaveFileManager(wave);
//		waveFileManager.saveWaveAsFile(outFolder+"/out.wav");
    }
}