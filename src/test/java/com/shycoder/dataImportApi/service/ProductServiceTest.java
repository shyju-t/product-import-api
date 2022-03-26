package com.shycoder.dataImportApi.service;

import com.shycoder.dataImportApi.entity.Job;
import com.shycoder.dataImportApi.entity.User;
import com.shycoder.dataImportApi.repository.JobRepository;
import com.shycoder.dataImportApi.repository.ProductRepository;
import com.shycoder.dataImportApi.repository.UserRepository;
import com.shycoder.dataImportApi.utils.Constants;
import com.shycoder.dataImportApi.utils.ExcelFileUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Collections;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    private static final String USERNAME = "test";

    @Mock
    private ProductRepository productRepository;
    @Mock
    private JobRepository jobRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private ExcelFileUtil excelFileUtil;

    private ProductService service;

    private Job job;

    @BeforeEach
    public void setUp() {
        User testUser = new User();
        testUser.setName(USERNAME);
        testUser.setUserId(1L);

        job = new Job(new Date(), Constants.INPROGRESS, testUser);
        when(userRepository.findByUsername(USERNAME)).thenReturn(Optional.of(testUser));
        when(jobRepository.save(any())).thenReturn(job);
        service = new ProductService(productRepository, jobRepository, userRepository, excelFileUtil);
    }

    @Test
    void should_save_job_status_as_failed_when_extract_product_throws_exception() {
        InputStream dummyInputStream = new ByteArrayInputStream( "test".getBytes() );
        when(excelFileUtil.extractProducts(any(), any())).thenThrow(new RuntimeException());
        service.save(dummyInputStream, USERNAME);
        assertEquals(job.getStatus(), Constants.FAILED);
    }

    @Test
    void should_save_job_status_as_completed_when_extract_product_successfully() {
        InputStream dummyInputStream = new ByteArrayInputStream( "test".getBytes() );
        job.setStatus(Constants.COMPLETED);
        when(excelFileUtil.extractProducts(any(), any())).thenReturn(Collections.emptyList());
        service.save(dummyInputStream, USERNAME);
        assertEquals(job.getStatus(), Constants.COMPLETED);
    }

    /*@Test
    void test_logging() {
        logger.error("{} is a value", 1);
    }*/
}