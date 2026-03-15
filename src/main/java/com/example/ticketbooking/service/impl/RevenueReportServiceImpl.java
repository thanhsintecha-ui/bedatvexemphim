package com.example.ticketbooking.service.impl;

import com.example.ticketbooking.dto.RevenueReportDTO;
import com.example.ticketbooking.entity.RevenueReport;
import com.example.ticketbooking.exception.ResourceNotFoundException;
import com.example.ticketbooking.repository.RevenueReportRepository;
import com.example.ticketbooking.service.RevenueReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class RevenueReportServiceImpl implements RevenueReportService {

    private final RevenueReportRepository revenueReportRepository;

    public RevenueReportServiceImpl(RevenueReportRepository revenueReportRepository) {
        this.revenueReportRepository = revenueReportRepository;
    }

    @Override
    @Transactional
    public RevenueReportDTO createRevenueReport(RevenueReportDTO revenueReportDTO) {
        RevenueReport revenueReport = new RevenueReport();
        revenueReport.setReportPeriodStart(revenueReportDTO.getReportPeriodStart());
        revenueReport.setReportPeriodEnd(revenueReportDTO.getReportPeriodEnd());
        revenueReport.setTotalRevenue(revenueReportDTO.getTotalRevenue());
        revenueReport.setTotalTicketsSold(revenueReportDTO.getTotalTicketsSold());
        revenueReport.setAverageTicketPrice(revenueReportDTO.getAverageTicketPrice());
        revenueReport.setSourceSystem(revenueReportDTO.getSourceSystem());
        revenueReport.setStatus(revenueReportDTO.getStatus());
        revenueReport.setReportDate(LocalDateTime.now());

        RevenueReport savedRevenueReport = revenueReportRepository.save(revenueReport);
        return mapToDTO(savedRevenueReport);
    }

    @Override
    public List<RevenueReportDTO> getAllRevenueReports() {
        return revenueReportRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public RevenueReportDTO getRevenueReportById(Long id) {
        RevenueReport revenueReport = revenueReportRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Revenue report not found with id: " + id));
        return mapToDTO(revenueReport);
    }

    @Override
    @Transactional
    public RevenueReportDTO updateRevenueReport(Long id, RevenueReportDTO revenueReportDTO) {
        RevenueReport existingRevenueReport = revenueReportRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Revenue report not found with id: " + id));

        existingRevenueReport.setReportPeriodStart(revenueReportDTO.getReportPeriodStart());
        existingRevenueReport.setReportPeriodEnd(revenueReportDTO.getReportPeriodEnd());
        existingRevenueReport.setTotalRevenue(revenueReportDTO.getTotalRevenue());
        existingRevenueReport.setTotalTicketsSold(revenueReportDTO.getTotalTicketsSold());
        existingRevenueReport.setAverageTicketPrice(revenueReportDTO.getAverageTicketPrice());
        existingRevenueReport.setSourceSystem(revenueReportDTO.getSourceSystem());
        existingRevenueReport.setStatus(revenueReportDTO.getStatus());

        RevenueReport updatedRevenueReport = revenueReportRepository.save(existingRevenueReport);
        return mapToDTO(updatedRevenueReport);
    }

    @Override
    @Transactional
    public void deleteRevenueReport(Long id) {
        RevenueReport revenueReport = revenueReportRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Revenue report not found with id: " + id));
        revenueReportRepository.delete(revenueReport);
    }

    @Override
    public List<RevenueReportDTO> getRevenueReportsByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return revenueReportRepository.findByReportDateBetween(startDate, endDate)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<RevenueReportDTO> getRevenueReportsByStatus(String status) {
        return revenueReportRepository.findByStatus(status)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<RevenueReportDTO> getRevenueReportsByPeriodRange(LocalDateTime periodStart, LocalDateTime periodEnd) {
        return revenueReportRepository.findByReportPeriodStartBetween(periodStart, periodEnd)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public RevenueReportDTO generateRevenueReport(LocalDateTime periodStart, LocalDateTime periodEnd) {
        // This is a simplified implementation
        // In a real application, you would calculate the actual revenue from bookings
        RevenueReport revenueReport = new RevenueReport();
        revenueReport.setReportPeriodStart(periodStart);
        revenueReport.setReportPeriodEnd(periodEnd);
        revenueReport.setTotalRevenue(java.math.BigDecimal.ZERO);
        revenueReport.setTotalTicketsSold(0L);
        revenueReport.setAverageTicketPrice(java.math.BigDecimal.ZERO);
        revenueReport.setStatus("GENERATED");
        revenueReport.setReportDate(LocalDateTime.now());

        RevenueReport savedRevenueReport = revenueReportRepository.save(revenueReport);
        return mapToDTO(savedRevenueReport);
    }

    private RevenueReportDTO mapToDTO(RevenueReport revenueReport) {
        RevenueReportDTO dto = new RevenueReportDTO();
        dto.setId(revenueReport.getId());
        dto.setReportDate(revenueReport.getReportDate());
        dto.setReportPeriodStart(revenueReport.getReportPeriodStart());
        dto.setReportPeriodEnd(revenueReport.getReportPeriodEnd());
        dto.setTotalRevenue(revenueReport.getTotalRevenue());
        dto.setTotalTicketsSold(revenueReport.getTotalTicketsSold());
        dto.setAverageTicketPrice(revenueReport.getAverageTicketPrice());
        dto.setSourceSystem(revenueReport.getSourceSystem());
        dto.setStatus(revenueReport.getStatus());
        return dto;
    }
}