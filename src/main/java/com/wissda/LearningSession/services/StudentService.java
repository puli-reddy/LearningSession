package com.wissda.LearningSession.services;

import com.wissda.LearningSession.dao.StudentDAO;
import com.wissda.LearningSession.models.Address;
import com.wissda.LearningSession.repos.StudentRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class StudentService {
    @Autowired
    private StudentRepo studentRepo;

    public StudentDAO createStudent(final String studentName, final String studentEmail, final Address address) {
        StudentDAO studentDAO = StudentDAO.builder()
                .email(studentEmail)
                .name(studentName)
                .address(address)
                .build();

        StudentDAO response = studentRepo.save(studentDAO);
        return response;
    }

    public StudentDAO getStudentById(final String studentId) {
        Optional<StudentDAO> response = studentRepo.findById(studentId);
        return response.orElse(null);
    }

    public List<StudentDAO> getStudents() {
        List<StudentDAO> response = studentRepo.findAll();
        return response;
    }

    public StudentDAO getStudentByEmail(final String email) {
        StudentDAO response = studentRepo.getByEmail(email);
        return response;
    }
}
