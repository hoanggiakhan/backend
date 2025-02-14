package com.example.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.dao.ReportDAO;
import com.example.backend.service.report.ReportService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE , makeFinal = true)
public class ReportController {
    ReportService reportService;

    @GetMapping("/{userId}")
    public ResponseEntity<?> getAllReportByUser(@PathVariable String userId){
        return ResponseEntity.ok(reportService.generateReportByMonth(userId));
    }

    @PostMapping("/{userId}")
    public ResponseEntity<?> saveReport(@PathVariable String userId , @RequestBody ReportDAO reportDAO){
        reportService.saveReports(reportDAO, userId);
        return ResponseEntity.ok("Lưu thành công");
    }
}
