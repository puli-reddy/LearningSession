package com.wissda.LearningSession.components;

import com.wissda.LearningSession.dao.StudentDAO;
import com.wissda.LearningSession.dto.StudentDTO;
import com.wissda.LearningSession.services.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class StudentComponent {
    @Autowired
    private StudentService studentService;

    public StudentDAO createStudent(final StudentDTO studentDTO) {
        log.info("Student Name : "+ studentDTO.getName());
        StudentDAO response = studentService.createStudent(studentDTO.getName(), studentDTO.getEmail(), studentDTO.getAddress());
        log.info(response.getEmail());
        return response;
    }

    public List<StudentDAO> getAllStudents() {
        List<StudentDAO> response = studentService.getStudents();
        return response;
    }

    public StudentDAO getStudentById(final StudentDTO studentDTO) {
        StudentDAO response = studentService.getStudentById(studentDTO.getStudentId());
        return response;
    }

    public void deleteStudentById(final StudentDTO studentDTO) {
        studentService.deleteStudentById(studentDTO.getStudentId());
    }

    public StudentDAO getStudentByEmail(final StudentDTO studentDTO) {
        StudentDAO response = studentService.getStudentByEmail(studentDTO.getEmail());
        return response;
    }
}
