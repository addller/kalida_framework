package com.kalida.security;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
    prePostEnabled = true,
    securedEnabled = true,
    jsr250Enabled = true
)//habilitando o perfil para determinado end point
public class SecurityConfig extends WebSecurityConfigurerAdapter{
    
    @Autowired
    private Environment environment;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    private static final String[] PUBLIC_MATCHERS = {
        "/h2/**",
        "/favicon.ico"
    };
    
    private static final String[] PUBLIC_MATCHERS_GET = {
        
    };
    
    private static final String[] PUBLIC_MATCHERS_POST = {
        "/auth/signin",
        "/users/**"
    };


    @Override
    protected void configure(HttpSecurity http) throws Exception {

        if(Arrays.asList(environment.getActiveProfiles()).contains("test")){
            http.headers().frameOptions().disable();//libera o acesso ao h2
        }

        http.cors().and().csrf().disable();//desabilitando a proteção cross request forgery pois a aplicação é statless

        http.authorizeRequests()
            .antMatchers(PUBLIC_MATCHERS).permitAll()//permitindo caminhos publicos
            .antMatchers(HttpMethod.GET, PUBLIC_MATCHERS_GET).permitAll()//permitindo caminhos publicos com get
            .antMatchers(HttpMethod.POST, PUBLIC_MATCHERS_POST).permitAll()
            .anyRequest().authenticated();//demais requisições, necessitam de autenticação

        http.addFilter(new JwtAuthenticationFilter(authenticationManager(), jwtTokenProvider));//adicionando filtro jwt
        http.addFilter(new JwtAuthorizationFilter(authenticationManager(), jwtTokenProvider, userDetailsService));

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    //configuração de autenticação
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource(){
        UrlBasedCorsConfigurationSource urlSource = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfiguration = new CorsConfiguration().applyPermitDefaultValues();
        corsConfiguration.setAllowedMethods(Arrays.asList("POST", "GET", "PUT", "DELETE", "OPTIONS"));
        urlSource.registerCorsConfiguration("/**", corsConfiguration);
        return urlSource;
    }
    
    @Bean
    BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}


}
