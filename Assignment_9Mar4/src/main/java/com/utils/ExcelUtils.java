package com.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtils {
	
	public File getFile(String environment) {
		
		File file;
		
		if(environment.equals("DEV"))
		{
			file=new File(System.getProperty("user.dir")+"\\src\\test\\resources\\TestData_Dev.xlsx");
		}
		else if(environment.equals("QA")) {
			file=new File(System.getProperty("user.dir")+"\\src\\test\\resources\\TestData_QA.xlsx");
		}
		else if(environment.equals("UAT")) {
			file=new File(System.getProperty("user.dir")+"\\src\\test\\resources\\TestData_UAT.xlsx");
		}
		else
		{
			file=new File(System.getProperty("user.dir")+"\\src\\test\\resources\\TestData_PROD.xlsx");
		}
		
		return file;
	}

	public ArrayList<String> readExcel(String sheetName, String colName, String environment) throws IOException {
		
		//Method to read data from excel by taking sheetName and Column number

		File file=getFile(environment);
		int colNo = 0;

		FileInputStream inputStream =new FileInputStream(file);

		Workbook book=new XSSFWorkbook(inputStream);

		Sheet sheet=book.getSheet(sheetName);

		int rowCount=sheet.getLastRowNum()-sheet.getFirstRowNum();

		ArrayList<String> arr=new ArrayList<String>();
		
		Row row=sheet.getRow(0);

		for(int j=0;j<row.getLastCellNum();j++) {
			if(row.getCell(j).getStringCellValue().trim().equals(colName.trim()))
				colNo=j;
		}

		for(int i=1; i<=rowCount; i++) {
			//System.out.println(i);

			row=sheet.getRow(i);

			
			String data=row.getCell(colNo).getStringCellValue();

			//System.out.println(data);

			arr.add(data);
		}
		return arr;
	}



	public List<LinkedHashMap<String, String>> readExcel(String sheetName, String environment) throws IOException {
		
		//Method to read data from excel by taking sheetName

		File file=getFile(environment);

		FileInputStream inputStream =new FileInputStream(file);

		Workbook book=new XSSFWorkbook(inputStream);

		Sheet sheet=book.getSheet(sheetName);

		List<LinkedHashMap<String, String>> dataList = new ArrayList<>();

		int count=sheet.getLastRowNum()-sheet.getFirstRowNum();

		for(int i=1; i<=count; i++) {

			LinkedHashMap<String, String> data=new LinkedHashMap<>();

			Row r=sheet.getRow(i);

			for(int j=0;j<r.getLastCellNum(); j++) {
				String fieldName=sheet.getRow(0).getCell(j).getStringCellValue();
				String fieldValue=r.getCell(j).getStringCellValue();
				data.put(fieldName, fieldValue);
			}

			dataList.add(data);
		}
		return dataList;

	}


	public String readExcel(String sheetName, String colName, int rowNo, String environment) throws IOException {
			
		//Method to read data from excel by taking sheetName, Row number and Column number

		File file=getFile(environment);
		int colNo=0;

		FileInputStream inputStream =new FileInputStream(file);

		Workbook book=new XSSFWorkbook(inputStream);

		Sheet sheet=book.getSheet(sheetName);

		Row row=sheet.getRow(rowNo);
		
		for(int j=0;j<row.getLastCellNum();j++) {
			if(row.getCell(j).getStringCellValue().trim().equals(colName.trim()))
				colNo=j;
		}

		return row.getCell(colNo).getStringCellValue();
	}


}
