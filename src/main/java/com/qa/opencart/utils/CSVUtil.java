package com.qa.opencart.utils;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

public class CSVUtil {
	
	private static final String CSV_PATH="./src/test/resources/testdata/";
	private static FileReader file;
	private static CSVReader reader;
	private static List<String[]> rows;
	
	
	public static Object[][] csvData(String csvName) {
		String csvFile = CSV_PATH+csvName+".csv";
		try {
			file = new FileReader(csvFile);
			reader = new CSVReader(file);
			rows= reader.readAll();
			//reader.close();			
			
		} catch (IOException | CsvException e) {
			e.printStackTrace();
		}
		finally {
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		Object [][] data = new Object[rows.size()-1][];
		for(int row=0;row<rows.size()-1;row++) {
			data[row]=rows.get(row+1);
		}
		
		return data;
	}

}
