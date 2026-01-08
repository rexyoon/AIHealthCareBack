package com.aihealthcare.aihealthcare.service;

import com.aihealthcare.aihealthcare.domain.Blood.BloodMetricEntity;
import com.aihealthcare.aihealthcare.repository.BloodMetricRepository;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.PromptTemplate; // 템플릿 기능 추가
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AiCoachService {

    private final BloodMetricRepository bloodMetricRepository;
    private final ChatClient chatClient;

    public AiCoachService(BloodMetricRepository bloodMetricRepository, ChatClient.Builder builder) {
        this.bloodMetricRepository = bloodMetricRepository;
        this.chatClient = builder.build();
    }

    public String analyzeBloodTest(Long testId) {
        //DB 조회
        BloodMetricEntity metrics = bloodMetricRepository.findByBloodTest_TestId(testId)
                .orElseThrow(() -> new RuntimeException("검사 결과가 없습니다."));

        //시스템 프롬프트 (코치의 두뇌 설정)
        String systemText = """
            당신은 'Iron Logic'이라는 이름의 AI 보디빌딩 케어 코치입니다.
            사용자의 혈액 검사 수치를 바탕으로 퍼포먼스 향상과 건강 관리를 위한 조언을 제공합니다.
            
            [분석 프로토콜]
            1. 호르몬 패널: 테스토스테론과 에스트로겐(E2)의 균형을 최우선으로 분석하십시오.
            2. 내장 기관: AST/ALT(간) 및 Creatinine/eGFR(신장) 수치를 확인하여 보충제/약물 스트레스를 평가하십시오.
            3. 혈액 성상: 적혈구 증가증(Hematocrit) 여부를 체크하십시오.
            
            [어조 및 스타일]
            - 전문적이지만 동기부여가 되는 어조 ("회원님", "선수님" 호칭 사용).
            - 중요한 경고(수치 이탈)는 굵은 글씨(**)로 강조.
            - 결론에는 구체적인 행동 지침(Action Plan)을 3가지 제안하십시오.
            
            [치명적 경고 및 면책]
            - 당신은 의사가 아닙니다. 모든 조언 뒤에는 "반드시 전문의와 상담하십시오"라는 문구를 포함하십시오.
            - 즉각적인 위험(예: 칼륨 수치 폭등 등)이 보이면 즉시 병원 방문을 권고하십시오.
            """;

        //사용자 프롬프트 템플릿
        //Spring AI의 PromptTemplate을 사용하여 데이터를 주입
        String userPromptTemplate = """
            내 이번 혈액 검사 결과입니다. 보디빌딩 관점에서 분석해주세요.
            
            [호르몬]
            - 총 테스토스테론: {testosterone} ng/dL
            - 에스트로겐(E2): {estradiol} pg/mL
            - 프로락틴: {prolactin} ng/mL
            
            [간/신장 기능]
            - AST: {ast} IU/L
            - ALT: {alt} IU/L
            - 크레아티닌: {creatinine} mg/dL
            - eGFR: {egfr}
            
            [혈중 지질]
            - 총 콜레스테롤: {cholesterol} mg/dL
            
            위 수치를 바탕으로 현재 내 몸 상태와, 식단/운동/휴식 수정 방안을 알려주세요.
            """;

        //템플릿에 데이터 채워넣기
        PromptTemplate template = new PromptTemplate(userPromptTemplate);
        Map<String, Object> map = Map.of(
                "testosterone", metrics.getTestosteroneTotal(),
                "estradiol", metrics.getEstradiolE2(),
                "prolactin", metrics.getProlactin(),
                "ast", metrics.getAst(),
                "alt", metrics.getAlt(),
                "creatinine", metrics.getCreatinine(),
                "egfr", metrics.getEgfr(),
                "cholesterol", metrics.getCholesterolTotal()
        );

        //렌더링된 메시지 생성
        String filledUserMessage = template.render(map);

        //AI 호출
        return chatClient.prompt()
                .system(systemText)
                .user(filledUserMessage)
                .call()
                .content();
    }
}
