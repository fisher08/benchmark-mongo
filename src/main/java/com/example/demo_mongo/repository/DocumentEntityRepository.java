package com.example.demo_mongo.repository;

import com.example.demo_mongo.model.DocumentEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DocumentEntityRepository extends MongoRepository<DocumentEntity, String> {

}
