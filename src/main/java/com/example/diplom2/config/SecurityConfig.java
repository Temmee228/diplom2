package com.example.diplom2.config;

import com.example.diplom2.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final CustomLoginSuccessHandler customLoginSuccessHandler;
    @Autowired
    private UserServiceImpl userService;

    public SecurityConfig(CustomLoginSuccessHandler customLoginSuccessHandler) {
        this.customLoginSuccessHandler = customLoginSuccessHandler;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(form -> form
                        .loginPage("/login")
                        .successHandler(customLoginSuccessHandler).permitAll())
                .authorizeHttpRequests(x -> x
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/user").hasRole("USER")
                        .requestMatchers("/org/**").hasAnyRole("ORG","ADMIN")
                        .requestMatchers("/unknown/**").permitAll()
                        .requestMatchers("/registration").permitAll()
                        .requestMatchers("/public/**", "/login", "/register").permitAll()
                        .requestMatchers("/error").permitAll()
                        .anyRequest().authenticated()

                );
        return http.build();


    }

    @Bean
    public PasswordEncoder passwordEncoder () {
        return NoOpPasswordEncoder.getInstance();
    }
}