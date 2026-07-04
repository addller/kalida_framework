package com.kalida.security;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(
    prePostEnabled = true,
    securedEnabled = true,
    jsr250Enabled = true
)//habilitando o perfil para determinado end point
public class SecurityConfig{
    
    @Autowired
    private Environment environment;

    private final JwtTokenProvider jwtTokenProvider;
    private final UserDetailsService userDetailsService;
    
    @Autowired
    private AuthenticationConfiguration authenticationConfiguration;

    private static final String[] PUBLIC_MATCHERS = {
        "/h2/**",
        "/api-docs.html",
        "/api-docs/**",
        "/swagger-ui/**"
    };
    
    private static final String[] PUBLIC_MATCHERS_GET = {
        "/auth/email/confirmation",
        "/auth/email/validation",
        "/auth/cache",
        "/auth/no-cache"
    };
    
    private static final String[] PUBLIC_MATCHERS_POST = {
        "/users/register",
        "/auth/signin",
        "/auth/access/recovery",
        "/auth/access/change/password",
        "/auth/create_cliente"
    };

    public SecurityConfig(JwtTokenProvider jwtTokenProvider, UserDetailsService userDetailsService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        if(Arrays.asList(environment.getActiveProfiles()).contains("test")){
            http.headers(headers -> headers.frameOptions(frameOptions -> frameOptions.disable()));//libera o acesso ao h2
        }

        //desabilitando a proteção cross request forgery pois a aplicação é statless
        //http.cors().and().csrf().disable();
        http.cors(cors -> cors.configurationSource(corsConfigurationSource()));
        http.csrf(csrf -> csrf.disable());

        http.authorizeHttpRequests(auth -> auth
            .requestMatchers(PUBLIC_MATCHERS).permitAll()
            .requestMatchers(HttpMethod.POST, PUBLIC_MATCHERS_POST).permitAll()
            .requestMatchers(HttpMethod.GET, PUBLIC_MATCHERS_GET).permitAll()
            .anyRequest().authenticated()
        );

        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        
        http.addFilter(new JwtAuthenticationFilter(authenticationConfiguration.getAuthenticationManager(), jwtTokenProvider));//adicionando filtro jwt
        http.addFilter(new JwtAuthorizationFilter(authenticationConfiguration.getAuthenticationManager(), jwtTokenProvider, userDetailsService));

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource(){
        UrlBasedCorsConfigurationSource urlSource = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfiguration = new CorsConfiguration().applyPermitDefaultValues();
        corsConfiguration.addAllowedOrigin("http://localhost:5500");
        corsConfiguration.setAllowedMethods(Arrays.asList("POST", "GET", "PUT", "DELETE", "OPTIONS"));
        urlSource.registerCorsConfiguration("/**", corsConfiguration);
        return urlSource;
    }

}