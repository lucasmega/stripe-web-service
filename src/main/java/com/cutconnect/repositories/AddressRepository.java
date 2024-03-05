package com.cutconnect.repositories;

import com.cutconnect.domains.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, String> {
}
