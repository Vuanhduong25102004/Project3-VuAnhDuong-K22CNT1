package com.controlleradmin;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.model.VadUser;
import com.repository.VadUserRepository;

@Controller
public class VadAdminUsersController {
	@Autowired
	private VadUserRepository userRepository;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@GetMapping("admin/users/users")
	public String adminUsers(Model model) {
		model.addAttribute("title", "Quản lý người dùng");

		List<VadUser> user = userRepository.findAll();
		model.addAttribute("users", user);

		return "admin/users/users";
	}

	// UPDATE: Hiển thị form chỉnh sửa người dùng
	@GetMapping("admin/users/users/edit/{id}")
	public String showEditUserForm(@PathVariable("id") Long id, Model model) {
		Optional<VadUser> userOpt = userRepository.findById(id);
		if (userOpt.isPresent()) {
			model.addAttribute("user", userOpt.get());
			return "admin/users/edit-user"; // Trang form chỉnh sửa
		}
		return "redirect:/admin/users";
	}

	// UPDATE: Xử lý form chỉnh người dùng
	@PostMapping("admin/users/users/edit/{id}")
	public String updateUser(@PathVariable("id") Long id, @ModelAttribute("user") VadUser user) {
		Optional<VadUser> existingUserOpt = userRepository.findById(id);
		if (existingUserOpt.isPresent()) {
			VadUser existingUser = existingUserOpt.get();
			// Cập nhật các trường thông tin khác
			existingUser.setFullname(user.getFullname());
			existingUser.setEmail(user.getEmail());
			existingUser.setPhone(user.getPhone());

			// Nếu người dùng nhập mật khẩu mới (không rỗng), thì mã hóa mật khẩu đó
			if (user.getPassword() != null && !user.getPassword().trim().isEmpty()) {
				String encodedPassword = passwordEncoder.encode(user.getPassword());
				existingUser.setPassword(encodedPassword);
			}
			// Cập nhật quyền nếu có thay đổi
			existingUser.setVadrole(user.getVadrole());

			userRepository.save(existingUser);
		}
		return "redirect:/admin/users/users";
	}

	// DELETE: Xóa người dùng
	@GetMapping("admin/users/users/delete/{id}")
	public String deleteUsers(@PathVariable("id") Long id) {
		userRepository.deleteById(id);
		return "redirect:/admin/users/users";
	}
}
