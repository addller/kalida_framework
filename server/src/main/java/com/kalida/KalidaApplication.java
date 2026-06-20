package com.kalida;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

@ComponentScan(basePackages = { 
	"com.kalida.config", 
	"com.kalida.service", 
	"com.kalida.security",
	"com.kalida.resources",
	"com.kalida.exception"
})
@EnableSpringDataWebSupport(pageSerializationMode = EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO)
@EnableJpaRepositories(basePackages = {"com.kalida.repository"})
@EntityScan(basePackages = {"com.kalida.model"})
@SpringBootApplication
public class KalidaApplication {

	public static void main(String[] args) {
		SpringApplication.run(KalidaApplication.class, args);
	}
}

