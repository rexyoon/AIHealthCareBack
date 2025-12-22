package com.aihealthcare.aihealthcare.repository;

import com.aihealthcare.aihealthcare.domain.HealthRecordEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface HealthRecordRepository extends JpaRepository<HealthRecordEntity, Long> {

    Optional<HealthRecordEntity> findByRecordDate(LocalDate recordDate);

    boolean existsByRecordDate(LocalDate recordDate);

    List<HealthRecordEntity> findAllByRecordDateBetweenOrderByRecordDateAsc(LocalDate from, LocalDate to);

    List<HealthRecordEntity> findAllByOrderByRecordDateAsc();
}
