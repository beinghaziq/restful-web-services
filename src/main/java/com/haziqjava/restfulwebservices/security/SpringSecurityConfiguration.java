package com.haziqjava.restfulwebservices.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SpringSecurityConfiguration {
  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//    To authenticate all requests
    http.authorizeHttpRequests(auth -> auth.anyRequest().authenticated());
//    A web page should be shown if request is not authenticated
    http.httpBasic(withDefaults());
    http.csrf().disable();
    return http.build();
  }

}
