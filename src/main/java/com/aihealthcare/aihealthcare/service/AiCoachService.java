package com.aihealthcare.aihealthcare.service;

import com.aihealthcare.aihealthcare.domain.Blood.BloodMetricEntity;
import com.aihealthcare.aihealthcare.repository.BloodMetricRepository;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class AiCoachService {

    private final BloodMetricRepository bloodMetricRepository;
    private final ChatClient chatClient;

    //  ChatClient.Builder를 주입받아 build()로 클라이언트를 생성합니다.
    // (Lombok의 @RequiredArgsConstructor 대신 명시적 생성자 사용)
    public AiCoachService(BloodMetricRepository bloodMetricRepository, ChatClient.Builder builder) {
        this.bloodMetricRepository = bloodMetricRepository;
        this.chatClient = builder.build(); // 여기서 실제 ChatClient 객체가 생성됩니다.
    }

    public String analyzeBloodTest(Long testId) {
        // 1. DB에서 수치 데이터 조회
        BloodMetricEntity metrics = bloodMetricRepository.findByBloodTest_TestId(testId)
                .orElseThrow(() -> new RuntimeException("검사 결과가 없습니다."));

        // 2. 프롬프트 엔지니어링 (DB 데이터를 텍스트로 변환)
        String userHealthData = formatHealthData(metrics);

        // 3. 시스템 프롬프트 (코치의 페르소나 설정)
        String systemPrompt = """
            당신은 전문 보디빌딩 케어 코치입니다.
            사용자의 혈액 검사 결과를 분석하여, 약물 부작용 가능성과 건강 관리 조언을 제공하세요.
            
            [주의사항]
            - 반드시 '의학적 진단이 아님'을 명시할 것.
            - 수치가 정상 범위를 벗어난 경우 구체적인 식단/영양제/휴식 방법을 제안할 것.
            """;

        // 4. 사용자 프롬프트
        String userPrompt = "다음은 나의 최근 혈액 검사 결과야. 분석해줘:\n" + userHealthData;

        //  Fluent API 방식으로 변경 (.prompt -> .system -> .user -> .call)
        return chatClient.prompt()
                .system(systemPrompt)
                .user(userPrompt)
                .call()       // AI 호출
                .content();   // 결과 텍스트만 추출
    }

    // DB 객체를 프롬프트용 문자열로 예쁘게 바꾸는 함수
    private String formatHealthData(BloodMetricEntity m) {
        return String.format("""
            - 총 테스토스테론: %s
            - E2 (에스트로겐): %s
            - 프로락틴: %s
            - 간수치(AST/ALT): %s / %s
            - 신장(Creatinine): %s
            """,
                m.getTestosteroneTotal(),
                m.getEstradiolE2(),
                m.getProlactin(),
                m.getAst(), m.getAlt(),
                m.getCreatinine()
        );
    }
}
