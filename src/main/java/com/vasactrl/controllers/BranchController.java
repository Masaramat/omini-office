package com.vasactrl.controllers;

import com.vasactrl.dtos.admin.BranchDto;
import com.vasactrl.models.Branch;
import com.vasactrl.services.interfaces.BranchService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController()
@RequestMapping("/api/v1/admin/branch")
@AllArgsConstructor
public class BranchController {
    private final BranchService service;

    @PostMapping()
    public Mono<BranchDto> save(@RequestBody @Valid Mono<BranchDto> branch){
        return service.save(branch);
    }

    @GetMapping()
    public Flux<BranchDto> getAll(){
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Mono<BranchDto> findById(@PathVariable Long id){
        return service.findById(id);
    }

    @PutMapping("/{id}")
    public Mono<BranchDto> update(@PathVariable Long id, @RequestBody @Valid Mono<BranchDto> branch){
        return service.update(branch, id);
    }
}
