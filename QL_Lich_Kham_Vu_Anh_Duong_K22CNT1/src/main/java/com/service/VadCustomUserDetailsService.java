package com.service;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.model.VadUser;
import com.repository.VadUserRepository;

@Service
public class VadCustomUserDetailsService implements UserDetailsService {
	
	@Autowired
    private VadUserRepository vadUserRepository;
    
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    	VadUser user = vadUserRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("Không tìm thấy người dùng với email: " + email);
        }
        // Sử dụng biến user sau khi đã khai báo
        String role = "ROLE_" + user.getVadrole().name().toUpperCase();
        return new org.springframework.security.core.userdetails.User(
            user.getEmail(),
            user.getPassword(),
            Collections.singleton(new SimpleGrantedAuthority(role))
        );
    }

}
