package com.jmusic.segment;

import com.jmusic.feature.Rolloff;
import com.jmusic.feature.SpectralCentroid;
import com.jmusic.feature.SpectralMean;
import com.jmusic.feature.SpectralVariance;
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
	
	/**
	 * Computes the mean of DFT frequencies
	 * 
	 * Mean = Σ Fi / N
	 * 
	 * @return mean of absolute frequencies
	 */
	public double spectralMean(){
		SpectralMean mean = new SpectralMean(absFrequencies);
		
		return mean.evaluate();
	}
	
	/**
	 * 
	 * @return variance of absolute frequencies
	 */
	public double spectralVariance(){
		SpectralVariance variance = new SpectralVariance(absFrequencies);
		
		return variance.evaluate();
	}
	
	/**
	 * 
	 * @return centroid of absolute frequencies
	 */
	public double spectralCentroid(){
		SpectralCentroid sc = new SpectralCentroid(absFrequencies, samplingRate);
		
		return sc.evaluate();
	}
	
	/**
	 * Calculates the spectral rolloff, i.e. the frequency below 
	 * which 85% of the spectrum's energy is located
	 * 
	 * @return roll off frequency
	 */
	public double rollOff(){
		Rolloff rollOff = new Rolloff(absFrequencies, samplingRate);
		
		return rollOff.evaluate();
	}
	
}
