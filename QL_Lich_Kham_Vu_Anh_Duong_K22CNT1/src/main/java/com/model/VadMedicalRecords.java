package com.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(name = "Vad_medical_records")
public class VadMedicalRecords {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
    @JoinColumn(name = "Vaduser_id", nullable = false)
    private VadUser user;

    @ManyToOne
    @JoinColumn(name = "Vaddoctor_id", nullable = false)
    private VadDoctors doctor;
    
    @ManyToOne
    @JoinColumn(name = "Vadappointment_id", nullable = false)
    private VadAppointments appointment;
    
    @Column(name = "Vaddiagnosis")
    private String diagnosis;
    
    @Column(name = "Vadprescription")
    private String prescription;
    
    @Column(name = "Vadnotes")
    private String notes;
    
    @Column(name = "Vadcreated_at", nullable = false)
    private LocalDateTime createdAt;
    
    public VadMedicalRecords() {
    	
    }
    
    public VadMedicalRecords(VadUser user, VadDoctors doctor, VadAppointments appointment, String diagnosis, String prescription, String notes, LocalDateTime createdAt) {
    	this.user = user;
    	this.doctor = doctor;
    	this.appointment = appointment;
    	this.diagnosis = diagnosis;
    	this.prescription = prescription;
    	this.notes = notes;
    	this.createdAt = createdAt;
    }
    
    public Long getId() {
        return id;
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
    
    public VadAppointments getAppointment() {
        return appointment;
    }
    
    public void setAppointment(VadAppointments appointment) {
    	this.appointment = appointment;
    }
    ///
    public String getDiagnosis() {
    	return diagnosis;
    }
    
    public void setDiagnosis(String diagnosis) {
    	this.diagnosis = diagnosis;
    }
    
    public String getPrescription() {
    	return prescription;
    }
    
    public void setPrescription(String prescription) {
    	this.prescription = prescription;
    }
    
    public String getNotes() {
    	return notes;
    }
    
    public void setNotes(String notes) {
    	this.notes = notes;
    }
  
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
