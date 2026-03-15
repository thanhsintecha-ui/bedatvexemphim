package com.example.ticketbooking.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class RevenueReportDTO {
    private Long id;
    private LocalDateTime reportDate;
    private BigDecimal totalRevenue;
    private Long totalTicketsSold;
    private BigDecimal averageTicketPrice;
    private LocalDateTime reportPeriodStart;
    private LocalDateTime reportPeriodEnd;
    private String sourceSystem;
    private String status;

    // Constructors
    public RevenueReportDTO() {}

    public RevenueReportDTO(Long id, LocalDateTime reportDate, BigDecimal totalRevenue, Long totalTicketsSold,
                           BigDecimal averageTicketPrice, LocalDateTime reportPeriodStart, LocalDateTime reportPeriodEnd,
                           String sourceSystem, String status) {
        this.id = id;
        this.reportDate = reportDate;
        this.totalRevenue = totalRevenue;
        this.totalTicketsSold = totalTicketsSold;
        this.averageTicketPrice = averageTicketPrice;
        this.reportPeriodStart = reportPeriodStart;
        this.reportPeriodEnd = reportPeriodEnd;
        this.sourceSystem = sourceSystem;
        this.status = status;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public LocalDateTime getReportDate() { return reportDate; }
    public void setReportDate(LocalDateTime reportDate) { this.reportDate = reportDate; }

    public BigDecimal getTotalRevenue() { return totalRevenue; }
    public void setTotalRevenue(BigDecimal totalRevenue) { this.totalRevenue = totalRevenue; }

    public Long getTotalTicketsSold() { return totalTicketsSold; }
    public void setTotalTicketsSold(Long totalTicketsSold) { this.totalTicketsSold = totalTicketsSold; }

    public BigDecimal getAverageTicketPrice() { return averageTicketPrice; }
    public void setAverageTicketPrice(BigDecimal averageTicketPrice) { this.averageTicketPrice = averageTicketPrice; }

    public LocalDateTime getReportPeriodStart() { return reportPeriodStart; }
    public void setReportPeriodStart(LocalDateTime reportPeriodStart) { this.reportPeriodStart = reportPeriodStart; }

    public LocalDateTime getReportPeriodEnd() { return reportPeriodEnd; }
    public void setReportPeriodEnd(LocalDateTime reportPeriodEnd) { this.reportPeriodEnd = reportPeriodEnd; }

    public String getSourceSystem() { return sourceSystem; }
    public void setSourceSystem(String sourceSystem) { this.sourceSystem = sourceSystem; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
