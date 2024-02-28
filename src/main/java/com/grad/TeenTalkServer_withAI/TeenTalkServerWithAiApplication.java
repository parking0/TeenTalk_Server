package com.grad.TeenTalkServer_withAI;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TeenTalkServerWithAiApplication {

	public static void main(String[] args) {
		SpringApplication.run(TeenTalkServerWithAiApplication.class, args);
	}

}

// docker run -p 8000:8000 --name rest_api gj98/test_server:latest