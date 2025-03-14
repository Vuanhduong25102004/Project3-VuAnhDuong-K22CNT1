package com.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.model.VadDoctors;
import com.repository.VadDoctorsRepository;

@Controller
public class VadDoctorsController {
	
	@Autowired
	private VadDoctorsRepository doctorsRepository;
	
	@GetMapping("/users/doctors")
	public String listDoctors(Model model) {
		List<VadDoctors> doctors = doctorsRepository.findAll();
		
		model.addAttribute("doctors", doctors);
        return "users/doctors"; 
	}

}
