package com.example.finance.controller;

import com.example.finance.business.ApiEndpoints;
import com.example.finance.model.dto.ReportDto;
import com.example.finance.service.ReportService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(ApiEndpoints.Endpoints.REPORT)
@AllArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @GetMapping(value = ApiEndpoints.Endpoints.ID)
    public ResponseEntity<ReportDto> getById(@PathVariable UUID id) {
        ReportDto reportById = reportService.getById(id);
        return ResponseEntity.ok(reportById);
    }

    @GetMapping(value = ApiEndpoints.Endpoints.GET_REPORTS_BY_USER_ID)
    public ResponseEntity<List<ReportDto>> getReportsByUserId(@PathVariable UUID id) {
        List<ReportDto> reportsByUserId = reportService.getByUserId(id);
        return ResponseEntity.ok(reportsByUserId);
    }

    @PostMapping(value = ApiEndpoints.Endpoints.CREATE)
    public ResponseEntity<ReportDto> create(@RequestBody ReportDto report) {
        ReportDto reportDto = reportService.create(report);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(reportDto);
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
