package com.shycoder.dataImportApi.utils;

import com.shycoder.dataImportApi.entity.Job;
import com.shycoder.dataImportApi.entity.Product;
import com.shycoder.dataImportApi.entity.User;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class ExcelFileUtilTest {

    private ExcelFileUtil excelFileUtil;

    @BeforeEach
    public void setUp() {
        excelFileUtil = new ExcelFileUtil();
    }

    @Test
    void should_return_extracted_product_from_row() {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet spreadsheet = workbook.createSheet("cell types");
        Row row = generateValidRow(spreadsheet, 1);

        Product expectedProduct = generateValidProduct();
        Product actualProduct = excelFileUtil.extractProduct(row);

        assertEquals(expectedProduct, actualProduct);
    }

    //only validation we are expecting is no index flag should be boolean
    @Test
    void should_throw_error_if_row_contains_invalid_data() {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet spreadsheet = workbook.createSheet("cell types");
        Row row = generateValidRow(spreadsheet, 1);
        row.getCell(16).setCellValue("invalid");
        assertThrows(IllegalStateException.class, () -> excelFileUtil.extractProduct(row));
    }

    @Test
    void extractProducts_should_ignore_invalid_rows() throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet spreadsheet = workbook.createSheet(Constants.SHEET);
        spreadsheet.createRow((short) 1);
        Row row1 = generateValidRow(spreadsheet, 2);
        row1.getCell(16).setCellValue("invalid");
        generateValidRow(spreadsheet, 3);

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        workbook.write(bos);
        byte[] barray = bos.toByteArray();
        InputStream is = new ByteArrayInputStream(barray);

        Job job = new Job(new Date(), Constants.INPROGRESS, new User());

        List<Product> actualProducts = excelFileUtil.extractProducts(is, job);
        assertEquals(1, actualProducts.size());
    }

    @Test
    void extractProducts_should_update_job_failed_and_message_atleast_one_invalid_row() throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet spreadsheet = workbook.createSheet(Constants.SHEET);
        spreadsheet.createRow((short) 1);
        Row row1 = generateValidRow(spreadsheet, 2);
        row1.getCell(16).setCellValue("invalid");
        generateValidRow(spreadsheet, 3);

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        workbook.write(bos);
        byte[] barray = bos.toByteArray();
        InputStream is = new ByteArrayInputStream(barray);

        Job job = new Job(new Date(), Constants.INPROGRESS, new User());

        excelFileUtil.extractProducts(is, job);
        assertEquals(Constants.FAILED, job.getStatus());
        assertEquals("Error while processing row number-1;", job.getMessage());
    }


    Row generateValidRow(XSSFSheet spreadsheet, int rowNum) {
        Row row = spreadsheet.createRow((short) rowNum);
        row.createCell(0).setCellValue(9783865994585L);
        row.createCell(1).setCellValue("Name");
        row.createCell(2).setCellValue("h1");
        row.createCell(3).setCellValue("Second Name");
        row.createCell(4).setCellValue("Brand");
        row.createCell(5).setCellValue("Author");
        row.createCell(6).setCellValue("other category");
        row.createCell(7).setCellValue("main category");
        row.createCell(8).setCellValue("attribute categories");
        row.createCell(9).setCellValue("image url");
        row.createCell(10).setCellValue("page link");
        row.createCell(11).setCellValue("meta title");
        row.createCell(12).setCellValue("meta keywords");
        row.createCell(13).setCellValue("meta description");
        row.createCell(14).setCellValue("description");
        row.createCell(15).setCellValue("canonical link");
        row.createCell(16).setCellValue(false);
        row.createCell(17).setCellValue("isbn");

        return row;
    }

    Product generateValidProduct() {
        Product product = new Product();
        product.setEan(9783865994585L);
        product.setName("Name");
        product.setH1("h1");
        product.setSecondName("Second Name");
        product.setBrand("Brand");
        product.setAuthor("Author");
        product.setOtherCategory("other category");
        product.setMainCategory("main category");
        product.setAttributeCategories("attribute categories");
        product.setImageUrl("image url");
        product.setPageLink("page link");
        product.setMetaTitle("meta title");
        product.setMetaKeywords("meta keywords");
        product.setMetaDescription("meta description");
        product.setDescription("description");
        product.setCanonicalLink("canonical link");
        product.setNoIndexFlag(false);
        product.setIsbn("isbn");
        return product;
    }
}