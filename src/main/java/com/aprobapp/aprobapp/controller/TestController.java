package com.aprobapp.aprobapp.controller;

import com.aprobapp.aprobapp.service.QuestionService;
import com.aprobapp.aprobapp.service.TestService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@CrossOrigin
public class TestController {
    private final TestService testService;

    public TestController(TestService testService) {
        this.testService = testService;
    }

    @GetMapping("/testPassed")
    public ResponseEntity<?> getTestOk(@RequestParam String email) {
        return ResponseEntity.ok(testService.getApprovedTests(email));
    }

    @GetMapping("/testAverage")
    public ResponseEntity<?> getAverageTest(@RequestParam String email) {
        return ResponseEntity.ok(testService.getAverageGrade(email));
    }
}
