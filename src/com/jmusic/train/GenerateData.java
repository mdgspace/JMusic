package com.jmusic.train;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.jmusic.Segment;
import com.jmusic.utils.Utils;
import com.jmusic.utils.WindowFunction;
import com.jmusic.wave.Wave;

public class GenerateData {
	
	public static final String DATA_PATH = "../../Projects/Genre-Classifier/data";
	
	public static final String [] genres = {"blues", "classical", 
			"country", "disco", "hiphop", "jazz", "metal", 
			"pop", "rock"};
	
	private static ArrayList<JsonModel> models = new ArrayList<>();
	
	public static final int SEGMENT_LENGTH = 65536;
	
	
	public static void readSong(String filePath, String fileName, String genre){
		Wave wave = new Wave(filePath);
		Segment[] segments = Utils.getSegments(wave, SEGMENT_LENGTH, 0, WindowFunction.HAMMING);
		
		for(Segment segment : segments){
			JsonModel model = new JsonModel(genre, fileName, segment.spectralMean(),
					segment.spectralVariance(), segment.spectralCentroid(),
					segment.rollOff(), segment.mfcc(), segment.chroma(),
					segment.zcr());
			
			models.add(model);
		}
		
	}
	
	public static void writeJsonToFile(String jsonData){
		FileWriter fileWriter = null;
		try {
			fileWriter = new FileWriter("feature_data.json");
			fileWriter.write(jsonData);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				fileWriter.flush();
				fileWriter.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	
	public static void main(String [] args){
		
		// construct filepath to read songs 
		for(int i=0; i<genres.length;i++){
			for(int j=0; j<100;j++){
				String fileName = genres[i]+"."+String.format("%05d", j)+".wav";
				String filePath = DATA_PATH +"/"+ genres[i]+"/"+ fileName;
				
				System.out.println("Reading song "+ fileName);
				readSong(filePath, fileName, genres[i]);
			}
		}
		
		System.out.println("Starting to write to data.json");
		final Gson gson = new Gson();
		String s = gson.toJson(models);
		
		writeJsonToFile(s);
		System.out.println("file written");
		
	}

}
