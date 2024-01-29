package com.vasactrl.services.implementations;

import com.vasactrl.dtos.admin.AccountDto;
import com.vasactrl.models.Account;
import com.vasactrl.repositories.AccountRepository;
import com.vasactrl.services.interfaces.AccountService;
import com.vasactrl.utilities.AccountModelUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.security.auth.login.AccountNotFoundException;

@Service
@Transactional
@AllArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepository repository;
    @Override
    public Mono<AccountDto> save(Mono<AccountDto> accountDtoMono) {

        return accountDtoMono
                .map(AccountModelUtil::toModel)
                .flatMap(repository::save)
                .map(AccountModelUtil::toDto);
    }

    @Override
    public Flux<AccountDto> all() {

        return repository.findAll()
                .map(AccountModelUtil::toDto);
    }

    @Override
    public Mono<AccountDto> findById(Long id) {
        return repository.findById(id)
                .map(AccountModelUtil::toDto)
                .switchIfEmpty(Mono.error(new AccountNotFoundException("Account not found")));
    }

    @Override
    public Mono<AccountDto> update(Mono<AccountDto> accountDtoMono, Long id) {
        return repository.findById(id)
                .flatMap(a-> accountDtoMono
                        .map(AccountModelUtil::toModel)
                        .doOnNext(account -> account.setId(id)))
                .flatMap(this.repository::save)
                .map(AccountModelUtil::toDto)
                .switchIfEmpty(Mono.error(new AccountNotFoundException("Account not found")));

    }
}
