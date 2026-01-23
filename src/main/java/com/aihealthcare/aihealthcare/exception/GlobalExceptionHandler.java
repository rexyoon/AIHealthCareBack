package com.aihealthcare.aihealthcare.exception;

import com.aihealthcare.aihealthcare.common.ApiResponse;
import org.springframework.ai.retry.NonTransientAiException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

/**
 * 전역 예외 처리기
 * - CustomException: ErrorCode 기반으로 status/message 반환
 * - OpenAI/Spring AI 관련 예외(특히 429 quota): 429로 그대로 반환
 * - 그 외: 500
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ApiResponse<Void>> handleCustom(CustomException e) {
        ErrorCode code = e.getErrorCode();
        return ResponseEntity
                .status(code.getHttpStatus())
                .body(ApiResponse.fail(code.getMessage()));
    }

    /**
     * Spring AI(OpenAI) 호출 중 NonTransientAiException 처리
     * - OpenAI quota 초과/한도 관련(HTTP 429, insufficient_quota)을 429로 그대로 내려줌
     */
    @ExceptionHandler(NonTransientAiException.class)
    public ResponseEntity<ApiResponse<Void>> handleNonTransientAi(NonTransientAiException e) {
        String msg = e.getMessage() == null ? "" : e.getMessage();

        // OpenAI 쿼터/한도 초과 케이스
        if (msg.contains("HTTP 429") || msg.contains("insufficient_quota")) {
            return ResponseEntity
                    .status(HttpStatus.TOO_MANY_REQUESTS)
                    .body(ApiResponse.fail("OpenAI 사용 한도(쿼터)가 초과되었습니다. 결제/크레딧을 확인해주세요."));
        }

        // 기타 NonTransient 오류는 502로 처리(업스트림 장애 성격)
        return ResponseEntity
                .status(HttpStatus.BAD_GATEWAY)
                .body(ApiResponse.fail("AI 요청 처리 중 오류가 발생했습니다. 잠시 후 다시 시도해주세요."));
    }

    /**
     * 나머지 예외
     * - 개발 중이면 e.printStackTrace()로 원인 파악에 도움
     * - 운영에서는 로깅 프레임워크(log.error)로 바꾸는 걸 추천
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleException(Exception e) {
        e.printStackTrace(); // 개발 편의. 운영에서는 logger로 변경 추천
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.fail("Server Error"));
    }
    @ExceptionHandler(ApiException.class)
    public ResponseEntity<?> handleApi(ApiException e) {
        return ResponseEntity
                .status(e.getStatus())
                .body(Map.of("message", e.getMessage()));
    }
}
