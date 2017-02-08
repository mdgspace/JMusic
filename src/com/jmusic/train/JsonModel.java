package com.jmusic.train;

public class JsonModel {
	
	public String genre;
	public String song_name;
	public double mean;
	public double variance;
	public double centroid;
	public double rolloff;
	public double[] mfcc;
	public double[] chroma;
	public double zcr;
	
	public JsonModel(){}
	
	public JsonModel(String genre, String songName, double spectralMean, double spectralVariance,
			double spectralCentroid, double rollOff, double[] mfcc, double[] chroma, double zcr) {
		
		this.genre = genre;
		this.song_name = songName;
		this.mean = spectralMean;
		this.variance = spectralVariance;
		this.centroid = spectralCentroid;
		this.rolloff = rollOff;
		this.mfcc = mfcc;
		this.chroma = chroma;
		this.zcr = zcr;
	}
	
	
}
