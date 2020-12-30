package com.runtimelearner.onlinegameengine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.runtimelearner.onlinegameengine.model.Webpage;

@RestController
@SpringBootApplication
public class OnlineGameEngineApplication {

	public static void main(String[] args) {
		SpringApplication.run(OnlineGameEngineApplication.class, args);
	}
	
	@RequestMapping("/")
	public String greeting() {
		return "Hello world!";
	}

}
