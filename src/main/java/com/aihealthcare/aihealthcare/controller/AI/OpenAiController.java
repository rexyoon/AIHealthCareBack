package com.aihealthcare.aihealthcare.controller.AI;

import com.aihealthcare.aihealthcare.dto.request.QueryRequest;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.aihealthcare.aihealthcare.service.AiCoachService;

@RestController
@RequestMapping("/api/openai")
public class OpenAIController {

    private final AiCoachService aiCoachService;

    public OpenAIController(AiCoachService aiCoachService) {
        this.aiCoachService = aiCoachService;
    }

    @PostMapping("/query")
    public ResponseEntity<JsonNode> query(@RequestBody QueryRequest request) {
        String q = request.getQuery();

        if (q == null || q.isBlank()) {
            return ResponseEntity.badRequest().build();
        }

        JsonNode result = aiCoachService.analyze(q);
        return ResponseEntity.ok(result); // ✅ JSON 그대로 내려감
    }
}