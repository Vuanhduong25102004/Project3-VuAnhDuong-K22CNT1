package com.controlleradmin;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.model.VadUser;
import com.repository.VadUserRepository;

@Controller

public class VadAdminController {

	@Autowired
    private VadUserRepository vadUserRepository; 
	
	@GetMapping("admin/dashboard")
	public String adminHome(Model model, Principal principal) {
		model.addAttribute("title", "Admin Dashboard");
		
		String email = principal.getName();
		// Tìm user theo email
        VadUser user = vadUserRepository.findByEmail(email);
        model.addAttribute("currentUser", user);
        
		return "admin/dashboard";
	}
	
	@GetMapping("/home")
	public String home(Model model, Principal principal) {
	    String email = principal.getName();
	    // Tìm user theo email
	    VadUser user = vadUserRepository.findByEmail(email);
	    model.addAttribute("currentUser", user);
	    return "header"; // Trả về home.html
	}


}