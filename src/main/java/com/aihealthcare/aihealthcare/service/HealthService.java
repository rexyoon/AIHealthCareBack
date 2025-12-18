package com.aihealthcare.aihealthcare.service;

import com.aihealthcare.aihealthcare.domain.HealthRecordEntity;
import com.aihealthcare.aihealthcare.dto.request.HealthRecordCreateRequest;
import com.aihealthcare.aihealthcare.dto.response.HealthRecordResponse;
import com.aihealthcare.aihealthcare.exception.CustomException;
import com.aihealthcare.aihealthcare.exception.ErrorCode;
import com.aihealthcare.aihealthcare.repository.HealthRecordRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class HealthService {

    private final HealthRecordRepository healthRecordRepository;

    public HealthService(HealthRecordRepository healthRecordRepository) {
        this.healthRecordRepository = healthRecordRepository;
    }

    @Transactional
    public HealthRecordResponse createRecord(HealthRecordCreateRequest request) {
        HealthRecordEntity entity = HealthRecordEntity.create(
                request.getWeightKg(),
                request.getMemo()
        );

        HealthRecordEntity saved = healthRecordRepository.save(entity);
        return HealthRecordResponse.from(saved);
    }

    @Transactional(readOnly = true)
    public List<HealthRecordResponse> findAll() {
        return healthRecordRepository.findAll()
                .stream()
                .map(HealthRecordResponse::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public HealthRecordResponse findOne(Long id) {
        HealthRecordEntity entity = healthRecordRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.HEALTH_RECORD_NOT_FOUND));
        return HealthRecordResponse.from(entity);
    }

    @Transactional
    public void delete(Long id) {
        if (!healthRecordRepository.existsById(id)) {
            throw new CustomException(ErrorCode.HEALTH_RECORD_NOT_FOUND);
        }
        healthRecordRepository.deleteById(id);
    }
}
