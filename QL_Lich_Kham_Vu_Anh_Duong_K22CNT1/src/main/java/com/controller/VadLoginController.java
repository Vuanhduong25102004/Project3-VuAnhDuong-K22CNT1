package com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.model.VadUser;
import com.service.VadUserService;

@Controller
public class VadLoginController {
	
	@Autowired
    private VadUserService vadUserService;
	
	@GetMapping({"/login", "/register"})
    public String Login(Model model) {
        model.addAttribute("vadUser", new VadUser());
        return "login-register";
    }

    // Xử lý dữ liệu đăng ký
    @PostMapping("/register")
    public String processRegistration(@ModelAttribute("vadUser") VadUser user, Model model) {
        try {
            vadUserService.registerNewUser(user);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "login-register";
        }
        return "redirect:/login";
    }
}
