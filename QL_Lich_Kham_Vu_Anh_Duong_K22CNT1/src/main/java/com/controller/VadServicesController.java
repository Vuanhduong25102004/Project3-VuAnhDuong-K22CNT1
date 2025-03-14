package com.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.model.VadServices;
import com.repository.VadServicesRepository;

@Controller
public class VadServicesController {
	
	@Autowired
	private VadServicesRepository servicesRepository;
	
	@GetMapping("/users/services")
	public String listServices(Model model) {
		List<VadServices> services = servicesRepository.findAll();
		model.addAttribute("services", services);
		
		return "users/services";
	}
	

}
