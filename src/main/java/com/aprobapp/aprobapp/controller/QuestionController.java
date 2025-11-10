package com.aprobapp.aprobapp.controller;


import com.aprobapp.aprobapp.service.QuestionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@CrossOrigin
public class QuestionController {
    private final QuestionService questionService;
    public QuestionController (QuestionService questionService){
        this.questionService = questionService;
    }
    @GetMapping("/errorQuestion")
    public ResponseEntity<?> getPercentage(@RequestParam String email){
        return  ResponseEntity.ok(questionService.getErrorQuestions(email));
    }


}
