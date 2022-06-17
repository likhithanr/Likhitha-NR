package com.n26.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Excel {

    public static FileInputStream fis = null;
    public static XSSFWorkbook workbook = null;
    public static XSSFSheet sheet = null;
    public static XSSFRow row = null;
    public static XSSFCell cell = null;
 
    /** 
     * @param sheetName
     * @param colName
     * @param rowNum
     * @return String
     * @throws IOException
     */
    // method to get row data for specified column
    public static String getColumnData(String sheetName, String colName, int rowNum) throws IOException {
        int col_Num = -1;
        try {
            fis = new FileInputStream(
                    System.getProperty("user.dir") + "/" + ConfigManager.gsAutomationAutoSupportDocs + "TestData.xlsx");
            workbook = new XSSFWorkbook(fis);
            fis.close();
            sheet = workbook.getSheet(sheetName);
            row = sheet.getRow(0);
            for (int i = 0; i < row.getLastCellNum(); i++) {
                if (row.getCell(i).getStringCellValue().trim().equals(colName.trim())) {
                    col_Num = i;
                    break;
                }
            }
            row = sheet.getRow(rowNum);
            cell = row.getCell(col_Num);
            if (cell.getCellType() == CellType.STRING)
                return cell.getStringCellValue();
            else if (cell.getCellType() == CellType.NUMERIC || cell.getCellType() == CellType.FORMULA) {
                String cellValue = String.valueOf(cell.getNumericCellValue());
                if (DateUtil.isCellDateFormatted(cell)) {
                    DateFormat df = new SimpleDateFormat("dd/MM/yy");
                    Date date = cell.getDateCellValue();
                    cellValue = df.format(date);
                }
                return cellValue;
            } else if (cell.getCellType() == CellType.BLANK)
                return "";
            else
                return String.valueOf(cell.getBooleanCellValue());
        } catch (Exception e) {
            e.printStackTrace();
            return "row " + rowNum + " or column " + col_Num + " does not exist  in Excel";
        } finally {
            fis.close();
        }
    }

    /** 
     * @param sheetName
     * @param rowCount
     * @return Map<String, String>
     */
    // method to get all the clumn data for specified row.
    public static Map<String, String> getTestData(String sheetName, int rowCount) {
        Map<String, String> excelData = new HashMap<String, String>();
        try {

            // loading TestData sheet from the specified location
            fis = new FileInputStream(
                    System.getProperty("user.dir") + "/" + ConfigManager.gsAutomationAutoSupportDocs + "TestData.xlsx");

            // loading workbook sheet
            workbook = new XSSFWorkbook(fis);
            fis.close();
            sheet = workbook.getSheet(sheetName);
            row = sheet.getRow(rowCount);
            String key;
            String value = "";
            for (int j = 1; j < row.getLastCellNum(); j++) {

                // get the column name and storig it in key
                cell = sheet.getRow(0).getCell(j);
                key = cell.getStringCellValue();
                cell = sheet.getRow(rowCount).getCell(j);
                if (cell.getCellType() == CellType.STRING)
                    value = cell.getStringCellValue();
                else if (cell.getCellType() == CellType.NUMERIC) {
                    value = String.valueOf((int) cell.getNumericCellValue());
                }
                excelData.put(key, value);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return excelData;
    }
}
