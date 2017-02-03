package com.jmusic.segment;

/**
 * Data structure to hold complex number and provide basic mathematical
 * functions for operations on them
 * 
 * @author Deepankar Agrawal
 *
 */
public class Complex {
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
    
    public double abs(){
    	return Math.sqrt(this.re*this.re + this.im*this.im);
    }
 
    @Override
    public String toString() {
        return String.format("%f + j%f", re, im);
    }
}
