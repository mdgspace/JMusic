package com.jmusic.math;

import com.jmusic.utils.WindowFunction;

/**
 * 
 * @author Anshul Shah, Deepankar Agrawal
 *
 */
public class FFT {
	
    /**
     * 
     * @param input input data to be transformed
     * @return transformed data
     */
    public Complex[] transform(double input[], int windowType) {
    	
    	WindowFunction window = new WindowFunction();
		window.setWindowType(windowType);
		double[] win=window.generate(input.length);
 
        Complex[] cinput = new Complex[input.length];
        for (int i = 0; i < input.length; i++)
            cinput[i] = new Complex(input[i]*win[i], 0.0);
 
        return fft(cinput);
    }
    
    /**
     * 
     * When the DFT is computed for purely real input, the output is
     * Hermitian-symmetric, i.e. the negative frequency terms are just the complex
     * conjugates of the corresponding positive-frequency terms, and the
     * negative-frequency terms are therefore redundant.  This function thus return
     * these positive frequency data
     * 
     * @param input data to be transformed
     * @return transformed data
     * 
     */
    public Complex[] transform_rfft(double input[], int windowType) {

      Complex ans[] = transform(input, windowType);
      
      // Calculates rfft from fft
      int arrSize = ans.length;
      Complex rfftAns[] = new Complex[(arrSize>>1) + 1];
      
      for(int i = 0;i<rfftAns.length;i++)
    	  rfftAns[i] = ans[i];
      
      return rfftAns;
    }
 
    private Complex[] fft(Complex[] x) {
        int n = x.length;

        // base case
        if (n == 1) return new Complex[] { x[0] };

        // radix 2 Cooley-Tukey FFT
        if (n % 2 != 0) { throw new RuntimeException("n is not a power of 2"); }

        // fft of even terms
        Complex[] even = new Complex[n/2];
        for (int k = 0; k < n/2; k++) {
            even[k] = x[2*k];
        }
        Complex[] q = fft(even);

        // fft of odd terms
        Complex[] odd  = even;  // reuse the array
        for (int k = 0; k < n/2; k++) {
            odd[k] = x[2*k + 1];
        }
        Complex[] r = fft(odd);

        // combine
        Complex[] y = new Complex[n];
        for (int k = 0; k < n/2; k++) {
            double kth = -2 * k * Math.PI / n;
            Complex wk = new Complex(Math.cos(kth), Math.sin(kth));
            y[k]       = q[k].add(wk.mult(r[k]));
            y[k + n/2] = q[k].sub(wk.mult(r[k]));
        }
        return y;
    }
}