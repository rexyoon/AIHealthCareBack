package com.aihealthcare.aihealthcare.repository;
import com.aihealthcare.aihealthcare.domain.BaseTimeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HealthRepository extends JpaRepository<BaseTimeEntity, Long> {

}