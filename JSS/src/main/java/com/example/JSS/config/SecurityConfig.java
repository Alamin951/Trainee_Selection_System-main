package com.example.JSS.config;

import com.example.JSS.filter.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .cors()
                .and()
                .csrf()
                .disable()
                .authorizeHttpRequests()
                .requestMatchers("/user/register", "/user/login", "/applicants/register")
                .permitAll()

                .requestMatchers(HttpMethod.GET, "/job_circular")
                .hasAnyAuthority("applicant", "admin")
                .requestMatchers(HttpMethod.POST, "/applications")
                .hasAnyAuthority("applicant", "admin")
                .requestMatchers(HttpMethod.GET, "/admit-card/{applicantId}")
                .hasAnyAuthority("applicant", "admin")

                .requestMatchers("/applicants", "/applications", "/applications/all", "/applications/update-status", "/applications/final-candidate", "/evaluator", "/job_circular/**", "/status", "/status/{status}")
                .hasAuthority("admin")


                .requestMatchers("/applicants/{id}", "/applications/pending/{id}", "/approval/pending/{approvalId}")
                .hasAuthority("applicant")

                .requestMatchers("/applicants", "/applications/applicant/{id}", "/notices")
                .hasAnyAuthority("admin", "applicant")

                .requestMatchers("/written_mark","/answerSheet/{evaluatorId}")
                .hasAuthority("evaluator")


                .requestMatchers("/notices/global", "/notices/applicant/{userId}")
                .hasAnyAuthority("admin", "applicant", "evaluator")
                .anyRequest()
                .authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);


        return http.build();

    }
}
