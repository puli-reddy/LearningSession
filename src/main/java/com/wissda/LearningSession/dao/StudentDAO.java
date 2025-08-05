package com.wissda.LearningSession.dao;

import com.wissda.LearningSession.models.Address;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import static com.wissda.LearningSession.constants.Collections.STUDENT;

@Document(collection = STUDENT)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StudentDAO {
    @Id
    private String id;
    private String name;
    private String email;
    private Address address;
}