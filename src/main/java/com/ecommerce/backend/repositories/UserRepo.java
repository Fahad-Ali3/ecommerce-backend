package com.ecommerce.backend.repositories;

import com.ecommerce.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User,Long> {
    Optional<User> findByUsername(String username);
}
