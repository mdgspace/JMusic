package com.jmusic.math;
import static java.lang.Math.*;

import com.jmusic.segment.Complex;

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
    public Complex[] transform(double input[]) {
 
        Complex[] cinput = new Complex[input.length];
        for (int i = 0; i < input.length; i++)
            cinput[i] = new Complex(input[i], 0.0);
 
        fft(cinput);
        
        return cinput;
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
    public Complex[] transform_rfft(double input[]) {

      Complex ans[] = transform(input);
      
      // Calculates rfft from fft
      int arrSize = ans.length;
      Complex rfftAns[] = new Complex[(arrSize>>1) + 1];
      
      for(int i = 0;i<rfftAns.length;i++)
    	  rfftAns[i] = ans[i];
      
      return rfftAns;
  }
 
    private int bitReverse(int n, int bits) {
        int reversedN = n;
        int count = bits - 1;
 
        n >>= 1;
        while (n > 0) {
            reversedN = (reversedN << 1) | (n & 1);
            count--;
            n >>= 1;
        }
 
        return ((reversedN << count) & ((1 << bits) - 1));
    }
 
    private void fft(Complex[] buffer) {
 
        int bits = (int) (log(buffer.length) / log(2));
        for (int j = 1; j < buffer.length / 2; j++) {
 
            int swapPos = bitReverse(j, bits);
            Complex temp = buffer[j];
            buffer[j] = buffer[swapPos];
            buffer[swapPos] = temp;
        }
 
        for (int N = 2; N <= buffer.length; N <<= 1) {
            for (int i = 0; i < buffer.length; i += N) {
                for (int k = 0; k < N / 2; k++) {
 
                    int evenIndex = i + k;
                    int oddIndex = i + k + (N / 2);
                    Complex even = buffer[evenIndex];
                    Complex odd = buffer[oddIndex];
 
                    double term = (-2 * PI * k) / (double) N;
                    Complex exp = (new Complex(cos(term), sin(term)).mult(odd));
 
                    buffer[evenIndex] = even.add(exp);
                    buffer[oddIndex] = even.sub(exp);
                }
            }
        }
    }
    
}