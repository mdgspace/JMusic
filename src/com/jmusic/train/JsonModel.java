package com.jmusic.train;

public class JsonModel {
	
	public String genre;
	public String songName;
	public double spectralMean;
	public double spectralVariance;
	public double spectralCentroid;
	public double rollOff;
	public double[] mfcc;
	public double[] chroma;
	public double zcr;
	
	public JsonModel(){}
	
	public JsonModel(String genre, String songName, double spectralMean, double spectralVariance,
			double spectralCentroid, double rollOff, double[] mfcc, double[] chroma, double zcr) {
		
		this.genre = genre;
		this.songName = songName;
		this.spectralMean = spectralMean;
		this.spectralVariance = spectralVariance;
		this.spectralCentroid = spectralCentroid;
		this.rollOff = rollOff;
		this.mfcc = mfcc;
		this.chroma = chroma;
		this.zcr = zcr;
	}
	
	
}
