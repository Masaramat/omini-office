package com.vasactrl.services.interfaces;

import com.vasactrl.dtos.admin.DepartmentDto;
import com.vasactrl.models.Department;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface DepartmentService {
    public Mono<DepartmentDto> create(Mono<DepartmentDto> departmentDTOMono);

    public Flux<DepartmentDto> all();

    public Mono<DepartmentDto> get(Long id);

    public Mono<DepartmentDto> update(Mono<DepartmentDto> departmentDtoMono, Long id);
}
