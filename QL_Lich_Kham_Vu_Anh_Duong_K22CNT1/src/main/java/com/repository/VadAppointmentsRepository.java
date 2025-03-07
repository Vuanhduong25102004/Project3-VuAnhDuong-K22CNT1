package com.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.model.VadAppointments;

public interface VadAppointmentsRepository extends JpaRepository<VadAppointments, Long> {

}
