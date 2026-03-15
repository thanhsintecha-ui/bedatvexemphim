package com.example.ticketbooking.repository;

import com.example.ticketbooking.entity.RevenueReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface RevenueReportRepository extends JpaRepository<RevenueReport, Long> {

    // Find reports by date range
    List<RevenueReport> findByReportDateBetween(LocalDateTime startDate, LocalDateTime endDate);

    // Find reports by status
    List<RevenueReport> findByStatus(String status);

    // Find reports by period range
    List<RevenueReport> findByReportPeriodStartBetween(LocalDateTime periodStart, LocalDateTime periodEnd);
}