package com.vasactrl.services.implementations;

import com.vasactrl.dtos.admin.BranchDto;
import com.vasactrl.exceptions.BranchNotFoundException;
import com.vasactrl.models.Branch;
import com.vasactrl.repositories.BranchRepository;
import com.vasactrl.services.interfaces.BranchService;
import com.vasactrl.utilities.BranchEntityUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
@Service
@AllArgsConstructor
@Transactional
public class BranchServiceImpl implements BranchService {

    private final BranchRepository repository;
    @Override
    public Flux<BranchDto> getAll() {

        return repository.findAll()
                .map(BranchEntityUtil::toDto);
    }

    @Override
    public Mono<BranchDto> findById(final Long id) {
        return repository.findById(id)
                .map(BranchEntityUtil::toDto)
                .switchIfEmpty(
                Mono.error(new BranchNotFoundException("Branch not found"))
        );
    }

    @Override
    public Mono<BranchDto> save(Mono<BranchDto> branchDtoMono) {

        return branchDtoMono
                .map(BranchEntityUtil::toModel)
                .flatMap(repository::save)
                .map(BranchEntityUtil::toDto);
    }

    @Override
    public Mono<BranchDto> update(Mono<BranchDto> branchDtoMono, Long id) {
        return repository.findById(id)
                .flatMap(b-> branchDtoMono
                        .map(BranchEntityUtil::toModel)
                        .doOnNext(branch -> branch.setId(id)))
                .flatMap(this.repository::save)
                .map(BranchEntityUtil::toDto)
                .switchIfEmpty(
                        Mono.error(new BranchNotFoundException("Branch not found")
                ));

    }
}
