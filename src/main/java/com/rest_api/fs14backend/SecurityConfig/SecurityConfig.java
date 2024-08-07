package com.rest_api.fs14backend.SecurityConfig;


import com.rest_api.fs14backend.filters.JwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;


import java.util.Arrays;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfig {

  @Autowired
  private JwtFilter jwtFilter;

  @Bean
  public AuthenticationManager authenticationManager(
    AuthenticationConfiguration authenticationConfiguration
  ) throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
  
  @Bean
  public CorsFilter corsFilter() {
    CorsConfiguration corsConfiguration = new CorsConfiguration();
    corsConfiguration.setAllowCredentials(true);
    corsConfiguration.setAllowedOrigins(Arrays.asList("http://localhost:3000/","http://127.0.0.1:3000/","https://boimela.netlify.app/"));
    corsConfiguration.setAllowedHeaders(Arrays.asList("Origin", "Access-Control-Allow-Origin", "Content-Type",
        "Accept", "Authorization", "Origin, Accept", "X-Requested-With",
        "Access-Control-Request-Method", "Access-Control-Allow-Origin", " Access-Control-Allow-Credentials"));
    corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
    UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
    urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
    return new CorsFilter(urlBasedCorsConfigurationSource);
  }
  

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
      .csrf()
      .disable()
      .authorizeHttpRequests()
      
        .requestMatchers("/signup", "/signin","/swagger-ui/**","/swagger-ui.html","/v3/api-docs/**")
        .permitAll()
        .and()
        .authorizeHttpRequests()
        .requestMatchers(HttpMethod.GET, "/api/v1/books/","/api/v1/authors/","/api/v1/categories/")
        .permitAll()
        
          
          .requestMatchers(HttpMethod.GET, "/users").hasRole("ADMIN")
          .requestMatchers(HttpMethod.POST, "/api/v1/books/").hasRole("ADMIN")
          .requestMatchers(HttpMethod.PUT, "/api/v1/books/{isbn}").hasRole("ADMIN")
          .requestMatchers(HttpMethod.DELETE, "/api/v1/books/{isbn}").hasRole("ADMIN")
          .requestMatchers(HttpMethod.POST, "/api/v1/authors/").hasRole("ADMIN")
          .requestMatchers(HttpMethod.PUT, "/api/v1/authors/{id}").hasRole("ADMIN")
          .requestMatchers(HttpMethod.DELETE, "/api/v1/authors/{id}").hasRole("ADMIN")
          .requestMatchers(HttpMethod.POST, "/api/v1/categories/").hasRole("ADMIN")
          .requestMatchers(HttpMethod.PUT, "/api/v1/categories/{id}").hasRole("ADMIN")
          .requestMatchers(HttpMethod.DELETE, "/api/v1/categories/{id}").hasRole("ADMIN")
          .requestMatchers(HttpMethod.GET, "/api/v1/loan/all").hasRole("ADMIN")
          .requestMatchers(HttpMethod.GET, "/api/v1/loan/{username}").hasRole("USER")
          .requestMatchers(HttpMethod.POST, "/api/v1/loan/").hasAnyRole("USER","ADMIN")
          .requestMatchers(HttpMethod.PUT, "/api/v1/loan/{id}").hasAnyRole("USER","ADMIN")
          .anyRequest().authenticated()
          
      .and()
      .sessionManagement()
      .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
      .and()
      
      
        .addFilter(corsFilter())
      // Add JWT token filter
      .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }
}
