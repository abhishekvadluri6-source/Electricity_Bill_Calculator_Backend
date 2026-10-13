package com.example.billcalc.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record HistoryItem(Long id, BigDecimal units, BigDecimal total, LocalDateTime createdAt) {
}
