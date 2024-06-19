package com.example.finance.factory;

import com.example.finance.model.dto.ReportDto;
import com.example.finance.model.entity.ReportEntity;
import com.example.finance.model.entity.UserAccountEntity;
import com.example.finance.utils.TestConstants;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ReportMockFactory {

    public ReportEntity createReportEntityWithUser(UserAccountEntity user) {
        return ReportEntity.builder()
                .reportId(TestConstants.REPORT_UUID)
                .userAccountEntity(user)
                .reportType(TestConstants.REPORT_TYPE)
                .startDate(TestConstants.REPORT_START_DATE)
                .endDate(TestConstants.REPORT_END_DATE)
                .generatedDate(TestConstants.REPORT_GENERATED_DATE)
                .build();
    }

    public ReportEntity createReportEntity() {
        return ReportEntity.builder()
                .reportId(TestConstants.REPORT_UUID)
                .userAccountEntity(UserMockFactory.createUserEntity())
                .reportType(TestConstants.REPORT_TYPE)
                .startDate(TestConstants.REPORT_START_DATE)
                .endDate(TestConstants.REPORT_END_DATE)
                .generatedDate(TestConstants.REPORT_GENERATED_DATE)
                .build();
    }

    public ReportDto createReportDto() {
        return new ReportDto(
                TestConstants.REPORT_UUID,
                TestConstants.USER_UUID,
                TestConstants.REPORT_TYPE,
                TestConstants.REPORT_START_DATE,
                TestConstants.REPORT_END_DATE,
                TestConstants.REPORT_GENERATED_DATE
        );
    }
}
