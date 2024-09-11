package com.bharat.EcomUserAuthService.Security;

import io.jsonwebtoken.security.Keys;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import javax.crypto.SecretKey;

@Configuration
public class SpringSecurity {

    // SecurityFilterChain configuration
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.cors(cors -> cors.disable())   // Disabling CORS
                .csrf(csrf -> csrf.disable())   // Disabling CSRF
                .authorizeHttpRequests(authorize -> authorize.anyRequest().permitAll()); // Allow all requests
        return httpSecurity.build();
    }

    // BCryptPasswordEncoder Bean
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    // SecretKey Bean for JWT
    @Bean
    public SecretKey secretKey(){
        // Generating a SecretKey using HS256 algorithm
        return Keys.secretKeyFor(io.jsonwebtoken.SignatureAlgorithm.HS256);
    }

}
