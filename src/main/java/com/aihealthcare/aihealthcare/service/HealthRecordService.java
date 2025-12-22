package com.aihealthcare.aihealthcare.service;

import com.aihealthcare.aihealthcare.domain.HealthRecordEntity;
import com.aihealthcare.aihealthcare.dto.request.HealthRecordCreateRequest;
import com.aihealthcare.aihealthcare.dto.request.HealthRecordUpdateRequest;
import com.aihealthcare.aihealthcare.dto.response.HealthRecordResponse;
import com.aihealthcare.aihealthcare.repository.HealthRecordRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.common.errors.ApiException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
@Service
@RequiredArgsConstructor
public class HealthRecordService {

    private final HealthRecordRepository healthRecordRepository;

    @Transactional
    public HealthRecordResponse create(HealthRecordCreateRequest request) {
        validateCreateRequest(request);

        if (healthRecordRepository.existsByRecordDate(request.getRecordDate())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "이미 해당 날짜의 기록이 존재합니다.");
        }

        HealthRecordEntity saved = healthRecordRepository.save(
                HealthRecordEntity.builder()
                        .recordDate(request.getRecordDate())
                        .weightKg(request.getWeightKg())
                        .note(request.getNote())
                        .build()
        );

        return toResponse(saved);
    }

    @Transactional(readOnly = true)
    public HealthRecordResponse getById(Long id) {
        HealthRecordEntity entity = healthRecordRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 id의 기록이 없습니다. id=" + id));
        return toResponse(entity);
    }

    @Transactional(readOnly = true)
    public List<HealthRecordResponse> getAll() {
        return healthRecordRepository.findAllByOrderByRecordDateAsc()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<HealthRecordResponse> getByDate(LocalDate from, LocalDate to) {
        if (from == null || to == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "from/to 날짜는 필수입니다.");
        }
        if (from.isAfter(to)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "from은 to보다 이후일 수 없습니다.");
        }

        return healthRecordRepository.findAllByRecordDateBetweenOrderByRecordDateAsc(from, to)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional
    public HealthRecordResponse update(Long id, HealthRecordUpdateRequest request) {
        if (request == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "요청 바디가 비었습니다.");
        }

        HealthRecordEntity entity = healthRecordRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 id의 기록이 없습니다. id=" + id));

        // 부분 수정(Null이면 유지)
        if (request.getWeightKg() != null) entity.setWeightKg(request.getWeightKg());
        if (request.getNote() != null) entity.setNote(request.getNote());

        HealthRecordEntity saved = healthRecordRepository.save(entity);
        return toResponse(saved);
    }

    @Transactional
    public void delete(Long id) {
        if (!healthRecordRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 id의 기록이 없습니다. id=" + id);
        }
        healthRecordRepository.deleteById(id);
    }

    private void validateCreateRequest(HealthRecordCreateRequest request) {
        if (request == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "요청 바디가 비었습니다.");
        }
        if (request.getRecordDate() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "recordDate는 필수입니다.");
        }
        // weightKg, note는 선택값으로 둠(원하면 여기서 범위 검증 추가 가능)
    }

    private HealthRecordResponse toResponse(HealthRecordEntity e) {
        return HealthRecordResponse.builder()
                .id(e.getId())
                .recordDate(e.getRecordDate())
                .weightKg(e.getWeightKg())
                .note(e.getNote())
                .createdAt(e.getCreatedAt())
                .updatedAt(e.getUpdatedAt())
                .build();
    }
}