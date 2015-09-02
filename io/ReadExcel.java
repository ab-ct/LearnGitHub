package com.generic.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ReadExcel {
	
	private File excelFile;
	private Workbook workbook;
	private Sheet sheet;
	private Logger logger = Logger.getLogger("log");
	
	public ReadExcel(File excelFile) throws EncryptedDocumentException, InvalidFormatException, FileNotFoundException, IOException
	{
		this.excelFile = excelFile;
		this.workbook = WorkbookFactory.create(new FileInputStream(excelFile));
		this.workbook = WorkbookFactory.create(excelFile);
		this.sheet = workbook.getSheetAt(0);
		
	}
	
	public void switchToSheet(int sheetNumber){
		logger.info("Switching to sheet number "+sheetNumber);
		sheet = workbook.getSheetAt(sheetNumber);
	}
	public void switchToSheet(String sheetName){
		logger.info("Switching to sheet "+sheetName);
		sheet = workbook.getSheet(sheetName);
		
	}
	public Row getRow(String sheetName, int rowNumber){
		logger.info("Getting row number "+rowNumber+" in sheet "+sheetName);
		return workbook.getSheet(sheetName).getRow(rowNumber);
	}
	
	public Row getRow(int sheetNumber, int rowNumber){
		logger.info("Getting row number "+rowNumber+" in sheet number"+sheetNumber);
		return workbook.getSheetAt(sheetNumber).getRow(rowNumber);
	}
	public Row getRow(int rowNumber){
		logger.info("Getting row number "+rowNumber+" in sheet "+sheet);
		return sheet.getRow(rowNumber);
	}
	
	public Cell getCell(String sheetName, int rowNumber, int cellNumber){
		logger.info("Reading cell number  "+cellNumber+" in row "+rowNumber+" of "+sheetName+" sheet");
		return workbook.getSheet(sheetName).getRow(rowNumber).getCell(cellNumber);
	}
	
	public Cell getCell(int sheetNumber, int rowNumber, int cellNumber){
		logger.info("Reading cell number  "+cellNumber+" in row "+rowNumber+" of "+sheetNumber+" sheet");
		return workbook.getSheetAt(sheetNumber).getRow(rowNumber).getCell(cellNumber);
	}
	public Cell getCell(int rowNumber, int cellNumber){
		logger.info("Reading cell number  "+cellNumber+" in row "+rowNumber+" of "+sheet+" sheet");
		return sheet.getRow(rowNumber).getCell(cellNumber);
	}
	public int getStringOccuranceInColumn(String sheetName, int column, String searchString){
		logger.info("Scanning "+ column + " column in "+sheetName+" sheet for occurances of "+searchString);
		int occurance = 0;
		Iterator<Row> ri = workbook.getSheet(sheetName).rowIterator();
		Row row = null;
		Cell cell = null;
		while(ri.hasNext()){
			row = ri.next();
			cell = row.getCell(column);
			if(cell==null)
				continue;
			if(cell.toString().equals(searchString))
				occurance++;
		}
		return occurance;
	}
	
	public int getStringOccurancesInColumn(int sheetNumber, int column, String searchString){
		logger.info("Scanning "+ column + " column in "+sheetNumber+" sheet for occurances of "+searchString);
		int occurance = 0;
		Iterator<Row> ri = workbook.getSheetAt(sheetNumber).rowIterator();
		Row row = null;
		Cell cell = null;
		while(ri.hasNext()){
			row = ri.next();
			cell = row.getCell(column);
			if(cell==null)
				continue;
			if(cell.toString().equals(searchString))
				occurance++;
		}
		return occurance;
	}
	public int getStringOccuranceInColumn(int column, String searchString){
		logger.info("Scanning "+ column + " column in "+sheet+" sheet for occurances of "+searchString);
		int occurance = 0;
		Iterator<Row> ri = sheet.rowIterator();
		Row row = null;
		Cell cell = null;
		while(ri.hasNext()){
			row = ri.next();
			cell = row.getCell(column);
			if(cell==null)
				continue;
			if(cell.toString().equals(searchString))
				occurance++;
		}
		return occurance;
	}
	public Date getEarliestDate(String sheetName, int column){
		Date earliestDate = null;
		Iterator<Row> ri = workbook.getSheet(sheetName).rowIterator();
		Row row = null;
		Cell cell = null;
		
		while(ri.hasNext()){
			row = ri.next();
			cell = row.getCell(column);
			
			if(cell==null || !DateUtil.isCellDateFormatted(cell) || !DateUtil.isCellInternalDateFormatted(cell)){
				//logger.info("Cell is empty.");
				continue;
			}
			else if(earliestDate==null){
				earliestDate = cell.getDateCellValue();
				continue;
			}
			else if(earliestDate!=null && cell.getDateCellValue()!=null){
				 if(earliestDate.after(cell.getDateCellValue()))
					 earliestDate = cell.getDateCellValue();
			}
		}
		
		return earliestDate;
	}
	
	public Date getEarliestDate(int sheetNumber, int column){
		Date earliestDate = null;
		Iterator<Row> ri = workbook.getSheetAt(sheetNumber).rowIterator();
		Row row = null;
		Cell cell = null;
		
		while(ri.hasNext()){
			row = ri.next();
			cell = row.getCell(column);
			
			if(cell==null || !DateUtil.isCellDateFormatted(cell) || !DateUtil.isCellInternalDateFormatted(cell)){
				//logger.info("Cell is empty.");
			continue;
		}
			else if(earliestDate==null){
				earliestDate = cell.getDateCellValue();
				continue;
			}
			else if(earliestDate!=null && cell.getDateCellValue()!=null){
				 if(earliestDate.after(cell.getDateCellValue()))
					 earliestDate = cell.getDateCellValue();
			}
		}
		
		return earliestDate;
	}
	
	public Date getEarliestDate(int column){
		Date earliestDate = null;
		Iterator<Row> ri = sheet.rowIterator();
		Row row = null;
		Cell cell = null;
		
		while(ri.hasNext()){
			row = ri.next();
			cell = row.getCell(column);
			
			if(cell==null || !DateUtil.isCellDateFormatted(cell) || !DateUtil.isCellInternalDateFormatted(cell)){
				//logger.info("Cell is empty.");
			continue;
		}
			else if(earliestDate==null){
				earliestDate = cell.getDateCellValue();
				continue;
			}
			else if(earliestDate!=null && cell.getDateCellValue()!=null){
				 if(earliestDate.after(cell.getDateCellValue()))
					 earliestDate = cell.getDateCellValue();
			}
		}
		
		return earliestDate;
	}
	public Date getLatestDate(String sheetName, int column){
		Date latestDate = null;
		Iterator<Row> ri = workbook.getSheet(sheetName).rowIterator();
		Row row = null;
		Cell cell = null;
		
		while(ri.hasNext()){
			row = ri.next();
			cell = row.getCell(column);
			
			if(cell==null || !DateUtil.isCellDateFormatted(cell) || !DateUtil.isCellInternalDateFormatted(cell)){
				logger.info("Cell is empty.");
			continue;
		}
			else if(latestDate==null){
				latestDate = cell.getDateCellValue();
				continue;
			}
			else if(latestDate!=null && cell.getDateCellValue()!=null){
				 if(latestDate.before(cell.getDateCellValue()))
				latestDate = cell.getDateCellValue();
			}
		}
		
		return latestDate;
	}
	
	public Date getLatestDate(int sheetNumber, int column){
		Date latestDate = null;
		Iterator<Row> ri = workbook.getSheetAt(sheetNumber).rowIterator();
		Row row = null;
		Cell cell = null;
		
		while(ri.hasNext()){
			row = ri.next();
			cell = row.getCell(column);
			
			if(cell==null || !DateUtil.isCellDateFormatted(cell) || !DateUtil.isCellInternalDateFormatted(cell)){
				logger.info("Cell is empty.");
			continue;
		}
			else if(latestDate==null){
				latestDate = cell.getDateCellValue();
				continue;
			}
			else if(latestDate!=null && cell.getDateCellValue()!=null){
				 if(latestDate.before(cell.getDateCellValue()))
				latestDate = cell.getDateCellValue();
			}
		}
		return latestDate;
	}
	public Date getLatestDate(int column){
		Date latestDate = null;
		Iterator<Row> ri = sheet.rowIterator();
		Row row = null;
		Cell cell = null;
		
		while(ri.hasNext()){
			row = ri.next();
			cell = row.getCell(column);
			
			if(cell==null || !DateUtil.isCellDateFormatted(cell) || !DateUtil.isCellInternalDateFormatted(cell)){
				logger.info("Cell is empty.");
			continue;
		}
			else if(latestDate==null){
				latestDate = cell.getDateCellValue();
				continue;
			}
			else if(latestDate!=null && cell.getDateCellValue()!=null){
				 if(latestDate.before(cell.getDateCellValue()))
				latestDate = cell.getDateCellValue();
			}
		}
		return latestDate;
	}
	
	public Double getMinimumNumber(String sheetName, int column){
		sheet = workbook.getSheet(sheetName);
		Iterator<Row> ir = sheet.rowIterator();
		Row row = null;
		Cell cell = null;
		Double min = null;
		while(ir.hasNext()){
			row = ir.next();
			cell = row.getCell(column);
			if(cell==null || cell.getCellType()!=Cell.CELL_TYPE_NUMERIC || DateUtil.isCellDateFormatted(cell))
				continue;
			else if(min==null)
				min=cell.getNumericCellValue();
			else if(cell.getNumericCellValue()<min)
				min=cell.getNumericCellValue();
		}
		if(min==null)
			logger.info("Column "+column+" of sheet "+sheetName+" doesn't have any number.");
		return min;
	}
	public double getMinimumNumber(int sheetNumber, int column){
		sheet = workbook.getSheetAt(sheetNumber);
		Iterator<Row> ir = sheet.rowIterator();
		Row row = null;
		Cell cell = null;
		Double min = null;
		while(ir.hasNext()){
			row = ir.next();
			cell = row.getCell(column);
			if(cell==null || cell.getCellType()!=Cell.CELL_TYPE_NUMERIC || DateUtil.isCellDateFormatted(cell))
				continue;
			else if(min==null)
				min=cell.getNumericCellValue();
			else if(cell.getNumericCellValue()<min)
				min=cell.getNumericCellValue();
		}
		if(min==null)
			logger.info("Column "+column+" of sheet #"+sheetNumber+" doesn't have any number.");
		return min;
	}
	public double getMinimumNumber(int column){
		Iterator<Row> ir = sheet.rowIterator();
		Row row = null;
		Cell cell = null;
		Double min = null;
		while(ir.hasNext()){
			row = ir.next();
			cell = row.getCell(column);
			if(cell==null || cell.getCellType()!=Cell.CELL_TYPE_NUMERIC || DateUtil.isCellDateFormatted(cell))
				continue;
			else if(min==null)
				min=cell.getNumericCellValue();
			else if(cell.getNumericCellValue()<min)
				min=cell.getNumericCellValue();
		}
		if(min==null)
			logger.info("Column "+column+" of sheet "+sheet+" doesn't have any number.");
		return min;
	}
	public double getMaximumNumber(String sheetName, int column){
		sheet = workbook.getSheet(sheetName);
		Iterator<Row> ir = sheet.rowIterator();
		Row row = null;
		Cell cell = null;
		Double min = null;
		while(ir.hasNext()){
			row = ir.next();
			cell = row.getCell(column);
			if(cell==null || cell.getCellType()!=Cell.CELL_TYPE_NUMERIC || DateUtil.isCellDateFormatted(cell))
				continue;
			else if(min==null)
				min=cell.getNumericCellValue();
			else if(cell.getNumericCellValue()>min)
				min=cell.getNumericCellValue();
		}
		if(min==null){
			logger.info("Column "+column+" of sheet "+sheetName+" doesn't have any number.");
		}	
		return min;
	}
	public double getMaximumNumber(int sheetNumber, int column){
		sheet = workbook.getSheetAt(sheetNumber);
		Iterator<Row> ir = sheet.rowIterator();
		Row row = null;
		Cell cell = null;
		Double min = null;
		while(ir.hasNext()){
			row = ir.next();
			cell = row.getCell(column);
			if(cell==null || cell.getCellType()!=Cell.CELL_TYPE_NUMERIC || DateUtil.isCellDateFormatted(cell))
				continue;
			else if(min==null)
				min=cell.getNumericCellValue();
			else if(cell.getNumericCellValue()>min)
				min=cell.getNumericCellValue();
		}
		if(min==null)
			logger.info("Column "+column+" of sheet "+sheetNumber+" doesn't have any number.");
		return min;
	}
	public double getMaximumNumber(int column){
		Iterator<Row> ir = sheet.rowIterator();
		Row row = null;
		Cell cell = null;
		Double min = null;
		while(ir.hasNext()){
			row = ir.next();
			cell = row.getCell(column);
			if(cell==null || cell.getCellType()!=Cell.CELL_TYPE_NUMERIC || DateUtil.isCellDateFormatted(cell))
				continue;
			else if(min==null)
				min=cell.getNumericCellValue();
			else if(cell.getNumericCellValue()>min)
				min=cell.getNumericCellValue();
		}
		if(min==null)
			logger.info("Column "+column+" of sheet "+sheet+" doesn't have any number.");
		return min;
	}
	public Object[] getRowAsObjectArray(int rowNumber){
		Object[] objarr = getRowAsArrayList(rowNumber).toArray();
		return objarr;
	}
	public ArrayList<Object> getRowAsArrayList(int rowNumber){
		Row row = getRow(rowNumber);
		//Iterator<Cell> cellIterator = row.cellIterator();
		ArrayList<Object> al = new ArrayList<>();
	   
		for(Cell cell : row){
			if(cell.getCellType()==Cell.CELL_TYPE_STRING)
				al.add(cell.getStringCellValue());
			if (cell.getCellType()==Cell.CELL_TYPE_NUMERIC)
				al.add(cell.getNumericCellValue());
			if(cell.getCellType()==Cell.CELL_TYPE_BOOLEAN)
				al.add(cell.getBooleanCellValue());
			}
		return al;
	}

	public Map<Integer,String> getColumn(int column){
		Map<Integer, String> colpair = new HashMap<>();
		Iterator<Row> rowit = sheet.rowIterator();
		while(rowit.hasNext()){
			Row row = rowit.next();
			System.out.println(row);
			colpair.put(row.getRowNum(),row.getCell(column).toString());
		}
		return colpair;
	}
	public int getcolumncount(String sheetName,int rowNumber)
	{
		switchToSheet(sheetName);
		int coloumcount=(int)sheet.getRow(rowNumber).getPhysicalNumberOfCells();
		return coloumcount;
	}
	public int getrowcount(String sheetName)
	{
		switchToSheet(sheetName);
		int rowcount=sheet.getPhysicalNumberOfRows();
		return rowcount;
	}
	public ArrayList getrowvalue(String sheetName,int rowNumber)
	{
		ArrayList list = new ArrayList<>();
		switchToSheet(sheetName);
		for(int i=0;i<getcolumncount(sheetName,rowNumber);i++)
		{
			Cell cell = sheet.getRow(rowNumber).getCell(i);
			if(cell!=null)
			{
				if(Cell.CELL_TYPE_STRING==cell.getCellType())
				{
					    list.add(cell.getStringCellValue());
				}
				if (cell.getCellType()==Cell.CELL_TYPE_NUMERIC)
				{
					list.add((int)cell.getNumericCellValue());
				}
				if(cell.getCellType()==Cell.CELL_TYPE_BOOLEAN)
				{
					list.add(cell.getBooleanCellValue());
				}
			}
		}
		return list;
	}
	
	
}