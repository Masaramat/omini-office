package com.vasactrl.configurations;

import com.vasactrl.configurations.components.AuthConverter;
import com.vasactrl.configurations.components.AuthManager;
import com.vasactrl.repositories.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import reactor.core.publisher.Mono;

@EnableWebFluxSecurity
@Configuration
public class SecurityConfig {

    @Bean
    public ReactiveUserDetailsService userDetailsService(UserRepository userRepository, PasswordEncoder encoder){
        return email -> userRepository.findByEmail(email)
                .switchIfEmpty(
                Mono.error(new UsernameNotFoundException("User not found"))
        );

    }
    @Bean
    public SecurityWebFilterChain securityFilterChain(ServerHttpSecurity http, AuthConverter authConverter, AuthManager jwtAuthManager){
        AuthenticationWebFilter jwtFilter = new AuthenticationWebFilter((ReactiveAuthenticationManager) jwtAuthManager);
        jwtFilter.setServerAuthenticationConverter(authConverter);
        return http
                .authorizeExchange(auth->{
                    auth.pathMatchers("/api/v1/admin/user/login").permitAll();
                    auth.anyExchange().permitAll();
                })
                .addFilterAt(jwtFilter, SecurityWebFiltersOrder.AUTHENTICATION)
                .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
                .formLogin(ServerHttpSecurity.FormLoginSpec::disable)
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .build();

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}
