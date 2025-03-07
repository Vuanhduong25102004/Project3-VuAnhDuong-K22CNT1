package com.controller;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.model.VadAppointments;
import com.model.VadAppointments.vadStatus;
import com.model.VadDoctors;
import com.model.VadUser;
import com.repository.VadAppointmentsRepository;
import com.repository.VadDoctorsRepository;
import com.repository.VadUserRepository;



@Controller
public class AppointmentController {
    @Autowired
    private VadDoctorsRepository doctorsRepository; // Service lấy danh sách bác sĩ từ database
    
    @Autowired
    private VadAppointmentsRepository appointmentsRepository;
    
    @Autowired 
    private VadUserRepository userRepository;
   
    // Đổi mapping từ "/" thành "/appointments"
    @GetMapping("/appointments")
    public String appointmenDoctors(Model model) {
        List<VadDoctors> doctors = doctorsRepository.findAll();
        model.addAttribute("doctors", doctors);
        return "appointment"; // Tên file Thymeleaf khác, ví dụ appointment.html
    }
    
    @PostMapping("/appointments")
    public String bookAppointment(
        @RequestParam("Vaddoctor_id") Long doctorId,
        @RequestParam("Vaddate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
        @RequestParam("Vadtime") @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime time,
        Principal principal // Lấy user đăng nhập
    ) {
        // Kiểm tra người dùng đã đăng nhập chưa
        if (principal == null) {
            throw new RuntimeException("Người dùng chưa đăng nhập");
        }

        // Lấy user từ database dựa vào email hoặc username
        VadUser user = userRepository.findByEmail(principal.getName());
        if (user == null) {
            throw new RuntimeException("Không tìm thấy người dùng trong hệ thống");
        }

        // Lấy thông tin bác sĩ từ database
        VadDoctors doctor = doctorsRepository.findById(doctorId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy bác sĩ"));

        // Tạo mới đối tượng VadAppointments
        VadAppointments appointment = new VadAppointments();
        appointment.setUser(user); // Gán user vào appointment
        appointment.setDoctor(doctor);
        appointment.setVadDate(date);
        appointment.setVadTime(time);
        appointment.setVadStatus(VadAppointments.vadStatus.pending);

        // Lưu vào database
        appointmentsRepository.save(appointment);

        return "redirect:/?success=true";
    }
    
}

