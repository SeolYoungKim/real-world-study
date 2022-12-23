package com.realworld.study;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication  //TODO 해당 애노테이션의 역할이 무엇인가? 빈 어떻게 등록되는지, DI는 어떻게 발생하는지? ApplicationContext ? SpringBoot Test
public class StudyApplication {
	public static void main(String[] args) {
		SpringApplication.run(StudyApplication.class, args);
	}
}
