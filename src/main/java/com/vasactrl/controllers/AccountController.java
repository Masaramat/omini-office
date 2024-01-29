package com.vasactrl.controllers;

import com.vasactrl.dtos.admin.AccountDto;
import com.vasactrl.models.Account;
import com.vasactrl.services.interfaces.AccountService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api/v1/admin/account")
@AllArgsConstructor
public class AccountController {
    private final AccountService service;

    @PostMapping()
    public Mono<AccountDto> save(@Valid @RequestBody Mono<AccountDto> account) {

        return service.save(account);
    }

    @GetMapping()
    public Flux<AccountDto> all() {
        return service.all();
    }

    @GetMapping("/{id}")
    public Mono<AccountDto> findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PutMapping("/{id}")
    public Mono<AccountDto> update(@PathVariable Long id, @RequestBody @Valid Mono<AccountDto> account) {
        return service.update(account, id);
    }

}
