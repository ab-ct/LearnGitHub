package com.generic.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.imageio.stream.FileImageInputStream;

import org.apache.log4j.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class WriteExcel {
	private File excelFile;
	private Workbook workbook;
	private Sheet sheet;
	private Row row;
	private Cell cell;
	private FileInputStream fin;
	private Logger logger = Logger.getLogger("log");
	public WriteExcel(File excelFile) throws InvalidFormatException, IOException{
		this.excelFile = excelFile;
		fin = new FileInputStream(excelFile);
		this.workbook = WorkbookFactory.create(fin);
		this.sheet = this.workbook.getSheetAt(0);
		
	}
	public void switchToSheet(int sheetNumber){
		logger.info("Switching to sheet number "+sheetNumber);
		sheet = workbook.getSheetAt(sheetNumber);
	}
	public void switchToSheet(String sheetName){
		logger.info("Switching to sheet "+sheetName);
		sheet = workbook.getSheet(sheetName);
		
	}
	public Workbook getWorkbook(){
		return workbook;
	}
	
	public void writeRow(int rowNumber, Object[] obj){
		logger.info("Writing object to "+sheet.getSheetName()+" : row number "+rowNumber);
		row = sheet.getRow(rowNumber);
		if(row==null){
			logger.info("Row is empty or doesn't exist. Creating new row.");
			sheet.createRow(rowNumber);
		}
		for(int i=0; i<obj.length;i++){
			cell = row.getCell(i);
			if(cell==null)
				row.createCell(i);
			if(obj[i] instanceof String)
				cell.setCellValue((String)obj[i]);
			if(obj[i] instanceof Double)
				cell.setCellValue((Double)obj[i]);
			if(obj[i] instanceof Boolean)
				cell.setCellValue((Boolean)obj[i]);
			if(obj[i] instanceof Date){
				cell.setCellValue((Date)obj[i]);
			if(obj[i]==null){
				System.out.println("Obj is null");
				cell.setCellValue("");
			}
			}
		}
	}
	public void writeRow(int rowNumber, ArrayList<Object> obj){
		Object[] objarr = obj.toArray();
		writeRow(rowNumber, objarr);
	}
	public void writeToFile(File file){
		FileOutputStream fout = null;
		try {
			logger.info("Writing workbook to file: "+file.getAbsolutePath());
			fout = new FileOutputStream(file);
			workbook.write(fout);
			fout.close();
		} 
		catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	public void writeToFile(){
		
		try {
			logger.info("Writing to file: "+excelFile.getAbsolutePath());
			fin.close();
			FileOutputStream fout = new FileOutputStream(excelFile);
			workbook.write(fout);
			fout.close();
		}
		catch(FileNotFoundException e){
			logger.info("File not found: "+excelFile.getAbsolutePath());
			e.printStackTrace();
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}
}
