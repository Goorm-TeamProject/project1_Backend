package com.eouil.bank.bankapi.config;

import com.eouil.bank.bankapi.repositories.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter(UserRepository userRepository) {
        return new JwtAuthenticationFilter(userRepository);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, JwtAuthenticationFilter jwtAuthenticationFilter) throws Exception {
        http
                .cors(cors -> cors.configurationSource(request -> {
                    CorsConfiguration config = new CorsConfiguration();
                    config.setAllowedOrigins(List.of(
                            "https://eouil.com",
                            "https://api.eouil.com",
                            "http://localhost:5173"
                    ));
                    config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
                    config.setAllowedHeaders(List.of("Authorization", "Content-Type"));
                    config.setAllowCredentials(true);
                    return config;
                }))
                .csrf(csrf -> csrf.disable())
                // H2 콘솔이 <frame> 안에서 동작하도록 허용
                .headers(headers -> headers.frameOptions(frame -> frame.disable()))
                .formLogin(form -> form.disable())
                .logout(logout -> logout.disable())
                .httpBasic(basic -> basic.disable())
                .authorizeHttpRequests(auth -> auth
                        // CORS 프리플라이트
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        // 인증 없는 접근 허용
                        .requestMatchers(
                                "/api/join",
                                "/api/login",
                                "/api/refresh",
                                "/api/logout",
                                "/api/accounts/**",
                                "/api/transactions/**",
                                "/api/health",
                                "/h2-console/**"         // 여기에 H2 콘솔 경로 추가
                        ).permitAll()
                        // 그 외는 인증 필요
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
