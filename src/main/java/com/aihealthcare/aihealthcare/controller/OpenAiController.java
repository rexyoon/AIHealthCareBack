package com.aihealthcare.aihealthcare.controller;

import com.aihealthcare.aihealthcare.dto.request.QueryRequest;
import com.aihealthcare.aihealthcare.dto.response.QueryResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/openai")
public class OpenAIController {

    @PostMapping("/query")
    public ResponseEntity<QueryResponse> query(@RequestBody QueryRequest request) {
        String userQuery = request.getQuery();
        if (userQuery == null || userQuery.isBlank()) {
            return ResponseEntity.badRequest()
                    .body(new QueryResponse("query 값이 비어있습니다."));
        }

       String aiReply = """
                [AI COACH MOCK RESPONSE]
                입력한 내용: %s

                컨디션 분석 완료.
                오늘은 수분 섭취 늘리고, 고강도 운동은 피하세요.
                """.formatted(userQuery);

        return ResponseEntity.ok(new QueryResponse(aiReply));
    }
}
