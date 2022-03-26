package com.shycoder.dataImportApi.controller;

import com.shycoder.dataImportApi.entity.Product;
import com.shycoder.dataImportApi.service.ProductService;
import com.shycoder.dataImportApi.utils.ExcelFileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
public class ProductsController {
    private final Logger logger = LoggerFactory.getLogger(ProductsController.class);
    private final ProductService productService;

    public ProductsController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping()
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }


    @RequestMapping(value = "/upload", method = RequestMethod.POST, consumes = "multipart/form-data")
    public ResponseEntity<?> uploadFile(@RequestBody MultipartFile file, Principal principal) throws IOException {
        String message = "";

        if (ExcelFileUtil.hasExcelFormat(file)) {
            message = "Uploading the file: " + file.getOriginalFilename();
            productService.save(file.getInputStream(), principal.getName());
            logger.info(message);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(message);
        }

        message = "Please upload an excel file!";
        logger.debug("{} not having a valid excel format", file.getOriginalFilename());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
    }
}
