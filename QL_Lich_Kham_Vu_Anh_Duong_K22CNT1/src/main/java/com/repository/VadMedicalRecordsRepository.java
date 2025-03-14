package com.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.model.VadMedicalRecords;
import com.model.VadUser;

public interface VadMedicalRecordsRepository extends JpaRepository<VadMedicalRecords, Long> {
	List<VadMedicalRecords> findByUser(VadUser user);

}
