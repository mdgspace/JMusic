package com.jmusic.feature;

/**
 * @author Anshul Shah
 *
 */
public class SpectralVariance extends Feature<Double> {
	/**
	 * 
	 * @param signal: Frequency-Domain signal
	 * @param samplingRate: Not used
	 */
	public SpectralVariance(double[] signal, double samplingRate){
		setValues(signal,samplingRate);
	}
	
	public Double evaluate() {
		SpectralMean sm = new SpectralMean(signal, samplingRate);
		double mean = sm.evaluate();
		double temp = 0;
        for(double a :signal)
            temp += (a-mean)*(a-mean);
        return temp/signal.length;
	}
	
}
