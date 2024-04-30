package com.example.finance.model.dto;

import java.time.LocalDate;
import java.util.UUID;

public record ReportDto(UUID reportId, UserAccountDto user, String reportType, LocalDate startDate, LocalDate endDate,
                        LocalDate generatedDate) {
}
