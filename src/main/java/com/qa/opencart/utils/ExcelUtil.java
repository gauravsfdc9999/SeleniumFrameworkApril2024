package com.qa.opencart.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelUtil {
	private static final String TEST_DATA_SHEET_PATH = "./src/test/resources/testdata/opencartdata.xlsx";
	
	private static Workbook book;
	private static Sheet sheet;
	
	public static Object[][] getTestData(String sheetName) {
		System.out.println("getting data from sheet :"+sheetName);
		Object data[][]=null;
		try {
			FileInputStream ip = new FileInputStream(TEST_DATA_SHEET_PATH);
			book= WorkbookFactory.create(ip);
			sheet = book.getSheet(sheetName);
			//initializing data
			data = new Object[sheet.getLastRowNum()][sheet.getRow(0).getLastCellNum()];
			for(int row=0;row<sheet.getLastRowNum();row++) {
				for(int col=0;col<sheet.getRow(0).getLastCellNum();col++) {
					data[row][col]=sheet.getRow(row+1).getCell(col).toString();
				}
				
			}
			
		} catch (IOException | InvalidFormatException e ) {
			e.printStackTrace();
		}
		
		return data;
	}
	
}
