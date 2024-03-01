package com.cutconnect.repositories;

import com.cutconnect.domains.Branch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BranchRepository extends JpaRepository<Branch, String> {
}
