package utils.dataManager;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import utils.logs.LogsManager;

import java.io.FileInputStream;

public class ExcelReader {
    private static final String TEST_DATA_PATH="src/test/resources/testData";

    public static String getExcelData(String fileName, String sheetName, int rowNum, int colNum){
        XSSFWorkbook workbook;
        XSSFSheet sheet;

        String cellData;
        try{
            workbook=new XSSFWorkbook( TEST_DATA_PATH+fileName);
            sheet=workbook.getSheet(sheetName);
            cellData=sheet.getRow(rowNum).getCell(colNum).getStringCellValue();
            return cellData;
        }
        catch (Exception e){
            LogsManager.error("Failed to get Excel data from file: ",fileName,e.getMessage());
            return "";
        }
    }
}
