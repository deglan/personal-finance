package com.example.finance.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "REPORT")
@Data
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
public class ReportEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "REPORT_ID")
    private UUID reportId;
    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false)
    private UserAccountEntity user;
    @Column(name = "REPORT_TYPE", nullable = false)
    private String reportType;
    @Column(name = "START_DATE", nullable = false)
    private LocalDate startDate;
    @Column(name = "END_DATE", nullable = false)
    private LocalDate endDate;
    @Column(name = "GENERATED_DATE", nullable = false)
    private LocalDate generatedDate;
}
