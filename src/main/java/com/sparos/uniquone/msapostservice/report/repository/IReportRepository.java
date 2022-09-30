package com.sparos.uniquone.msapostservice.report.repository;

import com.sparos.uniquone.msapostservice.report.domain.Report;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IReportRepository extends JpaRepository<Report, Long> {
}
