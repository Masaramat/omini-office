package com.vasactrl.controllers;

import com.vasactrl.dtos.admin.DepartmentDto;
import com.vasactrl.models.Department;
import com.vasactrl.services.interfaces.DepartmentService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/admin/department")
@AllArgsConstructor
public class DepartmentController {
    private final DepartmentService service;
    @PostMapping()
    public Mono<DepartmentDto> save(@RequestBody @Valid Mono<DepartmentDto> department){
        return service.create(department);
    }

    @GetMapping()
    public Flux<DepartmentDto> all(){
        return service.all();
    }

    @GetMapping("/{id}")
    public Mono<DepartmentDto> get(@PathVariable Long id){
        return service.get(id);
    }

    @PutMapping("/{id}")
    public Mono<DepartmentDto> edit(@RequestBody @Valid Mono<DepartmentDto> department, @PathVariable Long id){
        return service.update(department, id);

    }
}
