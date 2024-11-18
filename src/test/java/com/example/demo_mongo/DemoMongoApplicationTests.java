package com.example.demo_mongo;

import com.example.demo_mongo.model.Config;
import com.example.demo_mongo.model.DocumentEntity;
import com.example.demo_mongo.repository.DocumentEntityRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
class DemoMongoApplicationTests {

	@Autowired
	DocumentEntityRepository repository;

	@Test
	void contextLoads() {
	}

	@Test
	public void should_perform_save() {
		var config = new Config("CFD", UUID.randomUUID().toString(), 0.1, 0.2);
		var entity = new DocumentEntity(UUID.randomUUID().toString(), "name", List.of("test_string", "test_string"), config);
		repository.save(entity);

		var saved = repository.findAll();
        assertEquals(1, saved.size());
	}

}
