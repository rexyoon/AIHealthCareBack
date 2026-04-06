package com.aihealthcare.aihealthcare.service;

import com.aihealthcare.aihealthcare.domain.Blood.BloodMetricEntity;
import com.aihealthcare.aihealthcare.repository.BloodMetricRepository;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class AiCoachService {

    private final BloodMetricRepository bloodMetricRepository;
    private final ChatClient chatClient;

    // 구분자 상수 선언 (파싱 오류 방지)
    private static final String SECTION_DELIMITER = "@@@SECTION_BREAK@@@";

    public AiCoachService(BloodMetricRepository bloodMetricRepository, ChatClient.Builder builder) {
        this.bloodMetricRepository = bloodMetricRepository;
        this.chatClient = builder.build();
    }

    public String analyzeBloodTest(Long testId) {
        BloodMetricEntity metrics = bloodMetricRepository.findByBloodTest_TestId(testId)
                .orElseThrow(() -> new RuntimeException("검사 결과가 없습니다."));

        // 1. 시스템 프롬프트: AI의 페르소나와 출력 포맷 정의
        String systemText = """
            당신은 'Iron Logic'이라는 엘리트 AI 보디빌딩 케어 코치입니다.
            사용자의 혈액 데이터를 분석하여 '퍼포먼스 최적화'와 '건강 리스크 관리'를 동시에 수행합니다.
            
            [핵심 분석 로직 및 보디빌딩 특수성 고려]
            1. 간 기능(AST/ALT): 수치가 높더라도, 운동 강도가 높았다면 근육 손상에 의한 상승일 가능성을 고려하여 질문하십시오.
            2. 신장 기능(Creatinine): 크레아틴 보충제 섭취나 많은 근육량으로 인해 eGFR이 낮게 나올 수 있음을 감안하십시오.
            3. 호르몬(Testosterone/E2): 단순 수치보다 비율(Ratio)과 부작용(여유증, 성기능 등) 가능성을 중심으로 분석하십시오.
            4. 데이터 처리: 입력값에 'N/A'가 있다면 "데이터 없음"으로 간주하고, 절대 추측하여 값을 생성하지 마십시오.
            
            [ 안전 및 면책 프로토콜]
            - 당신은 의사가 아닙니다. 진단명을 확정 짓지 마십시오.
            - 약물 사용이 의심되어도 도덕적 판단을 하지 말고, 오직 '위험 관리(Harm Reduction)' 관점에서 조언하십시오.
            - 생명에 위협이 될 수 있는 수치(칼륨 이상, 급성 간부전 징후 등)는 즉시 응급실/병원 방문을 최우선으로 권고하십시오.
            
            [📤 출력 형식 - 반드시 아래 포맷을 엄수하십시오]
            두 섹션 사이에는 반드시 정확히 "%s"를 구분자로 넣어야 합니다.
            
            SECTION 1: MACHINE_JSON (프론트엔드 연동용, 코드블록 금지)
            {
              "health_score": (0~100 정수),
              "risk_level": ("NORMAL" | "CAUTION" | "DANGER"),
              "theme_color": ("#HEXCODE"),
              "summary_title": "강렬한 한 줄 요약",
              "abnormal_items": [ {"name": "항목명", "value": "수치", "status": "HIGH/LOW", "comment": "짧은 코멘트"} ],
              "action_checklist": ["행동지침1", "행동지침2", "행동지침3"]
            }
            
            %s
            
            SECTION 2: HUMAN_REPORT (사용자 가독성용 Markdown)
            - 호칭: '선수님' 또는 '회원님'
            - 구조:
              1.  긴급 점검 (위험 요소가 있을 때만 표시)
              2.  현재 상태 디브리핑 (보디빌딩 관점 해석)
              3.  Iron Logic 솔루션 (영양/훈련/휴식)
              4.  닥터에게 질문하기 (병원 방문 시 물어볼 질문)
            """.formatted(SECTION_DELIMITER, SECTION_DELIMITER);

        // 2. 사용자 프롬프트: 실제 데이터 주입
        String userPromptTemplate = """
            다음은 사용자의 혈액 검사 결과입니다. 값이 'N/A'인 항목은 분석에서 제외하십시오.
            
            [기본 정보]
            - 검사 목적: 보디빌딩 퍼포먼스 및 건강 관리
            
            [Data Payload]
            - Testosterone Total: {testosterone} ng/dL
            - Testosterone Free: {freeTestosterone} ng/dL
            - E2 (Estradiol): {estradiol} pg/mL
            - Prolactin: {prolactin} ng/mL
            - LH: {lh} mIU/mL
            - FSH: {fsh} mIU/mL
            
            - AST: {ast} IU/L
            - ALT: {alt} IU/L
            - Creatinine: {creatinine} mg/dL
            - eGFR: {egfr}
            - BUN: {bun} mg/dL
            
            - Cholesterol Total: {cholesterol} mg/dL
            - LDL: {ldl} mg/dL
            - HDL: {hdl} mg/dL
            - Triglycerides: {triglycerides} mg/dL
            
            - Sodium: {sodium} mmol/L
            - Potassium: {potassium} mmol/L
            - CPK(CK): {cpk} (만약 정보가 없다면 N/A)
            
            위 데이터를 바탕으로 분석을 시작하십시오.
            """;

        PromptTemplate template = new PromptTemplate(userPromptTemplate);
        Map<String, Object> map = new LinkedHashMap<>();

        // 데이터 매핑 (기존 로직 유지)
        mapData(map, metrics);
        // CPK 같이 엔티티에 없지만 중요한 건 추후 추가 고려 (현재는 N/A 처리됨)
        putSafe(map, "cpk", null);

        return chatClient.prompt()
                .system(systemText)
                .user(template.render(map))
                .call()
                .content();
    }

    private void mapData(Map<String, Object> map, BloodMetricEntity metrics) {
        putSafe(map, "testosterone", metrics.getTestosteroneTotal());
        putSafe(map, "freeTestosterone", metrics.getTestosteroneFree());
        putSafe(map, "estradiol", metrics.getEstradiolE2());
        putSafe(map, "prolactin", metrics.getProlactin());
        putSafe(map, "lh", metrics.getLh());
        putSafe(map, "fsh", metrics.getFsh());

        putSafe(map, "ast", metrics.getAst());
        putSafe(map, "alt", metrics.getAlt());
        putSafe(map, "creatinine", metrics.getCreatinine());
        putSafe(map, "egfr", metrics.getEgfr());
        putSafe(map, "bun", metrics.getBun());

        putSafe(map, "cholesterol", metrics.getCholesterolTotal());
        putSafe(map, "ldl", metrics.getLdl());
        putSafe(map, "hdl", metrics.getHdl());
        putSafe(map, "triglycerides", metrics.getTriglycerides());

        putSafe(map, "sodium", metrics.getSodium());
        putSafe(map, "potassium", metrics.getPotassium());
    }

    private void putSafe(Map<String, Object> map, String key, Object value) {
        // AI가 "N/A" 텍스트를 인식하도록 명시적 문자열 변환
        map.put(key, value != null ? value.toString() : "N/A");
    }
}
