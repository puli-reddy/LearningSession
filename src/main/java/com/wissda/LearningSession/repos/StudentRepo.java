package com.wissda.LearningSession.repos;

import com.wissda.LearningSession.dao.StudentDAO;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface StudentRepo extends MongoRepository<StudentDAO, String> {
    @Query("{email:?0}")
    StudentDAO getByEmail(String email);
}
