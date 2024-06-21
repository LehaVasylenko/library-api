package com.example.demo.repository;

import com.example.demo.model.BugReport;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * demo
 * Author: Vasylenko Oleksii
 * Date: 31.05.2024
 */
@Repository
public interface BugReportRepository extends CrudRepository<BugReport, Integer> {

}
