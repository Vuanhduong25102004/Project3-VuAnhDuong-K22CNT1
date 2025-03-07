package com.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.model.VadUser;
import com.repository.VadUserRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Service
public class VadUserService {
	@Autowired
	private VadUserRepository userRepository;
	
	@PersistenceContext
    private EntityManager entityManager;
	
	public List<VadUser> getAllUsers() {
	    List<VadUser> users = userRepository.findAll();
	    System.out.println("Fetched users: " + users);
	    return users;
	}
	
	public VadUser getUserByEmail(String email){
		return userRepository.findByEmail(email);
	}
	
	public VadUser saveUser(VadUser user) {
		return userRepository.save(user);
	}
    
}
