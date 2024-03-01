package com.cutconnect.repositories;

import com.cutconnect.domains.BarberShop;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BarberShopRepository extends JpaRepository<BarberShop, String> {
    List<BarberShop> findByNameContainingIgnoreCase(String name);
}
