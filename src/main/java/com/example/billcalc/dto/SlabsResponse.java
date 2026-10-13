package com.example.billcalc.dto;

import java.math.BigDecimal;
import java.util.List;

public record SlabsResponse(BigDecimal serviceCharge, List<SlabInfo> slabs) {
}
