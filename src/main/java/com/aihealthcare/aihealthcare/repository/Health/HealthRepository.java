package com.aihealthcare.aihealthcare.repository.Health;
import com.aihealthcare.aihealthcare.domain.health.HealthRecordEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HealthRepository extends JpaRepository<HealthRecordEntity, Long> {

}