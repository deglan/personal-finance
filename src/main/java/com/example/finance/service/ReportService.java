package com.example.finance.service;

import com.example.finance.exception.model.BackendException;
import com.example.finance.mapper.ReportMapper;
import com.example.finance.model.dto.ReportDto;
import com.example.finance.model.entity.ReportEntity;
import com.example.finance.model.entity.UserAccountEntity;
import com.example.finance.repository.ReportRepository;
import com.example.finance.repository.UserAccountRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Service
@Slf4j
public class ReportService {


    private static final String MESSAGE = "There is no such report";

    private final ReportRepository reportRepository;
    private final UserAccountRepository userAccountRepository;
    private final ReportMapper reportMapper;

    public List<ReportDto> getByUserId(UUID userId) {
        return reportMapper.toDtoList(reportRepository.findByUserAccountEntityUserId(userId));
    }

    public ReportDto getById(UUID id) {
        return reportRepository.findById(id)
                .map(reportMapper::toDto)
                .orElseThrow(() -> new BackendException(MESSAGE));
    }

    @Transactional
    public ReportDto create(ReportDto report) {
        UserAccountEntity userAccountEntity = userAccountRepository.findById(report.userId())
                .orElseThrow(() -> new BackendException("User not found"));
        ReportEntity reportEntity = reportMapper.toEntity(report);
        reportEntity.setUserAccountEntity(userAccountEntity);
        reportEntity.setGeneratedDate(LocalDate.now());
        ReportEntity savedReport = reportRepository.save(reportEntity);
        log.info("Saved report with ID {} and type {} for user with ID {}",
                reportEntity.getReportId(),
                reportEntity.getReportType(),
                reportEntity.getUserAccountEntity().getUserId());
        return reportMapper.toDto(savedReport);
    }

    @Transactional
    public ReportDto updateReport(UUID id, ReportDto reportDto) {
        ReportEntity reportDb = reportRepository.findById(id)
                .orElseThrow(() -> new BackendException(MESSAGE));
        reportDb.setReportType(reportDto.reportType());
        ReportEntity savedReport = reportRepository.save(reportDb);
        return reportMapper.toDto(savedReport);
    }

    @Transactional
    public void deleteReport(UUID id) {
        ReportEntity reportEntity = reportRepository.findById(id)
                .orElseThrow(() -> new BackendException(MESSAGE));
        reportRepository.delete(reportEntity);
    }
}
