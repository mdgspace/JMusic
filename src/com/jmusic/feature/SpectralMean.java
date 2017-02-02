package com.jmusic.feature;

/**
 * @author Anshul Shah
 *
 */
public class SpectralMean extends Feature<Double> {
	/**
	 * 
	 * @param signal: Frequency-Domain signal
	 * @param samplingRate: Not used
	 */
	public SpectralMean(double[] signal, double samplingRate){
		setValues(signal, samplingRate);
	}
	
	public Double evaluate() {
		double sum = 0.0;
        for(double a : signal)
            sum += a;
        return sum/signal.length;
	}

}
