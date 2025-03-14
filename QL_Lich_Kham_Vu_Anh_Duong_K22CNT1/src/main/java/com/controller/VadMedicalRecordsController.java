package com.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.model.VadMedicalRecords;
import com.model.VadUser;
import com.model.VadAppointments;
import com.repository.VadAppointmentsRepository;
import com.repository.VadMedicalRecordsRepository;
import com.repository.VadUserRepository;
import com.service.VadUserService;

@Controller
public class VadMedicalRecordsController {

    @Autowired
    private VadMedicalRecordsRepository medicalRecordsRepository;

    @Autowired
    private VadAppointmentsRepository appointmentsRepository;
    
    @Autowired
    private VadUserRepository userRepository;
    
    @Autowired
    private VadUserService vadUserService;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/users/medicalrecord")
    public String getUserMedicalRecords(Model model, Principal principal) {
        // Kiểm tra người dùng đã đăng nhập chưa
        if (principal == null) {
            throw new RuntimeException("Người dùng chưa đăng nhập");
        }
        // Lấy thông tin người dùng dựa trên email/username
        VadUser user = userRepository.findByEmail(principal.getName());
        if (user == null) {
            throw new RuntimeException("Không tìm thấy người dùng trong hệ thống");
        }
        // Lấy danh sách hồ sơ bệnh của user
        List<VadMedicalRecords> medicalRecords = medicalRecordsRepository.findByUser(user);
        model.addAttribute("medicalRecords", medicalRecords);
        
        model.addAttribute("user", user);
        
        List<VadAppointments> appointments = appointmentsRepository.findByUser(user);
        model.addAttribute("appointments", appointments);
        		
        return "users/user-medicalrecord"; // Tên file Thymeleaf hiển thị hồ sơ bệnh
    }
    
    // Xử lý submit form sửa thông tin (nếu dùng POST)
    
    @PostMapping("/users/edit")
    public String updateUserInfo(@ModelAttribute("user") VadUser formUser,
                                 @RequestParam("currentPassword") String currentPassword,
                                 Principal principal,
                                 Model model) {
        // Lấy thông tin người dùng hiện có
        VadUser existingUser = vadUserService.findByEmail(principal.getName());
        
        // So sánh mật khẩu nhập từ form với mật khẩu hiện có (đã mã hóa)
        if (!passwordEncoder.matches(currentPassword, existingUser.getPassword())) {
            // Nếu mật khẩu không đúng, thêm thông báo lỗi và quay lại trang thông tin
            model.addAttribute("error", "Mật khẩu hiện tại không đúng!");
            model.addAttribute("user", existingUser);
            return "users/user-medicalrecord";
        }
        // Cập nhật các trường cho phép chỉnh sửa
        existingUser.setFullname(formUser.getFullname());
        existingUser.setPhone(formUser.getPhone());
        // Cập nhật các trường khác nếu cần...

        // Lưu lại thông tin đã cập nhật
        vadUserService.updateUser(existingUser);
        return "redirect:/users/medicalrecord";
    }
}
