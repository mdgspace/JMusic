package com.jmusic.feature;

/**
 * Evaluate the spectral centroid of an array
 * 
 * @author Deepankar Agrawal,Anshul Shah
 *
 */
public class SpectralCentroid extends Feature<Double>{
	
	
	public SpectralCentroid(double[] signal, double samplingRate){
		setValues(signal, samplingRate);
	}
		
	public Double evaluate(){
		double sumCentroid=0;
		double sumIntensities=0;
		double df = (samplingRate/2.0)/signal.length;
		int size=signal.length;
		
		for (int i=0; i<size; i++){
			double frequencyi = df*i;
			sumCentroid += signal[i]*frequencyi;
			sumIntensities += signal[i];
		}
		double avgCentroid=sumCentroid/sumIntensities;
		
		return new Double(avgCentroid);
	}
}
