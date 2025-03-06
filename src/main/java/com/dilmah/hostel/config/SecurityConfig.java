package com.dilmah.hostel.config;

import io.github.bucket4j.Bucket;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.Duration;
import java.util.Arrays;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    private final ConcurrentMap<String, Bucket> buckets = new ConcurrentHashMap<>();

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtAuthenticationFilter jwtAuthenticationFilter) throws Exception {
        http.csrf(csrf -> csrf.disable())
            .cors(cors -> cors.configurationSource(corsConfigurationSource())) // Enable and customize CORS
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/auth/login").permitAll()
                .requestMatchers("/auth/register").permitAll()
                .requestMatchers("/warden/**").hasRole("WARDEN")
                .requestMatchers("/fine/**").hasAnyRole("WARDEN", "FACILITYADMIN")
                .requestMatchers("/inout/**").hasRole("HOSTELLER")
                .requestMatchers("/hosteller/**").hasRole("HOSTELLER")
                .requestMatchers("/superadmin/**").hasRole("SUPERADMIN")
                .requestMatchers("/facilityadmin/**").hasRole("FACILITYADMIN")
                .requestMatchers("/security/**").hasRole("SECURITYPERSONAL")
                .requestMatchers("/reports/**").hasAnyRole("WARDEN", "SUPERADMIN")
                .requestMatchers(HttpMethod.PUT, "/complaint/**").hasRole("HOSTELLER")
                .requestMatchers(HttpMethod.PATCH, "/complaint/**").hasRole("HOSTELLER")
                .requestMatchers(HttpMethod.POST, "/fine/**").hasAnyRole("WARDEN", "FACILITYADMIN")
                .requestMatchers(HttpMethod.DELETE, "/fine/**").hasAnyRole("WARDEN", "FACILITYADMIN")
                .requestMatchers(HttpMethod.PUT, "/fine/**").hasAnyRole("WARDEN", "FACILITYADMIN")
                .requestMatchers(HttpMethod.PATCH, "/fine/**").hasAnyRole("WARDEN", "FACILITYADMIN")
                .requestMatchers(HttpMethod.GET, "/fine/**").hasAnyRole("WARDEN", "FACILITYADMIN", "HOSTELLER")
                .requestMatchers(HttpMethod.PUT, "/foodmenu/**").denyAll()
                .requestMatchers(HttpMethod.PATCH, "/foodmenu/**").denyAll()
                .anyRequest().authenticated()
            )
            .httpBasic(withDefaults())
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
            .addFilterBefore(rateLimitingFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public OncePerRequestFilter rateLimitingFilter() {
        return new OncePerRequestFilter() {
            @Override
            protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
                    throws ServletException, IOException {

                String clientIP = request.getRemoteAddr();
                Bucket bucket = buckets.computeIfAbsent(clientIP, k -> createNewBucket());

                if (bucket.tryConsume(1)) {
                    filterChain.doFilter(request, response);
                } else {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    response.getWriter().write("Too many requests. Please try again later.");
                }
            }
        };
    }

    private Bucket createNewBucket() {
        return Bucket.builder()
                .addLimit(limit -> limit.capacity(50).refillGreedy(10, Duration.ofSeconds(1)))
                .build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*")); // Allow all origins
        configuration.setAllowedMethods(Arrays.asList("*")); // Allow all methods (GET, POST, PUT, DELETE, etc.)
        configuration.setAllowedHeaders(Arrays.asList("*")); // Allow all headers
        configuration.setAllowCredentials(true); // Allow credentials (cookies, HTTP authentication, etc.)

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // Apply this to all endpoints

        return source;
    }
}
