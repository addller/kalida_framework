package com.kalida.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class GeneralConfig {
    
    @Value("${security.salt}")
    private String salt;

    @Value("${security.pepper}")
    private String pepper;

    @Bean
    public String salt() {
        return salt;
    }

    @Bean
    public String pepper() {
        return pepper;
    }
}
