package com.shycoder.dataImportApi.repository;

import com.shycoder.dataImportApi.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobRepository extends JpaRepository<Job, Long> {
}
