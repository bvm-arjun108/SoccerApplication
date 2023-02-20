package com.cricket.User.Application.config;

import com.cricket.User.Application.filter.JWTTokenGeneratorFilter;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Collections;
import java.util.List;

@Configuration
@EnableWebSecurity
public class ProjectSecurityConfig {
    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        CsrfTokenRequestAttributeHandler requestHandler = new CsrfTokenRequestAttributeHandler();
        requestHandler.setCsrfRequestAttributeName("_csrf");
         http
                //Does not genereate any j-session ids. We are taking care of own session ids and token management inside the web application.
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                //When we generate a jwt token in our web application, we are going to send that token to the client application inside the response as a header.
                // So,inside cors configuration, we need to define those details telling the spring security to allow to send these header information as part of the response from the backend application to the client application.
                // Then only the browser is going to accept the new header which we are sending as part of the response.
                .cors().configurationSource(new CorsConfigurationSource() {
                    @Override
                    public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                        CorsConfiguration corsConfiguration = new CorsConfiguration();
                        corsConfiguration.setAllowedOrigins(Collections.singletonList("*")); // Url of your front end application.
                        corsConfiguration.setAllowedMethods(Collections.singletonList("*"));
                        corsConfiguration.setAllowCredentials(true);
                        corsConfiguration.setAllowedHeaders(Collections.singletonList("*"));
                        corsConfiguration.setExposedHeaders(List.of("Authorization"));
                        corsConfiguration.setMaxAge(3600L);
                        return corsConfiguration;
                    }
                }).and()
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/api/v1/user/login", "/api/v1/user/register")
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                        .and()
                        .addFilterAfter(new JWTTokenGeneratorFilter(), BasicAuthenticationFilter.class))
                .authorizeHttpRequests()
                .requestMatchers("/user/registerUser").permitAll()
                .requestMatchers("/user/login").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin();
         return http.build();
    }
}


