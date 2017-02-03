package com.jmusic.segment;

import com.jmusic.feature.SpectralCentroid;
import com.jmusic.wave.Wave;

/**
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
	}
	
	//TODO: decide on data type of amplitude
	private void buildFFT(short[] amplitude){
		
	}
	
	public double spectralCentroid(){
		SpectralCentroid sc = new SpectralCentroid(absFrequencies, samplingRate);
		return sc.evaluate();
	}
	
}
