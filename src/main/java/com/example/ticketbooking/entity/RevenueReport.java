package com.example.ticketbooking.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "revenue_reports")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RevenueReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "report_date", nullable = false)
    private LocalDateTime reportDate;

    @Column(name = "total_revenue", nullable = false, precision = 15, scale = 2)
    private BigDecimal totalRevenue;

    @Column(name = "total_tickets_sold", nullable = false)
    private Long totalTicketsSold;

    @Column(name = "average_ticket_price", precision = 15, scale = 2)
    private BigDecimal averageTicketPrice;

    @Column(name = "report_period_start", nullable = false)
    private LocalDateTime reportPeriodStart;

    @Column(name = "report_period_end", nullable = false)
    private LocalDateTime reportPeriodEnd;

    @Column(name = "source_system", length = 50)
    private String sourceSystem;

    @Column(name = "status", length = 20)
    private String status;
}
