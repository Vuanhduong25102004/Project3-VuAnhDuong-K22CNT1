package com.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.model.VadAppointments;
import com.model.VadUser;

public interface VadAppointmentsRepository extends JpaRepository<VadAppointments, Long> {
	List<VadAppointments> findByUser(VadUser user);
}
