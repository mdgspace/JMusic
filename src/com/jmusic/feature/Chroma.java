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
		int pitch;
		
		for(int i = 0; i< signal.length; i++){
			double frequencyi = df*i;
			
			// Conversion from frequency to pitch
			if(frequencyi == 0){
				pitch = 0;
			}else{
				pitch = frequencyToMidi(frequencyi);
			}
			
			int pitchClass = pitch%12;
			if(pitchClass<0) pitchClass += 12;
			
			chroma[pitchClass] += signal[i];
		}
		
		// Normalization
		double maxElement = 0;
		for(int i = 0;i<12;i++)
			maxElement = Math.max(maxElement,chroma[i]);
		
		for(int i = 0;i<12;i++)
			chroma[i] /= maxElement;
		
		return chroma;
	}
	/*
	 * Convert a given frequency in Hertz to its corresponding 
	 * MIDI pitch number (60 = Middle C)
	 */
	private int frequencyToMidi(double frequency){
		double flog2 =  Math.log(frequency / 440.0) / Math.log(2);
		return (int) (Math.round(69 + 12 * (flog2)));
	}

}
