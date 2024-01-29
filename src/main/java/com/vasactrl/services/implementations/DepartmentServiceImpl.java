package com.vasactrl.services.implementations;

import com.vasactrl.dtos.admin.DepartmentDto;
import com.vasactrl.exceptions.DepartmentNotFoundException;
import com.vasactrl.models.Department;
import com.vasactrl.repositories.DepartmentRepository;
import com.vasactrl.services.interfaces.DepartmentService;
import com.vasactrl.utilities.DepartmentModelUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
@Transactional
@Service
@AllArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {
    private final DepartmentRepository repository;
    @Override
    public Mono<DepartmentDto> create(Mono<DepartmentDto> departmentDtoMono) {

        return departmentDtoMono
                .map(DepartmentModelUtil::toModel)
                .flatMap(this.repository::save)
                .map(DepartmentModelUtil::toDto);
    }

    @Override
    public Flux<DepartmentDto> all() {
        return repository.findAll()
                .map(DepartmentModelUtil::toDto)
                .switchIfEmpty(
                Mono.error(new DepartmentNotFoundException("Department not found"))
        );
    }

    @Override
    public Mono<DepartmentDto> get(Long id) {
        return repository.findById(id)
                .map(DepartmentModelUtil::toDto)
                .switchIfEmpty(
                Mono.error(new DepartmentNotFoundException("Department not found with ID " + id))
        );
    }

    @Override
    public Mono<DepartmentDto> update(Mono<DepartmentDto> departmentDtoMono, Long id) {
        return repository.findById(id)
                .flatMap(dept -> departmentDtoMono
                        .map(DepartmentModelUtil::toModel)
                        .doOnNext(department -> department.setId(id)))
                .flatMap(this.repository::save)
                .map(DepartmentModelUtil::toDto)
                .switchIfEmpty(Mono.error(new DepartmentNotFoundException("Department not found")));
    }
}
