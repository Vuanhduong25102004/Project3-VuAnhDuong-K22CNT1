package com.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import java.time.LocalDateTime;

@Entity
@Table(name = "Vad_feedback")
public class VadFeedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Nếu có entity cho người dùng, bạn có thể dùng quan hệ ManyToOne
    // Ví dụ:
    // @ManyToOne
    // @JoinColumn(name = "Vaduser_id", nullable = false)
    // private VadUsers user;
    // Hiện tại lưu trực tiếp dưới dạng int:
    
    
    @ManyToOne
    @JoinColumn(name = "Vaduser_id", nullable = false)
    private VadUser userId;

    @ManyToOne
    @JoinColumn(name = "Vaddoctor_id", nullable = false)
    private VadDoctors doctor;

    @Column(name = "Vadrating", nullable = false)
    @Min(1)
    @Max(5)
    private int rating;

    @Column(name = "Vadcomment", columnDefinition = "TEXT")
    private String comment;

    @Column(name = "Vadcreated_at", nullable = false)
    private LocalDateTime createdAt;

    // Constructor không tham số
    public VadFeedback() {
    }

    // Constructor có tham số (không bao gồm id vì được sinh tự động)
    public VadFeedback(VadUser userId, VadDoctors doctor, int rating, String comment, LocalDateTime createdAt) {
        this.userId = userId;
        this.doctor = doctor;
        this.rating = rating;
        this.comment = comment;
        this.createdAt = createdAt;
    }

    // Getters và Setters
    public Long getId() {
        return id;
    }

    public VadUser getUserId() {
        return userId;
    }

    public void setUserId(VadUser userId) {
        this.userId = userId;
    }

    public VadDoctors getDoctorId() {
        return doctor;
    }

    public void setDoctorId(VadDoctors doctor) {
        this.doctor = doctor;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
