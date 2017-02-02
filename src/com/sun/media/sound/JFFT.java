package com.sun.media.sound;
import static java.lang.Math.*;

public class JFFT {
 
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
    
    /**
     * 
     * @param input input data to be transformed
     * @return transformed data
     */
    public double[] transform(double input[]) {
 
        Complex[] cinput = new Complex[input.length];
        for (int i = 0; i < input.length; i++)
            cinput[i] = new Complex(input[i], 0.0);
 
        fft(cinput);
        
        int pos = 0;
        double ans[] = new double[2*cinput.length];
        
        for (Complex c : cinput) {
        	ans[pos] = c.re;
        	ans[pos+1] = c.im;
        	pos+=2;
        }
        return ans;
    }
    
    /**
     * 
     * @param input data to be transformed
     * @return transformed data
     * 
     * When the DFT is computed for purely real input, the output is
       Hermitian-symmetric, i.e. the negative frequency terms are just the complex
       conjugates of the corresponding positive-frequency terms, and the
       negative-frequency terms are therefore redundant.  This function does not
       compute the negative frequency terms, and the length of the transformed
       axis of the output is therefore ``n//2 + 1``.
     */
    public double[] transform_rfft(double input[]) {

      double ans[] = transform(input);
      
      // Calculates rfft from fft
      int arrSize = ans.length;
      double rfftAns[] = new double[(arrSize>>1) + 2];
      
      for(int i = 0;i<rfftAns.length;i++)
    	  rfftAns[i] = ans[i];
      
      return rfftAns;
  }
}
 
class Complex {
    public final double re;
    public final double im;
 
    public Complex() {
        this(0, 0);
    }
 
    public Complex(double r, double i) {
        re = r;
        im = i;
    }
 
    public Complex add(Complex b) {
        return new Complex(this.re + b.re, this.im + b.im);
    }
 
    public Complex sub(Complex b) {
        return new Complex(this.re - b.re, this.im - b.im);
    }
 
    public Complex mult(Complex b) {
        return new Complex(this.re * b.re - this.im * b.im,
                this.re * b.im + this.im * b.re);
    }
 
    @Override
    public String toString() {
        return String.format("(%f,%f)", re, im);
    }
}