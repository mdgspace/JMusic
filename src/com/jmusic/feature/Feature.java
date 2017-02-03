package com.jmusic.feature;

/**
 * Abstract class for a feature
 * 
 * @author Deepankar Agrawal
 *
 */
public abstract class Feature<T> {
	
	protected double[] signal;
	protected double   samplingRate;
	
	public void setValues(double[] signal, double samplingRate){
		this.signal = signal;
		this.samplingRate = samplingRate;
	}
	
	public abstract T evaluate();

}
