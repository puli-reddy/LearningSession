package com.wissda.LearningSession.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import static com.wissda.LearningSession.constants.Routes.STUDENTS;

@Slf4j
@RestController
@RequestMapping("/test")
@CrossOrigin(origins="*")
public class TestController {
    @Value("spring.application.stage")
    private String stage;
    @GetMapping("/")
    public ResponseEntity<Object> test() {
        return new ResponseEntity<>(Map.of("Status","Live","Stage",stage), HttpStatus.OK);
    }
}
