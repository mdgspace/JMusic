package com.jmusic.segment;

import com.jmusic.feature.SpectralCentroid;
import com.jmusic.wave.Wave;

public class Segment {
	
	public short[] amplitude;
	public Complex[] frequencies;
	public double[] absFrequencies;
	
	public double samplingRate;
	
	public Segment(Wave wave){
		this(wave.getSampleAmplitudes(), wave.getWaveHeader().getSampleRate());
	}
	
	public Segment(short[] amplitude, int samplingRate){
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
