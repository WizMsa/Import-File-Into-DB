package com.mnesa.excel.helpers;

import com.mnesa.excel.Model.Product;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

@Slf4j
public class ExcelHelper {

    public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";

    public static List<Product> savingExcelToDb(InputStream inputStream) {
        try {
            Product product = new Product();
            Workbook workbook = new XSSFWorkbook(inputStream);
            Sheet sheet = workbook.getSheetAt(workbook.getNumberOfSheets() - 1);
            Iterator<Row> rows = sheet.iterator();
            List<Product> list = new ArrayList<>();
            int rowNumber = 0;
            while (rows.hasNext()) {
                Row currentRow = rows.next();
                if (rowNumber == 0) {
                    rowNumber++;
                    continue;
                }
                int cellCount = 0;
                Iterator<Cell> cells = currentRow.cellIterator();
                while (cells.hasNext()) {
                    log.info("Total cells : {}", currentRow.getLastCellNum());
                    Cell currentCell = cells.next();
                    switch (cellCount){
                        case 0:
                    switch (currentCell.getCellType()) {
                        case STRING, NUMERIC, FORMULA, BOOLEAN -> product.setPartNumber("044270K020");
                    }
                        case 1:
                            switch (currentCell.getCellType()){
                                case FORMULA, NUMERIC,BOOLEAN,STRING -> product.setPartName(currentCell.getStringCellValue());
                            }
                        case 2:
                            switch (currentCell.getCellType()){
                                case FORMULA, STRING,NUMERIC ,BOOLEAN-> product.setQuantity(Long.parseLong(currentCell.getStringCellValue()));
                            }
                        case 3:
                            switch (currentCell.getCellType()){
                                case FORMULA, NUMERIC,BOOLEAN,STRING -> product.setPrice(currentCell.getNumericCellValue());
                            }
                        case 4:
                            switch (currentCell.getCellType()){
                                case FORMULA,STRING,BOOLEAN,NUMERIC -> product.setAmount(currentCell.getNumericCellValue());
                            }
                    }
                    cellCount++;
                }

                log.info("Cell type : {}",currentRow.getCell(4).getCellType());
                list.add(product);
            }
            workbook.close();
            return list;
        } catch (IOException e) {
            log.error("Failed To Add List  : {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public static boolean fileFormat(MultipartFile file) {
        return Objects.equals(file.getContentType(), TYPE);
    }
}
