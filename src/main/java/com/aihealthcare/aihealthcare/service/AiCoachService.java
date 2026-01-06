package com.aihealthcare.aihealthcare.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class AiCoachService {

    private final ChatClient chatClient;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public AiCoachService(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    private static final String SYSTEM_PROMPT = """
너는 보디빌더의 건강(혈압, 혈당, 체중) 변화를 분석하는 에이전트다.
너의 역할은 다음 중 하나를 선택하는 것이다.

1) 위험 판단 -> 경고 생성
2) 정상 판단 -> 요약 생성

규칙:
- 출력은 반드시 JSON만 출력한다. (설명 문장/마크다운/코드펜스 금지)
- decision은 "RISK" 또는 "NORMAL" 중 하나.
- metrics에는 bp_systolic, bp_diastolic, glucose, weight 숫자를 채운다.
- 입력에서 숫자를 추출할 수 없으면 decision="RISK"로 두고 warnings에 "입력값 파싱 실패"를 포함한다.

출력 JSON 스키마:
{
  "decision": "RISK"|"NORMAL",
  "summary": "string",
  "warnings": ["string", ...],
  "metrics": {
    "bp_systolic": number,
    "bp_diastolic": number,
    "glucose": number,
    "weight": number
  }
}
""";

    public JsonNode analyze(String userQuery) {

        String raw = chatClient.prompt()
                .system(SYSTEM_PROMPT)
                .user("""
입력값:
%s

이 입력값을 분석해서 위 스키마에 맞는 JSON만 출력해.
""".formatted(userQuery))
                .call()
                .content();

        try {

            return objectMapper.readTree(raw);

        } catch (Exception e) {


            ObjectNode root = objectMapper.createObjectNode();
            root.put("decision", "RISK");
            root.put("summary", "AI 응답 JSON 파싱 실패");

            ArrayNode warnings = objectMapper.createArrayNode();
            warnings.add("AI가 JSON이 아닌 형식으로 응답함");
            root.set("warnings", warnings);

            ObjectNode metrics = objectMapper.createObjectNode();
            metrics.put("bp_systolic", 0);
            metrics.put("bp_diastolic", 0);
            metrics.put("glucose", 0);
            metrics.put("weight", 0);
            root.set("metrics", metrics);

            return root; // JsonNode로 업캐스팅
        }
    }
}
