package com.jmusic.feature;

/**
 * Calculate Mel Frequency Cepstrum Coeffecients from the magnitude 
 * spectrum of a signal
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
    public int numCepstral = 12;
    
    /**
     * lower limit of filter (or 64 Hz?)
     */
    private static double lowerFilterFreq = 0;
    /**
     * upper limit of filter (or half of sampling freq.?)
     */
    private static double upperFilterFreq = 8000;
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
		this(signal, samplingRate, 12);
	}
	
	public MFCC(double[] signal, double samplingRate, int numCepc){
		this.numCepstral = numCepc;
		
		setValues(signal, samplingRate);	
		frameLength = signal.length;
		upperFilterFreq = frameLength/2;
	}

	@Override
	public double[] evaluate() {
        
        // Mel Filtering
        int cbin[] = fftBinIndices(samplingRate, frameLength);
       
        // get Mel Filterbank
        double fbank[] = melFilter(signal, cbin);

        // Non-linear transformation
        double f[] = nonLinearTransformation(fbank);

        return dct(f, true);
        
	}
	
	/**
     * calculates the FFT bin indices<br>
     * 
     * 5-3-05 Daniel MCEnnis paramaterize sampling rate and frameSize
     * 
     * @return array of FFT bin indices
     */
    private int[] fftBinIndices(double samplingRate,int frameSize){
        int cbin[] = new int[numMelFilters + 2];
        
        cbin[0] = (int)Math.round(lowerFilterFreq / samplingRate * frameSize);
        cbin[cbin.length - 1] = (int)(upperFilterFreq);
        
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
     * Cepstral coefficients are calculated from the output of the
     * Non-linear Transformation method<br>
     *
     * @param f Output of the Non-linear Transformation method
     * @return Cepstral Coefficients
     */
    private double[] dct(double f[], boolean normalize){
        double cepc[] = new double[numCepstral];
        double normalizationFactor = 1.0;
        double numMel = (double)numMelFilters;
        
        for (int i = 0; i < numCepstral; i++){
            for (int j = 0; j < numMelFilters; j++){
                cepc[i] += f[j] * Math.cos(Math.PI * i / numMel * (j + 0.5));    
            }
            
            cepc[i] = cepc[i] * 2;
            
          //normalize
            
            if(normalize){
            	if(i==0){	
                	  normalizationFactor = Math.sqrt(1/(4*numMel));
                }else normalizationFactor = Math.sqrt(1/(2*numMel));
                  
                cepc[i] = cepc[i] * normalizationFactor;
            }       
        }
        
        
        
        return cepc;
    }
    
    /**
     * the output of mel filtering is subjected to a logarithm 
     * function (natural logarithm)<br>
     * 
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
     * calculates center frequency<br>
     * 
     * @param i Index of mel filters
     * @return Center Frequency
     */
    private double centerFreq(int i,double samplingRate){
        double mel[] = new double[2];
        mel[0] = freqToMel(lowerFilterFreq);
        mel[1] = freqToMel(upperFilterFreq);
        
        // take inverse mel of:
        double temp = mel[0] + ((mel[1] - mel[0]) / (numMelFilters + 1)) * i;
        return inverseMel(temp);
    }
    
    /**
     * calculates the inverse of Mel Frequency<br>
     * 
     */
    private double inverseMel(double x){
        return 700 * (Math.expm1(x/1125));
    }
    /**
     * convert frequency to mel-frequency<br>
     * 
     * @param freq Frequency
     * @return Mel-Frequency
     */
    private double freqToMel(double freq){
        return 1125 * Math.log(1 + freq / 700);
    }

}
