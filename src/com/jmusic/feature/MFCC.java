package com.jmusic.feature;

import com.jmusic.utils.Utils;

/**
 * Calculate Mel Frequency Cepstrum Coeffecients from the magnitude spectrum
 * of a signal
 * 
 * @author Deepankar Agrawal
 *
 */
public class MFCC extends Feature<double[]>{
	
	 /**
     * Number of samples per frame
     */
    private static int frameLength = 1024;
    
    /**
     * Number of MFCCs per frame
     */
    public int numCepstra = 13;
    /**
     * Pre-Emphasis Alpha (Set to 0 if no pre-emphasis should be performed)
     */
    private final static double preEmphasisAlpha = 0;
    /**
     * lower limit of filter (or 64 Hz?)
     */
    private final static double lowerFilterFreq = 133.3334;
    /**
     * upper limit of filter (or half of sampling freq.?)
     */
    private final static double upperFilterFreq = 6855.4976;
    /**
     * number of mel filters (SPHINX-III uses 40)
     */
    public final static int numMelFilters = 32;
	
	/**
	 * 
	 * @param spectrum: Absolute Frequency-Domain Signal
	 * @param samplingRate
	 */
	public MFCC(double[] signal, double samplingRate){
		this(signal, samplingRate, 13);
	}
	
	public MFCC(double[] signal, double samplingRate, int numCepc){
		this.numCepstra = numCepc;
		
		setValues(signal, samplingRate);	
		frameLength = signal.length;
	}

	@Override
	public double[] evaluate() {

        // Pre-Emphasis
//        double outputSignal[] = preEmphasis(signal);
        
        // Mel Filtering
        int cbin[] = fftBinIndices(samplingRate, frameLength);
        //Utils.printArray(cbin, "\n");
       
        // get Mel Filterbank
        double fbank[] = melFilter(signal, cbin);
//        Utils.printArray(fbank, "\n");

        // Non-linear transformation
        double f[] = nonLinearTransformation(fbank);

        // Cepstral coefficients
        return cepCoefficients(f);
        
	}
	
	/**
     * calculates the FFT bin indices<br>
     * calls: none<br>
     * 
     * 5-3-05 Daniel MCEnnis paramaterize sampling rate and frameSize
     * 
     * @return array of FFT bin indices
     */
    private int[] fftBinIndices(double samplingRate,int frameSize){
        int cbin[] = new int[numMelFilters + 2];
        
        cbin[0] = (int)Math.round(lowerFilterFreq / samplingRate * frameSize);
        cbin[cbin.length - 1] = (int)(frameSize / 2);
        
        for (int i = 1; i <= numMelFilters; i++){
            double fc = centerFreq(i,samplingRate);

            cbin[i] = (int)Math.round(fc / samplingRate * frameSize);
        }
        
        return cbin;
    }
    /**
     * Calculate the output of the mel filter<br>
     */
    private double[] melFilter(double bin[], int cbin[]){
        double temp[] = new double[numMelFilters + 2];

        for (int k = 1; k <= numMelFilters; k++){
            double num1 = 0, num2 = 0;

            for (int i = cbin[k - 1]; i <= cbin[k]; i++){
                num1 += ((i - cbin[k - 1] + 1) / (cbin[k] - cbin[k-1] + 1)) * bin[i];
            }

            for (int i = cbin[k] + 1; i <= cbin[k + 1]; i++){
                num2 += (1 - ((i - cbin[k]) / (cbin[k + 1] - cbin[k] + 1))) * bin[i];
            }

            temp[k] = num1 + num2;
        }

        double fbank[] = new double[numMelFilters];
        for (int i = 0; i < numMelFilters; i++){
            fbank[i] = temp[i + 1];
        }

        return fbank;
    }
    /**
     * Cepstral coefficients are calculated from the output of the Non-linear Transformation method<br>
     * calls: none<br>
     * called by: featureExtraction
     * @param f Output of the Non-linear Transformation method
     * @return Cepstral Coefficients
     */
    private double[] cepCoefficients(double f[]){
        double cepc[] = new double[numCepstra];
        
        for (int i = 0; i < cepc.length; i++){
            for (int j = 1; j <= numMelFilters; j++){
                cepc[i] += f[j - 1] * Math.cos(Math.PI * i / numMelFilters * (j - 0.5));
            }
        }
        
        return cepc;
    }
    /**
     * the output of mel filtering is subjected to a logarithm function (natural logarithm)<br>
     * calls: none<br>
     * called by: featureExtraction
     * @param fbank Output of mel filtering
     * @return Natural log of the output of mel filtering
     */
    private double[] nonLinearTransformation(double fbank[]){
        double f[] = new double[fbank.length];
        final double FLOOR = -50;
        
        for (int i = 0; i < fbank.length; i++){
            f[i] = Math.log(fbank[i]);
            
            // check if ln() returns a value less than the floor
            if (f[i] < FLOOR) f[i] = FLOOR;
        }
        
        return f;
    }
    /**
     * calculates logarithm with base 10<br>
     * calls: none<br>
     * called by: featureExtraction
     * @param value Number to take the log of
     * @return base 10 logarithm of the input values
     */
    private static double log10(double value){
        return Math.log(value) / Math.log(10);
    }
    /**
     * calculates center frequency<br>
     * calls: none<br>
     * called by: featureExtraction
     * @param i Index of mel filters
     * @return Center Frequency
     */
    private static double centerFreq(int i,double samplingRate){
        double mel[] = new double[2];
        mel[0] = freqToMel(lowerFilterFreq);
        mel[1] = freqToMel(samplingRate / 2);
        
        // take inverse mel of:
        double temp = mel[0] + ((mel[1] - mel[0]) / (numMelFilters + 1)) * i;
        return inverseMel(temp);
    }
    /**
     * calculates the inverse of Mel Frequency<br>
     * calls: none<br>
     * called by: featureExtraction
     */
    private static double inverseMel(double x){
        double temp = Math.pow(10, x / 2595) - 1;
        return 700 * (temp);
    }
    /**
     * convert frequency to mel-frequency<br>
     * calls: none<br>
     * called by: featureExtraction
     * @param freq Frequency
     * @return Mel-Frequency
     */
    private static double freqToMel(double freq){
        return 2595 * log10(1 + freq / 700);
    }
    
    /**
     * perform pre-emphasis to equalize amplitude of high and low frequency<br>
     * calls: none<br>
     * called by: featureExtraction
     * @param inputSignal Speech Signal (16 bit integer data)
     * @return Speech signal after pre-emphasis (16 bit integer data)
     */
    protected static double[] preEmphasis(double inputSignal[]){
        double outputSignal[] = new double[inputSignal.length];
        
        // apply pre-emphasis to each sample
        for (int n = 1; n < inputSignal.length; n++){
            outputSignal[n] = inputSignal[n] - preEmphasisAlpha * inputSignal[n - 1];
        }
        
        return outputSignal;
    }

}
