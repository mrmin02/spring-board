package com.busyvacation.aboard;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import com.busyvacation.aboard.config.StorageConfig;
import com.busyvacation.aboard.db.service.StorageService;

@SpringBootApplication
@EnableConfigurationProperties(StorageConfig.class)
public class AboardApplication {

	public static void main(String[] args) {
		SpringApplication.run(AboardApplication.class, args);
	}
	@Bean  
	CommandLineRunner init(StorageService storageService) {   
		return (args) -> {    
			System.out.println("폴더 생성");
			storageService.init();   // 파일들을 저장할 폴더가 없는 경우 만들어 줌.
		};  
	} 
}
