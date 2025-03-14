package com.controller;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.model.VadDoctors;
import com.model.VadFeedback;
import com.model.VadUser;
import com.repository.VadDoctorsRepository;
import com.repository.VadFeedbackRepository;
import com.repository.VadUserRepository;

@Controller
public class VadFeedbackController {

	@Autowired
	private VadFeedbackRepository feedbackRepository;

	@Autowired
	private VadDoctorsRepository doctorsRepository;

	@Autowired
	private VadUserRepository vadUserRepository;

	@PostMapping("/feedback")
	public String submitFeedback(@RequestParam("doctorId") Long doctorId, @RequestParam("rating") int rating,
			@RequestParam("comment") String comment, Principal principal) {
		// Kiểm tra giá trị rating (nếu cần)
		if (rating < 1 || rating > 5) {
			// Xử lý lỗi, có thể redirect với thông báo lỗi
			return "redirect:/users/doctors?error=InvalidRating";
		}

		// Lấy thông tin bác sĩ
		Optional<VadDoctors> doctorOptional = doctorsRepository.findById(doctorId);
		if (!doctorOptional.isPresent()) {
			// Nếu không tìm thấy bác sĩ, redirect về trang danh sách
			return "redirect:/users/doctors?error=DoctorNotFound";
		}
		VadDoctors doctor = doctorOptional.get();

		// Lấy thông tin người dùng từ email trong principal
		// (Giả sử email được dùng làm username)
		String email = principal.getName();
		VadUser user = vadUserRepository.findByEmail(email);
		if (user == null) {
			// Nếu không tìm thấy người dùng, chuyển hướng hoặc xử lý lỗi
			return "redirect:/users/doctors?error=UserNotFound";
		}

		// Tạo đối tượng Feedback và gán dữ liệu
		VadFeedback feedback = new VadFeedback();
		feedback.setRating(rating);
		feedback.setComment(comment);
		feedback.setCreatedAt(LocalDateTime.now());
		feedback.setDoctorId(doctor);
		feedback.setUserId(user);

		// Lưu đánh giá vào cơ sở dữ liệu
		feedbackRepository.save(feedback);

		// Redirect về trang danh sách bác sĩ hoặc trang chi tiết bác sĩ
		return "redirect:/users/doctors";
	}
}