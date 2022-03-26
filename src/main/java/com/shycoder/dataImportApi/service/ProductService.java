package com.shycoder.dataImportApi.service;

import com.shycoder.dataImportApi.entity.Job;
import com.shycoder.dataImportApi.entity.Product;
import com.shycoder.dataImportApi.entity.User;
import com.shycoder.dataImportApi.repository.JobRepository;
import com.shycoder.dataImportApi.repository.ProductRepository;
import com.shycoder.dataImportApi.repository.UserRepository;
import com.shycoder.dataImportApi.utils.Constants;
import com.shycoder.dataImportApi.utils.ExcelFileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.Date;
import java.util.List;

@Service
public class ProductService {
    private final Logger logger = LoggerFactory.getLogger(ProductService.class);

    private final ProductRepository productRepository;
    private final JobRepository jobRepository;
    private final UserRepository userRepository;
    private final ExcelFileUtil excelFileUtil;

    public ProductService(ProductRepository productRepository, JobRepository jobRepository, UserRepository userRepository, ExcelFileUtil excelFileUtil) {
        this.productRepository = productRepository;
        this.jobRepository = jobRepository;
        this.userRepository = userRepository;
        this.excelFileUtil = excelFileUtil;
    }

    @Async
    public void save(InputStream fileStream, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Couldn't find username:" + username));
        Job job = jobRepository.save(new Job(new Date(), Constants.INPROGRESS, user));
        try {
            List<Product> products = excelFileUtil.extractProducts(fileStream, job);
            productRepository.saveAll(products);

            jobRepository.save(job);
        } catch (Exception e) {
            logger.error("Error while extracting products. Error Message: ", e);
            job.setStatus(Constants.FAILED);
            jobRepository.save(job);
        }
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
}