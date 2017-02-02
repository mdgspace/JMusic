package com.jmusic;

import com.musicg.wave.Wave;
import com.musicg.wave.extension.Spectrogram;


public class Test {
    public static void main(String[] args) {
    	
		String filename = "audio_work/pop.00000.wav";

		String outFolder="out";

		// create a wave object
		Wave wave = new Wave(filename);

		// print the wave header and info
		System.out.println(wave);
		
//		short arr[] = wave.getSampleAmplitudes();
//		for(int i = 0;i<100;i+=2){
//			System.out.println(arr[i]);
//		}

		// trim the wav
//		wave.leftTrim(1);
//		wave.rightTrim(0.5F);
		
		Spectrogram s = new Spectrogram(wave);
		double[][] data = s.getNormalizedSpectrogramData();
		
		System.out.println(data.length);
		System.out.println(data[0].length);
		
		// save the trimmed wav
//		WaveFileManager waveFileManager=new WaveFileManager(wave);
//		waveFileManager.saveWaveAsFile(outFolder+"/out.wav");	
    }
}