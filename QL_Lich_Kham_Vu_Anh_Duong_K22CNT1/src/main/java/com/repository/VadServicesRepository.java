package com.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.model.VadServices;

public interface VadServicesRepository extends JpaRepository<VadServices, Long> {

	// List<VadServices> findAll();
}
