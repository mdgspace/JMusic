package com.jmusic;

import com.musicg.wave.Wave;
import com.musicg.wave.extension.Spectrogram;
import com.sun.media.sound.JFFT;

public class Test2 {

	public static void main(String[] args) {
		String filename = "audio_work/pop.00000.wav";
		
		Wave wave = new Wave(filename);
		
		Spectrogram sp = new Spectrogram(wave,16,0);
		
		double arr[][] = sp.getAbsoluteSpectrogramData();
		
		System.out.println(arr[0].length);
		for(int i = 0;i<arr[0].length;i++)
			System.out.println(arr[0][i]);
		
		double input[] = {-232.56000000000006, -2403.1699999999996, -2592.5900000000006, -296.40000000000003};
		
		JFFT fft = new JFFT();
		double ans[] = fft.transform_rfft(input);
		for(int i = 0;i<ans.length;i++)
			System.out.println(ans[i]);
		
	}

}
