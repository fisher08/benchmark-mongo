package com.example.demo_mongo;

import org.springframework.boot.SpringApplication;

public class TestDemoMongoApplication {

	public static void main(String[] args) {
		SpringApplication.from(DemoMongoApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
