package com.jmusic.segment;

import com.jmusic.feature.SpectralCentroid;
import com.jmusic.feature.SpectralMean;
import com.jmusic.math.FFT;
import com.jmusic.utils.Utils;
import com.jmusic.wave.Wave;

/**
 * Container class for features of audio data segments
 * 
 * @author Deepankar Agrawal
 *
 */
public class Segment {
	
	public double[] amplitude;
	public Complex[] frequencies;
	public double[] absFrequencies;
	
	public double samplingRate;
	
	public Segment(Wave wave){
		this(wave.getAmplitudes(), wave.getWaveHeader().getSampleRate());
	}
	
	public Segment(double[] amplitude, int samplingRate){
		this.amplitude = amplitude;
		this.samplingRate = (double)samplingRate;
		
		buildFFT(amplitude);
	}
	
	private void buildFFT(double[] amplitude){
		// call the fft and transform to fourier domain
		FFT fft = new FFT();
		this.frequencies = fft.transform_rfft(amplitude);
		this.absFrequencies = Utils.complexToAbs(frequencies);
	}
	
	public double spectralMean(){
		SpectralMean mean = new SpectralMean(absFrequencies);
		return mean.evaluate();
	}
	
	public double spectralCentroid(){
		SpectralCentroid sc = new SpectralCentroid(absFrequencies, samplingRate);
		return sc.evaluate();
	}
	
}
