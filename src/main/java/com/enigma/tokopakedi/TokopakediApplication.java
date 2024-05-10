package com.enigma.tokopakedi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@SpringBootApplication
public class TokopakediApplication {

	public static void main(String[] args) {
		SpringApplication.run(TokopakediApplication.class, args);
	}

}
