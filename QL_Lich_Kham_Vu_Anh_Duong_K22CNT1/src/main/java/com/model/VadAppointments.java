package com.model;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.*;

@Entity
@Table(name = "Vad_appointments")
public class VadAppointments {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
    @JoinColumn(name = "Vaduser_id", nullable = false)
    private VadUser user;

    @ManyToOne
    @JoinColumn(name = "Vaddoctor_id", nullable = false)
    private VadDoctors doctor;
    
    @Column(name = "Vaddate", nullable = false)
    private LocalDate vadDate;
    
    @Column(name = "Vadtime", nullable = false)
    private LocalTime vadTime;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "Vadstatus")
    private vadStatus vadStatus;
    
    public enum vadStatus{
    	pending, approved, canceled , completed
    }
    
    public VadAppointments() {
    }
    
    public VadAppointments(Long id, VadUser user, VadDoctors doctor, LocalDate vadDate, LocalTime vadTime, vadStatus vadStatus) {
        this.id = id;
        this.user = user;
        this.doctor = doctor;
        this.vadDate = vadDate;
        this.vadTime = vadTime;
        this.vadStatus = vadStatus;
    }
 // Getters và Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public VadUser getUser() {
        return user;
    }

    public void setUser(VadUser user) {
        this.user = user;
    }

    public VadDoctors getDoctor() {
        return doctor;
    }

    public void setDoctor(VadDoctors doctor) {
        this.doctor = doctor;
    }

    public LocalDate getVadDate() {
        return vadDate;
    }

    public void setVadDate(LocalDate vadDate) {
        this.vadDate = vadDate;
    }

    public LocalTime getVadTime() {
        return vadTime;
    }

    public void setVadTime(LocalTime vadTime) {
        this.vadTime = vadTime;
    }

    public vadStatus getVadStatus() {
        return vadStatus;
    }

    public void setVadStatus(vadStatus vadStatus) {
        this.vadStatus = vadStatus;
    }
}
