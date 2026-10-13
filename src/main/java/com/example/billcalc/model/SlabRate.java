package com.example.billcalc.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

/** One billing slab. upToUnits == null means "no upper limit". */
@Entity
@Table(name = "slab_rates")
public class SlabRate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "slab_order", nullable = false, unique = true)
    private int slabOrder;

    @Column(name = "up_to_units")
    private Integer upToUnits;

    @Column(name = "rate_per_unit", nullable = false, precision = 8, scale = 2)
    private BigDecimal ratePerUnit;

    protected SlabRate() { }

    public SlabRate(int slabOrder, Integer upToUnits, BigDecimal ratePerUnit) {
        this.slabOrder = slabOrder;
        this.upToUnits = upToUnits;
        this.ratePerUnit = ratePerUnit;
    }

    public Long getId() { return id; }
    public int getSlabOrder() { return slabOrder; }
    public Integer getUpToUnits() { return upToUnits; }
    public BigDecimal getRatePerUnit() { return ratePerUnit; }
}
