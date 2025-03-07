package com.model;

import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "Vad_doctors")
public class VadDoctors {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "Vadfullname")
    private String fullname;
    
    @Column(name = "Vadspecialty")
    private String specialty;
    
    @Column(name = "Vadphone")
    private String phone;
    
    @Column(name = "Vademail")
    private String email;
    
    @Column(name = "Vadexperience")
    private String experience;
    
    @Column(name = "Vadphoto")
    private String photo;
    
    @OneToMany(mappedBy = "doctor", fetch = FetchType.LAZY)
    private List<VadFeedback> feedbackList;
    // Constructor không tham số (no-args constructor)
    public VadDoctors() {
    }
    
    // Constructor có tham số (không bao gồm id vì id được sinh tự động)
    public VadDoctors(String fullname, String specialty, String phone, String email, String experience, String photo) {
        this.fullname = fullname;
        this.specialty = specialty;
        this.phone = phone;
        this.email = email;
        this.experience = experience;
        this.photo = photo;
    }
    public List<VadFeedback> getFeedbackList() {
        return feedbackList;
    }

    public void setFeedbackList(List<VadFeedback> feedbackList) {
        this.feedbackList = feedbackList;
    }
    // Getters và Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getFullname() {
        return fullname;
    }
    
    public void setFullname(String fullname) {
        this.fullname = fullname;
    }
    
    public String getSpecialty() {
        return specialty;
    }
    
    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }
    
    public String getPhone() {
        return phone;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getExperience() {
        return experience;
    }
    
    public void setExperience(String experience) {
        this.experience = experience;
    }
    
    public String getPhoto() {
        return photo;
    }
    
    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
