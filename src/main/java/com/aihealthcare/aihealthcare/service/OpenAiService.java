package com.aihealthcare.aihealthcare.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OpenAiService {
    private final String OPENAI_API_URL = "https://api.openai.com/v1/engines/davinci-codex/completions"; // OpenAI API URL
    private final String API_KEY = "YOUR_OPENAI_API_KEY";
    public String getResponse(String userInput){
        RestTemplate restTemplate = new RestTemplate();
        return "OpenAI의 응답";
    }
}