package com.jmusic.test;

public class Test2 {

	public static void main(String[] args) {
		String filename = "audio_work/pop.00000.wav";
		
		UtilTest ut = new UtilTest(filename);
		ut.Test1(6);
		ut.Test2(131072);
	}

}
