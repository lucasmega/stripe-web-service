package com.cutconnect.repositories;

import com.cutconnect.domains.Professional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProfessionalRepository extends JpaRepository<Professional, String> {
    List<Professional> findProfessionalsByBarberShopId(String barberShopId);
}
