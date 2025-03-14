package com.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.model.VadDoctors;

public interface VadDoctorsRepository extends JpaRepository<VadDoctors, Long> {
	// List<VadDoctors> getAll();
}
