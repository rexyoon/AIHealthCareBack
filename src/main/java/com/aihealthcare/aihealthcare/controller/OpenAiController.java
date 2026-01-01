package com.aihealthcare.aihealthcare.controller;

import com.aihealthcare.aihealthcare.service.OpenAiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/openai")
public class OpenAiController{
    @Autowired
    private OpenAiService openAiService;
    @PostMapping("/query")
    public ResponseEntity<String> getOpenAIResponse(@RequestBody String userInput){
        String response = openAiService.getResponse(userInput);
        return ResponseEntity.ok(response);
    }
}