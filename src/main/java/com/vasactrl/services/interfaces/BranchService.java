package com.vasactrl.services.interfaces;

import com.vasactrl.dtos.admin.BranchDto;
import com.vasactrl.models.Branch;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BranchService {
    public Flux<BranchDto> getAll();

    public Mono<BranchDto> findById(final Long id);

    public Mono<BranchDto> save(Mono<BranchDto> branchDtoMono);

    public Mono<BranchDto> update(Mono<BranchDto> branchDtoMono, Long id);
}
