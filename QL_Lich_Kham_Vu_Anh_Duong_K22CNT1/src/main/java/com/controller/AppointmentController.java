package com.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.model.VadDoctors;
import com.repository.VadDoctorsRepository;



@Controller
public class AppointmentController {
    @Autowired
    private VadDoctorsRepository doctorsRepository; // Service lấy danh sách bác sĩ từ database

    // Đổi mapping từ "/" thành "/appointments"
    @GetMapping("/appointments")
    public String appointmenDoctors(Model model) {
        List<VadDoctors> doctors = doctorsRepository.findAll();
        model.addAttribute("doctors", doctors);
        return "appointment"; // Tên file Thymeleaf khác, ví dụ appointment.html
    }
}

