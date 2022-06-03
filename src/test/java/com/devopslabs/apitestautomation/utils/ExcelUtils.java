package com.devopslabs.apitestautomation.utils;

import java.io.FileInputStream;

import lombok.SneakyThrows;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtils {

  private final XSSFWorkbook excelWorkbook;

  private final XSSFSheet excelWorkSheet;

  private XSSFCell cell;

  @SneakyThrows
  public ExcelUtils(String excelPath, String sheetName) {
    FileInputStream excelFile = new FileInputStream(excelPath);
    excelWorkbook = new XSSFWorkbook(excelFile);
    excelWorkSheet = excelWorkbook.getSheet(sheetName);
  }

  public String getCellData(int rowNum, int colNum) {
    cell = excelWorkSheet.getRow(rowNum).getCell(colNum);
    DataFormatter formatter = new DataFormatter();
    String cellData = formatter.formatCellValue(cell);
    return cellData;
  }
}
