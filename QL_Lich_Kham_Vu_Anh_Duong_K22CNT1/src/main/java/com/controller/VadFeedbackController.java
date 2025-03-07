package com.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.model.VadDoctors;
import com.model.VadFeedback;
import com.repository.VadDoctorsRepository;
import com.repository.VadFeedbackRepository;

@Controller
public class VadFeedbackController {

    @Autowired
    private VadFeedbackRepository feedbackRepository;
    
    @Autowired
    private VadDoctorsRepository doctorsRepository;

    @GetMapping("/api/feedback")
    public String listFeedback(Model model) {
        List<VadFeedback> feedbackList = feedbackRepository.findAll();
        model.addAttribute("feedbackList", feedbackList);
        return "feedback"; // Tên view tương ứng với file feedback.html
    }
    
    @PostMapping("/api/feedback")
    public String submitFeedback(@RequestParam("doctorId") long doctorId,
                                 @RequestParam("rating") int rating,
                                 @RequestParam("comment") String comment) {
        // Lấy đối tượng VadDoctors từ repository
        VadDoctors doctor = doctorsRepository.findById(doctorId)
            .orElseThrow(() -> new RuntimeException("Bác sĩ không tồn tại"));

        VadFeedback feedback = new VadFeedback();
        feedback.setDoctorId(doctor); // truyền đối tượng thay vì int
        feedback.setRating(rating);
        feedback.setComment(comment);
        feedback.setCreatedAt(LocalDateTime.now());
        feedbackRepository.save(feedback);

        return "redirect:/doctors";
    }
}