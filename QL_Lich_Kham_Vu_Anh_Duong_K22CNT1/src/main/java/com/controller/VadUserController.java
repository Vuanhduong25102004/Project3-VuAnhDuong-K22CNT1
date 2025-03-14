package com.controller;

import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.model.VadUser;
import com.service.VadUserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/users")
public class VadUserController {
    @Autowired
    private VadUserService vadUserService;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @GetMapping("/profile")
    public String userInfo(Model model, Principal principal) {
        // principal.getName() chứa email hoặc username dùng để đăng nhập
        String email = principal.getName();
        // Lấy thông tin người dùng từ DB
        VadUser user = vadUserService.findByEmail(email);
        // Thêm đối tượng user vào model để Thymeleaf có thể truy cập
        model.addAttribute("user", user);
        // Trả về tên template (user-info.html)
        return "users/user-info";
    }
    
 // Hiển thị trang sửa thông tin
    @GetMapping("/edit")
    public String editUserInfo(Model model, Principal principal) {
        String email = principal.getName();
        VadUser user = vadUserService.findByEmail(email);
        model.addAttribute("user", user);
        return "users/edit-user-info"; // Tạo file edit-user-info.html trong thư mục templates/users/
    }
    

    
}
