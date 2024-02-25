package com.cutconnect.repositories;

import com.cutconnect.domains.Branch;
import com.cutconnect.domains.Professional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BranchRepository extends JpaRepository<Branch, Long> {
}
