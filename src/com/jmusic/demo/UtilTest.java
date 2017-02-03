package com.jmusic.demo;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.jmusic.segment.Segment;
import com.jmusic.utils.Utils;
import com.jmusic.wave.Wave;

/**
 * @author Anshul Shah
 *
 */
public class UtilTest {

	/**
	 * @param args
	 */
	String filename;
	Wave wave;

	public UtilTest(String filename){
		this.filename = filename;
		this.wave = new Wave(filename);
	}
	
	public void Test1(int numSegments){
		System.out.println(Utils.getSegmentSize(wave.getAmplitudes().length, numSegments));
	}
	
	public void Test2(int segmentSize) {
		Segment[] segment = Utils.getSegments(wave, segmentSize, 0,"HAMMING");

		BufferedWriter writer = null;
        try {
            File logFile = new File("out/absAmp.txt");
            System.out.println("Writing to: " + logFile.getCanonicalPath());
            writer = new BufferedWriter(new FileWriter(logFile));
            
//            System.out.println(segment[0].amplitude.length);
//            for(int i = 0;i<segment[0].amplitude.length;i++)
//     		{
//            	writer.write(segment[0].amplitude[i]+System.lineSeparator());
//     		}
            
            System.out.println(segment[0].absFrequencies.length);
    		for(int i = 0;i<segment[0].absFrequencies.length;i++){
    			writer.write(segment[0].absFrequencies[i]+System.lineSeparator());
    		}
        } 
        catch (Exception e) {
            e.printStackTrace();
        } 
        finally {
            try {
                writer.close();
            } catch (Exception e) {}
        }
	}
}
