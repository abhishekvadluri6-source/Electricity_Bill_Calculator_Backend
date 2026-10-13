package com.example.billcalc.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record BillRequest(
        @NotNull(message = "Enter the number of units consumed.")
        @DecimalMin(value = "0", message = "Units cannot be negative.")
        @DecimalMax(value = "100000", message = "Units must be 100000 or less.")
        BigDecimal units) {
}
