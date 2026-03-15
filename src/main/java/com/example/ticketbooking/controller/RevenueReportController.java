package com.example.ticketbooking.controller;

import com.example.ticketbooking.dto.RevenueReportDTO;
import com.example.ticketbooking.service.RevenueReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/revenue-reports")
public class RevenueReportController {

    private final RevenueReportService revenueReportService;

    @Autowired
    public RevenueReportController(RevenueReportService revenueReportService) {
        this.revenueReportService = revenueReportService;
    }

    @PostMapping
    public ResponseEntity<RevenueReportDTO> createRevenueReport(@Valid @RequestBody RevenueReportDTO revenueReportDTO) {
        RevenueReportDTO createdReport = revenueReportService.createRevenueReport(revenueReportDTO);
        return ResponseEntity.ok(createdReport);
    }

    @GetMapping
    public ResponseEntity<List<RevenueReportDTO>> getAllRevenueReports() {
        List<RevenueReportDTO> reports = revenueReportService.getAllRevenueReports();
        return ResponseEntity.ok(reports);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RevenueReportDTO> getRevenueReportById(@PathVariable Long id) {
        RevenueReportDTO report = revenueReportService.getRevenueReportById(id);
        return ResponseEntity.ok(report);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RevenueReportDTO> updateRevenueReport(@PathVariable Long id,
            @Valid @RequestBody RevenueReportDTO revenueReportDTO) {
        RevenueReportDTO updatedReport = revenueReportService.updateRevenueReport(id, revenueReportDTO);
        return ResponseEntity.ok(updatedReport);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRevenueReport(@PathVariable Long id) {
        revenueReportService.deleteRevenueReport(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/date-range")
    public ResponseEntity<List<RevenueReportDTO>> getRevenueReportsByDateRange(
            @RequestParam LocalDateTime startDate,
            @RequestParam LocalDateTime endDate) {
        List<RevenueReportDTO> reports = revenueReportService.getRevenueReportsByDateRange(startDate, endDate);
        return ResponseEntity.ok(reports);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<RevenueReportDTO>> getRevenueReportsByStatus(@PathVariable String status) {
        List<RevenueReportDTO> reports = revenueReportService.getRevenueReportsByStatus(status);
        return ResponseEntity.ok(reports);
    }

    @GetMapping("/period-range")
    public ResponseEntity<List<RevenueReportDTO>> getRevenueReportsByPeriodRange(
            @RequestParam LocalDateTime periodStart,
            @RequestParam LocalDateTime periodEnd) {
        List<RevenueReportDTO> reports = revenueReportService.getRevenueReportsByPeriodRange(periodStart, periodEnd);
        return ResponseEntity.ok(reports);
    }

    @PostMapping("/generate")
    public ResponseEntity<RevenueReportDTO> generateRevenueReport(
            @RequestParam LocalDateTime periodStart,
            @RequestParam LocalDateTime periodEnd) {
        RevenueReportDTO report = revenueReportService.generateRevenueReport(periodStart, periodEnd);
        return ResponseEntity.ok(report);
    }
}