package com.aits.hivelink_demo.config;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
        throws Exception {

        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/**").permitAll()
                        .requestMatchers(
                                "/api/docs",
                                "/api/docs/**",
                                "/api/swagger-ui/**",
                                "/swagger-ui/**",
                                "/api-docs/**"
                        ).permitAll()
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                // Authentication / authorization errors
                .exceptionHandling(exception -> exception

                        // 401 - user is not authenticated
                        .authenticationEntryPoint(
                                (request, response, ex) -> {

                                    response.setStatus(
                                            HttpServletResponse.SC_UNAUTHORIZED
                                    );

                                    response.setContentType(
                                            MediaType.APPLICATION_JSON_VALUE
                                    );

                                    response.getWriter().write("""
                                            {
                                                "status": 401,
                                                "error": "UNAUTHORIZED",
                                                "message": "Authentication token is required.",
                                                "path": "%s"
                                            }
                                            """.formatted(
                                            request.getRequestURI()
                                    ));
                                }
                        )

                        // 403 - user is authenticated but not authorized
                        .accessDeniedHandler(
                                (request, response, ex) -> {

                                    response.setStatus(
                                            HttpServletResponse.SC_FORBIDDEN
                                    );

                                    response.setContentType(
                                            MediaType.APPLICATION_JSON_VALUE
                                    );

                                    response.getWriter().write("""
                                            {
                                                "status": 403,
                                                "error": "FORBIDDEN",
                                                "message": "You do not have permission to access this resource.",
                                                "path": "%s"
                                            }
                                            """.formatted(
                                            request.getRequestURI()
                                    ));
                                }
                        )
                );

        return http.build();
    }
}
