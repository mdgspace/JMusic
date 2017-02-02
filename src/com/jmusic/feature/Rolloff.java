package com.jmusic.feature;
/*
 * @author Anshul Shah
 * 
 *  Determine the spectral rolloff, i.e. the frequency below 
 *  which 85% of the spectrum's energy is located
 */
public class Rolloff extends Feature<Double> {
	/**
	 * 
	 * @param spectrum: Absolute Frequency-Domain Signal
	 * @param samplingRate
	 */
	public Rolloff(double[] spectrum, double samplingRate){
		setValues(spectrum, samplingRate);
	}
	
	public Double evaluate() {
		double spectralSum = 0;
		for(int i = 0;i<signal.length;i++){
			spectralSum += signal[i];
		}
		
		double rolloffSum = 0;
		double rolloffIndex = 0;
		for(int i = 0;i<signal.length;i++)
		{
			rolloffSum += signal[i];
			if(rolloffSum > 0.85*spectralSum)
			{
				rolloffIndex = i;
				break;
			}
		}
		
		// Convert index into a frequency
		double frequency = rolloffIndex * ((samplingRate / 2.0) / (double) signal.length);
		
		return frequency;
	}
}
