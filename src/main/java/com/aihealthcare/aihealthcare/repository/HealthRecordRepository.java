package com.aihealthcare.aihealthcare.repository;

import com.aihealthcare.aihealthcare.domain.HealthRecordEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HealthRecordRepository extends JpaRepository<HealthRecordEntity, Long> {
}
