package com.vasactrl.repositories;

import com.vasactrl.models.User;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.Optional;
@Repository
public interface UserRepository extends ReactiveCrudRepository<User, Long> {
    public Mono<UserDetails> findByEmail(String email);

}
