package com.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import com.model.VadUser;
import com.service.VadUserService;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/users")
public class VadUserController {
	@Autowired
	private VadUserService userService;
	
	@GetMapping
	public List<VadUser> getAllUsers(){
		return userService.getAllUsers();
	}
	
	@GetMapping("/{email}")
	public VadUser getUserByEmail(@PathVariable String email){
		return userService.getUserByEmail(email);
	}
	
	@PostMapping
	public VadUser createUser(@RequestBody VadUser user) {
		return userService.saveUser(user);
	}
}
