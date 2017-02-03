package com.jmusic.feature;

/**
 * @author Anshul Shah
 *
 */
public class SpectralVariance extends Feature<Double> {
	/**
	 * 
	 * @param signal: Frequency-Domain signal
	 */
	public SpectralVariance(double[] signal){
		setValues(signal, 44000);
	}
	
	public Double evaluate() {
		SpectralMean sm = new SpectralMean(signal);
		double mean = sm.evaluate();
		double temp = 0;
        for(double a :signal)
            temp += (a-mean)*(a-mean);
        return temp/signal.length;
	}
	
}
