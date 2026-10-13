package com.example.billcalc.dto;

import java.math.BigDecimal;
import java.util.List;

public record BillResponse(BigDecimal units, List<BillLine> lines, BigDecimal energyCharge,
                           BigDecimal serviceCharge, BigDecimal total) {
}
