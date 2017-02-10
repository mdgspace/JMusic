package com.jmusic;

import com.jmusic.feature.Chroma;
import com.jmusic.feature.MFCC;
import com.jmusic.feature.Rolloff;
import com.jmusic.feature.SpectralCentroid;
import com.jmusic.feature.SpectralMean;
import com.jmusic.feature.SpectralVariance;
import com.jmusic.feature.ZeroCrossingRate;
import com.jmusic.math.Complex;
import com.jmusic.math.FFT;
import com.jmusic.utils.Utils;

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
	
	private double samplingRate;
	private int overlapFactor;
	private int windowType;
	
	
	
	public Segment(double[] amplitude, int samplingRate, int overlapFactor, int windowType){
		this.amplitude = amplitude;
		this.samplingRate = (double)samplingRate;
		this.overlapFactor = overlapFactor;
		this.windowType = windowType;
		
		buildFFT(amplitude, windowType);
	}
	
	private void buildFFT(double[] amplitude, int windowType){
		// call the fft and transform to fourier domain
		FFT fft = new FFT();
		this.frequencies = fft.transform_rfft(amplitude, windowType);
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
	
	/**
	 * 
	 * @return array of 12 chroma features
	 */
	public double[] chroma(){
		Chroma chroma = new Chroma(absFrequencies, samplingRate);
		
		return chroma.evaluate();
	}
	
	/**
	 * Calculate Mel Frequency Cepstrum Coeffecients from the magnitude 
	 * spectrum of a signal
	 * 
	 * @return first 12 Mfcc coefficients
	 */
	public double[] mfcc(){
		MFCC mfcc = new MFCC(absFrequencies, samplingRate);
		
		return mfcc.evaluate();
	}
	
	/**
	 * Evaluate the zero crossing rate of a signal
	 * 
	 * @return value of zcr
	 */
	public double zcr(){
		ZeroCrossingRate zcr = new ZeroCrossingRate(amplitude, samplingRate);
		
		return zcr.evaluate();
	}

	public int getOverlapFactor() {
		return overlapFactor;
	}

	public int getWindowType() {
		return windowType;
	}
	
}
