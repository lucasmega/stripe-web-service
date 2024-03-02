package com.cutconnect.repositories;

import com.cutconnect.domains.Schedule;
import com.cutconnect.domains.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends  JpaRepository<User, String> {
}
