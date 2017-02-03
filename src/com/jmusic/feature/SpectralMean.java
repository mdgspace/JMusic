package com.jmusic.feature;

/**
 * @author Anshul Shah
 *
 */
public class SpectralMean extends Feature<Double> {
	/**
	 * 
	 * @param signal: Frequency-Domain signal
	 */
	public SpectralMean(double[] signal){
		setValues(signal, 44000);
	}
	
	public Double evaluate() {
		double sum = 0.0;
        for(double a : signal)
            sum += a;
        return sum/signal.length;
	}

}
