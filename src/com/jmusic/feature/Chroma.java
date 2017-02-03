/**
 * 
 */
package com.jmusic.feature;

/**
 * @author Anshul Shah
 *
 */
public class Chroma extends Feature< double[] > {
	/**
	 * 
	 * @param spectrum: Absolute Frequency-Domain Signal
	 * @param samplingRate
	 */
	public Chroma(double signal[], double samplingRate){
		setValues(signal, samplingRate);
	}
	
	public double[] evaluate() {
		double[] chroma = new double[12];
		double df = (samplingRate / 2.0) / (double) signal.length; 
		for(int i = 0;i<signal.length;i++){
			double frequencyi = df*i;
			
			int pitch;
			// Conversion from frquency to pitch
			if(frequencyi == 0){
				pitch = 0;
			}
			else{
				pitch = frequencyToMidi(frequencyi);
			}
			int pitchClass = pitch % 12;
			
			chroma[pitchClass] += signal[i];
		}
		
		return chroma;
	}
	/*
	 * Convert a given frequency in Hertz to its corresponding 
	 * MIDI pitch number (60 = Middle C)
	 */
	public int frequencyToMidi(double frequency)
	{
		double flog2 =  Math.log(frequency / 440.0) / Math.log(2); 
		return (int) (Math.round(69 + 12 * (flog2)));
	}

}
