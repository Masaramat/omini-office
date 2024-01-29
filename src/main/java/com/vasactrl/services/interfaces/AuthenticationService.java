package com.vasactrl.services.interfaces;

import com.vasactrl.dtos.AuthenticationRequest;
import com.vasactrl.dtos.AuthenticationResponse;
import com.vasactrl.dtos.RegisterRequest;
import com.vasactrl.dtos.RequestResponse;
import com.vasactrl.models.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AuthenticationService {
    public Mono<User> create(RegisterRequest registerRequest);

    public Flux<User> all();

    public Mono <ResponseEntity<AuthenticationResponse<String>>> authenticate(AuthenticationRequest authenticationRequest);
}
