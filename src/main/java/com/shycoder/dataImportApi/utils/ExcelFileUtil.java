package com.shycoder.dataImportApi.utils;

import com.shycoder.dataImportApi.entity.Job;
import com.shycoder.dataImportApi.entity.Product;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class ExcelFileUtil {
    private static final Logger logger = LoggerFactory.getLogger(ExcelFileUtil.class);

    public List<Product> extractProducts(InputStream is, Job job) {
        try {
            Workbook workbook = new XSSFWorkbook(is);

            Sheet sheet = workbook.getSheet(Constants.SHEET);
            Iterator<Row> rows = sheet.iterator();

            List<Product> products = new ArrayList<>();

            // skipping header
            if(rows.hasNext()){
                rows.next();
            }
            int rowNumber = 1;
            boolean atLeastOneRowFailed = false;
            String message = "";
            while (rows.hasNext()) {
                try {
                    Row currentRow = rows.next();
                    Product product = extractProduct(currentRow);
                    product.setJob(job);
                    products.add(product);
                }catch (Exception e){
                    //in case of an exception in one row we are ignoring only that row
                    atLeastOneRowFailed = true;
                    message = message + "Error while processing " + rowNumber + "th row;";
                    logger.error("Error while processing {0}th row. Error message: {1}",rowNumber, e);
                }
                rowNumber++;
            }

            workbook.close();

            //setting job status message for saving in database.
            if(atLeastOneRowFailed) {
                job.setStatus(Constants.FAILED);
                job.setMessage(message);
            }else {
                job.setStatus(Constants.COMPLETED);
            }

            return products;
        } catch (IOException e) {
            job.setStatus(Constants.FAILED);
            job.setMessage("Error while parsing excel file");
            logger.error("Error while parsing excel file. Error Message: {0}", e);
            throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
        }
    }

    public Product extractProduct(Row row) {
        Iterator<Cell> cellsInRow = row.iterator();

        Product product = new Product();

        int cellIdx = 0;
        while (cellsInRow.hasNext()) {
            Cell currentCell = cellsInRow.next();

            switch (cellIdx) {
                case 0:
                    product.setEan((long) currentCell.getNumericCellValue());
                    break;

                case 1:
                    product.setName(currentCell.getStringCellValue());
                    break;

                case 2:
                    product.setH1(currentCell.getStringCellValue());
                    break;

                case 3:
                    product.setSecondName(currentCell.getStringCellValue());
                    break;

                case 4:
                    product.setBrand(currentCell.getStringCellValue());
                    break;
                case 5:
                    product.setAuthor(currentCell.getStringCellValue());
                    break;
                case 6:
                    product.setOtherCategory(currentCell.getStringCellValue());
                    break;
                case 7:
                    product.setMainCategory(currentCell.getStringCellValue());
                    break;
                case 8:
                    product.setAttributeCategories(currentCell.getStringCellValue());
                    break;
                case 9:
                    product.setImageUrl(currentCell.getStringCellValue());
                    break;
                case 10:
                    product.setPageLink(currentCell.getStringCellValue());
                    break;
                case 11:
                    product.setMetaTitle(currentCell.getStringCellValue());
                    break;
                case 12:
                    product.setMetaKeywords(currentCell.getStringCellValue());
                    break;
                case 13:
                    product.setMetaDescription(currentCell.getStringCellValue());
                    break;
                case 14:
                    product.setDescription(currentCell.getStringCellValue());
                    break;
                case 15:
                    product.setCanonicalLink(currentCell.getStringCellValue());
                    break;
                case 16:
                    product.setNoIndexFlag(currentCell.getBooleanCellValue());
                    break;
                case 17:
                    product.setIsbn(getCellValue(currentCell).toString());
                    break;

                default:
                    break;
            }
            cellIdx++;
        }
        return product;
    }

    public static boolean hasExcelFormat(MultipartFile file) {
        return Constants.TYPE.equals(file.getContentType());
    }

    private static Object getCellValue(Cell cell) {
        CellType cellType = cell.getCellType();

        switch (cellType) {
            case NUMERIC:
                return new BigDecimal(String.valueOf(cell.getNumericCellValue())).toPlainString();
            case STRING:
                return cell.getStringCellValue();
            case ERROR:
                return String.valueOf(cell.getErrorCellValue());
            case BLANK:
                return "";
            case FORMULA:
                return cell.getCellFormula();
            case BOOLEAN:
                return cell.getBooleanCellValue();
        }
        return "error decoding string value of the cell";
    }
}
