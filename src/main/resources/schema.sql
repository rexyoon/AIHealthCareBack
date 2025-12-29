-- 1. 데이터베이스 생성 및 선택
CREATE DATABASE IF NOT EXISTS aihealthcaredb;
USE aihealthcaredb;

-- 2. 사용자 테이블 (Users)
-- AI 분석의 기초가 되는 신체 정보를 포함합니다.
CREATE TABLE users (
                       user_id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '사용자 고유 ID',

    -- 로그인 정보
                       login_id VARCHAR(50) NOT NULL UNIQUE COMMENT '로그인 아이디',
                       password VARCHAR(255) NOT NULL COMMENT '비밀번호 (암호화 필수)',
                       username VARCHAR(50) NOT NULL COMMENT '사용자 이름',

    -- AI 분석용 필수 정보 (BMI 및 연령별 기준 산출용)
                       gender ENUM('M', 'F') NOT NULL COMMENT '성별 (M: 남성, F: 여성)',
                       birth_date DATE NOT NULL COMMENT '생년월일 (나이 계산용)',
                       height DOUBLE NOT NULL COMMENT '키 (cm) - BMI 계산용',

                       created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
                       updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- 3. 건강 기록 테이블 (Health Records)
-- 사용자가 매일 입력하는 혈압, 혈당, 몸무게 데이터입니다.
CREATE TABLE health_records (
                                record_id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '기록 고유 ID',
                                user_id BIGINT NOT NULL COMMENT 'users 테이블 참조',

    -- 핵심 4대 건강 지표
                                blood_sugar DOUBLE COMMENT '혈당 (mg/dL)',
                                weight DOUBLE COMMENT '몸무게 (kg)',
                                systolic_bp INT COMMENT '수축기(최고) 혈압 (mmHg)',
                                diastolic_bp INT COMMENT '이완기(최저) 혈압 (mmHg)',

    -- 기타 기록
                                note TEXT COMMENT '사용자가 남기는 특이사항(메모)',
                                recorded_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '측정 일시',

    -- 외래키 설정 (사용자 삭제 시 기록도 삭제)
                                FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);

-- 4. AI 분석 로그 테이블 (AI Analysis Logs)
-- AI가 사용자의 기록을 보고 해준 조언을 저장합니다. (비용 절약 & 이력 관리)
CREATE TABLE ai_analysis_logs (
                                  log_id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '로그 고유 ID',
                                  user_id BIGINT NOT NULL COMMENT 'users 테이블 참조',
                                  record_id BIGINT COMMENT '특정 health_records 기록에 대한 분석인 경우 참조',

    -- AI 분석 결과
                                  health_status VARCHAR(50) COMMENT 'AI가 판단한 상태 요약 (예: 주의, 위험, 양호)',
                                  exercise_advice TEXT COMMENT '추천 운동 가이드',
                                  diet_advice TEXT COMMENT '식단 조언',
                                  full_response TEXT COMMENT 'AI 응답 원본 전체 (디버깅/백업용)',

                                  created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '분석 일시',

                                  FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
                                  FOREIGN KEY (record_id) REFERENCES health_records(record_id) ON DELETE SET NULL
);

-- 5. 목표 테이블 (Health Goals) - 선택 사항
-- "목표 체중까지 3kg 남았습니다!" 같은 멘트를 위해 필요합니다.
CREATE TABLE health_goals (
                              goal_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                              user_id BIGINT NOT NULL,

                              target_weight DOUBLE COMMENT '목표 몸무게 (kg)',
                              target_blood_sugar DOUBLE COMMENT '목표 혈당 (mg/dL)',

                              start_date DATE DEFAULT (CURRENT_DATE) COMMENT '목표 시작일',
                              end_date DATE COMMENT '목표 달성 예정일',

                              FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);
