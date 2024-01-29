package com.vasactrl.repositories;

import com.vasactrl.models.Branch;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BranchRepository extends ReactiveCrudRepository<Branch, Long> {
}
