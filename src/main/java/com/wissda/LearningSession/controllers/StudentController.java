package com.wissda.LearningSession.controllers;

import com.wissda.LearningSession.components.StudentComponent;
import com.wissda.LearningSession.dao.StudentDAO;
import com.wissda.LearningSession.dto.StudentDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.wissda.LearningSession.constants.Routes.*;

@Slf4j
@RestController
@RequestMapping(STUDENTS)
@CrossOrigin(origins="*")
public class StudentController {
    @Autowired
    private StudentComponent studentComponent;

    @PostMapping(CREATE)
    public ResponseEntity<Object> createStudent(@RequestBody final StudentDTO studentDTO) {
        log.info("Invoked createStudent API");
        log.info(studentDTO.getName());
        StudentDAO response = studentComponent.createStudent(studentDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(GET)
    public ResponseEntity<Object> getStudents() {
        List<StudentDAO> response = studentComponent.getAllStudents();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(BY_ID)
    public ResponseEntity<Object> getStudentById(@RequestBody final StudentDTO studentDTO) {
        StudentDAO response = studentComponent.getStudentById(studentDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(BY_EMAIL)
    public ResponseEntity<Object> getStudentByEmail(@RequestBody final StudentDTO studentDTO) {
        StudentDAO response = studentComponent.getStudentByEmail(studentDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
