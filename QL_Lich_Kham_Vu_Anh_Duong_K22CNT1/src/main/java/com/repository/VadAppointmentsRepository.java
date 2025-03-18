package com.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.model.VadAppointments;
import com.model.VadUser;

public interface VadAppointmentsRepository extends JpaRepository<VadAppointments, Long> {
	@Query("SELECT a FROM VadAppointments a " +
	           "WHERE a.user = :user " +
	           "ORDER BY CASE " +
	           "           WHEN a.vadStatus = 'pending' THEN 1 " +
	           "           WHEN a.vadStatus = 'approved' THEN 2 " +
	           "           WHEN a.vadStatus = 'canceled' THEN 3 " +
	           "           WHEN a.vadStatus = 'completed' THEN 4 " +
	           "         END ASC")
	List<VadAppointments> findByUser(@Param("user") VadUser user);
}
