package com.swm.studywithmentor.configuration;

import com.swm.studywithmentor.filter.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, PasswordEncoder passwordEncoder,
        UserDetailsService userDetailsService) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
            .userDetailsService(userDetailsService)
            .passwordEncoder(passwordEncoder)
            .and().build();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtAuthenticationFilter authenticationFilter)
        throws Exception {
        http.cors()
            .configurationSource(request -> {
                CorsConfiguration cors = new CorsConfiguration();
                //                cors.setAllowedOrigins(List.of("http://localhost:3000"));
                cors.setAllowedOriginPatterns(List.of("*"));
                cors.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));
                cors.setAllowedHeaders(List.of("*"));
                cors.setAllowCredentials(true);
                return cors;
            })
            .and()
            .csrf().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class)
            .authorizeRequests()
            .antMatchers("/test", "/login", "/login/google").permitAll()
            // Comment out the following line for the ease of testing
            .anyRequest().authenticated();
        return http.build();
    }
}
