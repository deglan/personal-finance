package com.example.finance.controller;

import com.example.finance.business.ApiEndpoints;
import com.example.finance.model.dto.ReportDto;
import com.example.finance.service.ReportService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(ApiEndpoints.Endpoints.REPORT)
@AllArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @GetMapping(value = ApiEndpoints.Endpoints.ID)
    public ResponseEntity<ReportDto> getById(@PathVariable UUID id) {
        ReportDto reportServiceById = reportService.getById(id);
        return ResponseEntity.ok(reportServiceById);
    }

    @PostMapping(value = ApiEndpoints.Endpoints.CREATE)
    public ResponseEntity<ReportDto> create(@RequestBody ReportDto report) {
        ReportDto reportDto = reportService.create(report);
        return ResponseEntity.ok(reportDto);
    }

    @PutMapping(value = ApiEndpoints.Endpoints.ID)
    public ResponseEntity<ReportDto> update(@PathVariable UUID id, @RequestBody ReportDto report) {
        ReportDto reportDto = reportService.updateReport(id, report);
        return ResponseEntity.ok(reportDto);
    }

    @DeleteMapping(value = ApiEndpoints.Endpoints.ID)
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        reportService.deleteReport(id);
        return ResponseEntity.noContent().build();
    }
}
