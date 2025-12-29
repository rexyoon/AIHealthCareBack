package com.aihealthcare.aihealthcare.repository;
import com.aihealthcare.aihealthcare.domain.HealthRecordEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HealthRepository extends JpaRepository<HealthRecordEntity, Long> {

}