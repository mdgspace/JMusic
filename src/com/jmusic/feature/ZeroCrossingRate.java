package com.jmusic.feature;
/**
 * Evaluate the zero crossing rate of a signal
 * 
 * 
 * 
 * @author Anshul Shah
 *
 */
public class ZeroCrossingRate extends Feature<Double>{
	/**
	 * 
	 * @param signal: Time - Domain Amplitude of the signal
	 * @param samplingRate: Not used
	 */
	public ZeroCrossingRate(short[] signal, double samplingRate){
		double[] sig = new double[signal.length];
		for(int i = 0;i<sig.length;i++)
			sig[i] = signal[i];
		setValues(sig, samplingRate);
	}
		
	public Double evaluate(){
		double ans = 0;
		double signalDuration = (double) signal.length / samplingRate; 
		for(int i = 1;i<signal.length;i++)
		{
			if(signal[i]*signal[i-1] < 0) ans++;
		}
		ans /= signalDuration;
		return ans;
	}
}
