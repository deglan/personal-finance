package com.example.finance.service;

import com.example.finance.exception.model.BackendException;
import com.example.finance.factory.ReportMockFactory;
import com.example.finance.factory.UserMockFactory;
import com.example.finance.mapper.ReportMapper;
import com.example.finance.mapper.UserAccountMapper;
import com.example.finance.model.dto.ReportDto;
import com.example.finance.model.dto.UserAccountDto;
import com.example.finance.model.entity.ReportEntity;
import com.example.finance.model.entity.UserAccountEntity;
import com.example.finance.repository.ReportRepository;
import com.example.finance.utils.TestConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReportServiceTest {

    @Mock
    private ReportRepository reportRepository;

    @Mock
    private UserAccountService userAccountService;
    @Mock
    private UserAccountMapper userAccountMapper;

    @Mock
    private ReportMapper reportMapper;

    @InjectMocks
    private ReportService reportService;

    private ReportDto reportDto;
    private ReportEntity reportEntity;
    private UserAccountEntity userAccountEntity;
    private UserAccountDto userDto;

    @BeforeEach
    void setUp() {
        reportDto = ReportMockFactory.createReportDto();
        reportEntity = ReportMockFactory.createReportEntity();
        userAccountEntity = UserMockFactory.createUserEntity();
        userDto = UserMockFactory.createUserDto();
    }

    @Test
    void shouldReturnReportsByUserId() {
        // Given
        List<ReportEntity> reportEntities = List.of(reportEntity);
        List<ReportDto> reportDtos = List.of(reportDto);

        when(reportRepository.findByUserAccountEntityUserId(TestConstants.USER_UUID)).thenReturn(reportEntities);
        when(reportMapper.toDtoList(reportEntities)).thenReturn(reportDtos);

        // When
        List<ReportDto> result = reportService.getByUserId(TestConstants.USER_UUID);

        // Then
        assertThat(result).isEqualTo(reportDtos);
    }

    @Test
    void shouldReturnReportById() {
        // Given
        when(reportRepository.findById(TestConstants.REPORT_UUID)).thenReturn(Optional.of(reportEntity));
        when(reportMapper.toDto(reportEntity)).thenReturn(reportDto);

        // When
        ReportDto result = reportService.getById(TestConstants.REPORT_UUID);

        // Then
        assertThat(result).isEqualTo(reportDto);
    }

    @Test
    void shouldThrowBackendExceptionWhenReportNotFoundById() {
        // Given
        when(reportRepository.findById(TestConstants.REPORT_UUID)).thenReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> reportService.getById(TestConstants.REPORT_UUID))
                .isInstanceOf(BackendException.class)
                .hasMessageContaining("There is no such report");
    }

    @Test
    void shouldCreateReport() {
        // Given
        when(userAccountService.getById(reportDto.userId())).thenReturn(userDto);
        when(userAccountMapper.toEntity(userDto)).thenReturn(userAccountEntity);
        when(reportMapper.toEntity(reportDto)).thenReturn(reportEntity);
        when(reportRepository.save(reportEntity)).thenReturn(reportEntity);
        when(reportMapper.toDto(reportEntity)).thenReturn(reportDto);

        // When
        ReportDto result = reportService.create(reportDto);

        // Then
        assertThat(result).isEqualTo(reportDto);
        verify(reportRepository).save(reportEntity);
    }

    @Test
    void shouldUpdateReport() {
        // Given
        when(reportMapper.toEntity(reportDto)).thenReturn(reportEntity);
        when(reportRepository.save(reportEntity)).thenReturn(reportEntity);
        when(reportMapper.toDto(reportEntity)).thenReturn(reportDto);

        // When
        ReportDto result = reportService.updateReport(reportDto);

        // Then
        assertThat(result).isEqualTo(reportDto);
        verify(reportRepository).save(reportEntity);
    }

    @Test
    void shouldDeleteReport() {
        // Given
        when(reportRepository.findById(TestConstants.REPORT_UUID)).thenReturn(Optional.of(reportEntity));

        // When
        reportService.deleteReport(TestConstants.REPORT_UUID);

        // Then
        verify(reportRepository).delete(reportEntity);
    }

    @Test
    void shouldThrowBackendExceptionWhenReportNotFoundForDelete() {
        // Given
        when(reportRepository.findById(TestConstants.REPORT_UUID)).thenReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> reportService.deleteReport(TestConstants.REPORT_UUID))
                .isInstanceOf(BackendException.class)
                .hasMessageContaining("There is no such report");
    }
}