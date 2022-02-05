package com.map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class MapApplication extends SpringBootServletInitializer{

	public static void main(String[] args) {
		SpringApplication.run(MapApplication.class, args);
	}

}
