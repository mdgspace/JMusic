package com.musicg.demo;

import com.jmusic.wave.Wave;
import com.jmusic.wave.extension.Spectrogram;
import com.musicg.processor.TopManyPointsProcessorChain;

public class PitchDemo{
	
	public static void main(String[] args){
		
		String filename = "audio_work/cock_a_1.wav";

		// create a wave object
		Wave wave = new Wave(filename);
		Spectrogram spectrogram = new Spectrogram(wave);
		
		TopManyPointsProcessorChain processorChain=new TopManyPointsProcessorChain(spectrogram.getNormalizedSpectrogramData(),1);
		double[][] processedIntensities=processorChain.getIntensities();
		
		for (int i=0; i<processedIntensities.length; i++){
			for (int j=0; j<processedIntensities[i].length; j++){
				if (processedIntensities[i][j]>0){
					System.out.println(i+": "+processedIntensities[i][j]);
				}
			}
		}
	}
}