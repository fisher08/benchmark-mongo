package com.example.demo_mongo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Document
public class DocumentEntity {

    @Id
    private String id;
    private String name;
    private List<String> nestedArr;
    private Config config;
}
