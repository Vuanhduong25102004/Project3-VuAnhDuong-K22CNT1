package com.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.model.VadFeedback;

public interface VadFeedbackRepository extends JpaRepository<VadFeedback, Long> {
	
}