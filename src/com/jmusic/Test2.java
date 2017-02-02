package com.jmusic;

import com.musicg.wave.Wave;
import com.musicg.wave.extension.Spectrogram;

public class Test2 {

	public static void main(String[] args) {
		String filename = "audio_work/pop.00000.wav";
		
		Wave wave = new Wave(filename);
		
		Spectrogram sp = new Spectrogram(wave,16,0);
		
		double arr[][] = sp.getAbsoluteSpectrogramData();
		
		System.out.println(arr[0].length);
		for(int i = 0;i<arr[0].length;i++)
			System.out.println(arr[0][i]);
		
		
//		double ans[] = FFTTest.transform_rfft();
//		for(int i = 0;i<ans.length;i++)
//			System.out.println(ans[i]);
		
	}

}
