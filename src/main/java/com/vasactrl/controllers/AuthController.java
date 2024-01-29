package com.vasactrl.controllers;

import com.vasactrl.dtos.AuthenticationRequest;
import com.vasactrl.dtos.AuthenticationResponse;
import com.vasactrl.dtos.RegisterRequest;
import com.vasactrl.models.User;
import com.vasactrl.services.interfaces.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = "api/v1/admin/user")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationService service;
    @PostMapping
    public Mono<User> register(@Valid @RequestBody RegisterRequest registerRequest){
        return service.create(registerRequest);
    }

    @GetMapping()
    public Flux<User> getAll(){
        return service.all();
    }

    @PostMapping("/login")
    public Mono <ResponseEntity<AuthenticationResponse<String>>> login(@Valid @RequestBody AuthenticationRequest request){return service.authenticate(request);


    }


}
