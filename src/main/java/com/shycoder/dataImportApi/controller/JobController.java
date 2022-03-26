package com.shycoder.dataImportApi.controller;

import com.shycoder.dataImportApi.entity.Job;
import com.shycoder.dataImportApi.service.JobService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/jobs")
public class JobController {

    private final JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @GetMapping()
    public List<Job> getJobs() {
        return jobService.getAllJobs();
    }
}
