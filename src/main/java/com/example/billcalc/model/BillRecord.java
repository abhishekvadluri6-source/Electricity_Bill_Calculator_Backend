package com.example.billcalc.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/** A bill that was calculated and saved to MySQL. */
@Entity
@Table(name = "bill_history")
public class BillRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal units;

    @Column(name = "energy_charge", nullable = false, precision = 12, scale = 2)
    private BigDecimal energyCharge;

    @Column(name = "service_charge", nullable = false, precision = 12, scale = 2)
    private BigDecimal serviceCharge;

    @Column(name = "total_amount", nullable = false, precision = 12, scale = 2)
    private BigDecimal totalAmount;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    protected BillRecord() { }

    public BillRecord(BigDecimal units, BigDecimal energyCharge, BigDecimal serviceCharge, BigDecimal totalAmount) {
        this.units = units;
        this.energyCharge = energyCharge;
        this.serviceCharge = serviceCharge;
        this.totalAmount = totalAmount;
    }

    @PrePersist
    void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public BigDecimal getUnits() { return units; }
    public BigDecimal getEnergyCharge() { return energyCharge; }
    public BigDecimal getServiceCharge() { return serviceCharge; }
    public BigDecimal getTotalAmount() { return totalAmount; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}
