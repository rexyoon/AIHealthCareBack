package com.aihealthcare.aihealthcare.repository;

import com.aihealthcare.aihealthcare.domain.AI.AiAnalysisLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AiAnalysisLogResporitory extends JpaRepository<AiAnalysisLog, Long> {
    List<AiAnalysisLog> findAllByUserIdOrderByCreatedAtDesc(Long userId);
}