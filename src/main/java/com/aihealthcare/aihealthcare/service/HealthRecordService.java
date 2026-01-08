package com.aihealthcare.aihealthcare.service;

// [중요 1] HealthRecord 파일 자체의 위치 (보내주신 코드 기준 domain 패키지)
import com.aihealthcare.aihealthcare.domain.health.HealthRecord;

// [중요 2] HealthRecord 안에서 import 하고 있는 User의 위치 (domain.user 패키지)

import com.aihealthcare.aihealthcare.dto.request.HealthRecordRequest;
import com.aihealthcare.aihealthcare.dto.response.HealthRecordResponse;
import com.aihealthcare.aihealthcare.repository.Health.HealthRecordRepository;
import com.aihealthcare.aihealthcare.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HealthRecordService {

    private final HealthRecordRepository healthRecordRepository;
    private final UserRepository userRepository;

    // 1. 생성 (Create)
    @Transactional
    public Long createRecord(HealthRecordRequest request) {
        // 이제 UserRepository에서 찾아온 User 객체와
        // HealthRecord가 필요로 하는 User 객체의 타입이 (domain.user.User)로 정확히 일치합니다.
        User user = userRepository.findById(request.userId())
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다. id=" + request.userId()));

        HealthRecord record = HealthRecord.builder()
                .user(user) // 타입 일치! 오류 해결됨
                .bloodSugar(request.bloodSugar())
                .weight(request.weight())
                .systolicBp(request.systolicBp())
                .diastolicBp(request.diastolicBp())
                .note(request.note())
                .build();

        return healthRecordRepository.save(record).getId();
    }

    // 2. 조회 - 특정 유저의 전체 기록 (Read)
    public List<HealthRecordResponse> getRecordsByUserId(Long userId) {
        return healthRecordRepository.findAllByUserIdOrderByRecordedAtDesc(userId).stream()
                .map(HealthRecordResponse::from)
                .collect(Collectors.toList());
    }

    // 3. 수정 (Update)
    @Transactional
    public void updateRecord(Long recordId, HealthRecordRequest request) {
        HealthRecord record = healthRecordRepository.findById(recordId)
                .orElseThrow(() -> new IllegalArgumentException("기록이 없습니다. id=" + recordId));

        record.update(
                request.bloodSugar(),
                request.weight(),
                request.systolicBp(),
                request.diastolicBp(),
                request.note()
        );
    }

    // 4. 삭제 (Delete)
    @Transactional
    public void deleteRecord(Long recordId) {
        HealthRecord record = healthRecordRepository.findById(recordId)
                .orElseThrow(() -> new IllegalArgumentException("기록이 없습니다. id=" + recordId));
        healthRecordRepository.delete(record);
    }
}
