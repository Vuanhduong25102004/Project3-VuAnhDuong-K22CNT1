package com.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    public List<VadUser> getAllUsers() {
        List<VadUser> users = userRepository.findAll();
        System.out.println("Fetched users: " + users);
        return users;
    }
    
    public VadUser getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    
    public VadUser saveUser(VadUser user) {
        return userRepository.save(user);
    }
    
    public VadUser findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    
 // Phương thức cập nhật thông tin người dùng
    public VadUser updateUser(VadUser user) {
        return userRepository.save(user);
    }
    
    public VadUser registerNewUser(VadUser user) throws Exception {
        // Kiểm tra xem email đã tồn tại hay chưa
        if (userRepository.findByEmail(user.getEmail()) != null) {
            throw new Exception("Email đã được sử dụng!");
        }
        // Mã hóa mật khẩu
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        // Nếu cần, set role mặc định là patient
        user.setVadrole(VadUser.vadRole.patient);
        return userRepository.save(user);
    }
}
