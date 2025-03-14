package com.repository;

import com.model.VadUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VadUserRepository extends JpaRepository<VadUser, Long> {
    VadUser findByEmail(String email);
}
