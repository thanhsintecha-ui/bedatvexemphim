package com.example.ticketbooking.service;

import com.example.ticketbooking.dto.RevenueReportDTO;

import java.time.LocalDateTime;
import java.util.List;

public interface RevenueReportService {

    RevenueReportDTO createRevenueReport(RevenueReportDTO revenueReportDTO);

    List<RevenueReportDTO> getAllRevenueReports();

    RevenueReportDTO getRevenueReportById(Long id);

    RevenueReportDTO updateRevenueReport(Long id, RevenueReportDTO revenueReportDTO);

    void deleteRevenueReport(Long id);

    List<RevenueReportDTO> getRevenueReportsByDateRange(LocalDateTime startDate, LocalDateTime endDate);

    List<RevenueReportDTO> getRevenueReportsByStatus(String status);

    List<RevenueReportDTO> getRevenueReportsByPeriodRange(LocalDateTime periodStart, LocalDateTime periodEnd);

    RevenueReportDTO generateRevenueReport(LocalDateTime periodStart, LocalDateTime periodEnd);
}