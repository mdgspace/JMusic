package com.jmusic.feature;

/**
 * Evaluate the zero crossing rate of a signal
 * 
 * @author Anshul Shah, Deepankar Agrawal
 *
 */
public class ZeroCrossingRate extends Feature<Double>{
	/**
	 * 
	 * @param signal: Time - Domain Amplitude of the signal
	 * @param samplingRate: sampling rate of audio signal
	 */
	public ZeroCrossingRate(double[] signal, double samplingRate){
		setValues(signal, samplingRate);
	}
		
	public Double evaluate(){
		double ans = 0;
		double signalDuration = (double) signal.length / samplingRate; 
		for(int i = 1;i<signal.length;i++){
			if(signal[i]*signal[i-1] < 0) ans++;
		}
		ans /= signalDuration;
		return ans;
	}
}
