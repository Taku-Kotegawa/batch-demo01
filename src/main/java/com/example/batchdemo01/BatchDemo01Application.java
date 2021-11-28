package com.example.batchdemo01;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableBatchProcessing
@SpringBootApplication
public class BatchDemo01Application {

	public static void main(String[] args) {
		SpringApplication.run(BatchDemo01Application.class, args);
	}

}
