package com.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.model.VadDoctors;
import com.repository.VadDoctorsRepository;

@Controller
public class HomeController {

    @Autowired
    private VadDoctorsRepository doctorsRepository;
    
    @GetMapping("/")
    public String home(Model model) {
        List<VadDoctors> doctors = doctorsRepository.findAll();
        model.addAttribute("doctors", doctors);
        return "home";
    }
}

