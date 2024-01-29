package com.vasactrl.configurations.components;

import com.vasactrl.configurations.BearerToken;
import com.vasactrl.services.implementations.JwtService;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class AuthManager implements ReactiveAuthenticationManager {
    final JwtService jwtService;
    final ReactiveUserDetailsService userDetailsService;

    public AuthManager(JwtService jwtService, ReactiveUserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }


    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        return Mono.justOrEmpty(authentication)

                .cast(BearerToken.class)
                .flatMap(auth -> {
                    String userName = jwtService.getUserName(auth.getCredentials());
                    return userDetailsService.findByUsername(userName)

                            .flatMap(userDetails -> {
                                if (jwtService.validate(userDetails, auth.getCredentials())) {
                                    return Mono.justOrEmpty(new UsernamePasswordAuthenticationToken(
                                            userDetails, auth.getCredentials(), userDetails.getAuthorities()));
                                } else {
                                    return Mono.error(new IllegalArgumentException("Invalid/Expired token"));
                                }
                            })
                            .switchIfEmpty(Mono.error(new IllegalArgumentException("User not found in authorization")));
                });
    }


}
