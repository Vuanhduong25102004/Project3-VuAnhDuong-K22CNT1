package com.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.model.VadServices;
import com.model.VadUser;

public interface VadServicesRepository extends JpaRepository<VadServices, Long> {
}
