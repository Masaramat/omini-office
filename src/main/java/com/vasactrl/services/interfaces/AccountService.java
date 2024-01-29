package com.vasactrl.services.interfaces;

import com.vasactrl.dtos.admin.AccountDto;
import com.vasactrl.models.Account;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AccountService {
    public Mono<AccountDto> save(Mono<AccountDto> accountDtoMono);

    public Flux<AccountDto> all();

    public Mono<AccountDto> findById(Long id);

    public Mono<AccountDto> update(Mono<AccountDto> account, Long id);
}
