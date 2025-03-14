package com.controlleradmin;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.model.VadAppointments;
import com.model.VadDoctors;
import com.model.VadMedicalRecords;
import com.model.VadUser;
import com.repository.VadAppointmentsRepository;
import com.repository.VadDoctorsRepository;
import com.repository.VadMedicalRecordsRepository;
import com.repository.VadUserRepository;

@Controller
public class VadAdminMedicalRecordsController {

	@Autowired
	private VadMedicalRecordsRepository medicalRecordsRepository;

	@Autowired
	private VadUserRepository userRepository;

	@Autowired
	private VadDoctorsRepository doctorRepository;

	@Autowired
	private VadAppointmentsRepository appointmentRepository;

	@GetMapping("admin/medicalrecord/medicalrecord")
	public String adminServices(Model model) {
		model.addAttribute("title", "Quản lý hồ sơ bệnh nhân");

		List<VadMedicalRecords> medicalRecords = medicalRecordsRepository.findAll();
		model.addAttribute("medicalRecords", medicalRecords);

		return "admin/medicalrecord/medicalrecord";
	}

	// Hiển thị form tạo mới hồ sơ bệnh nhân
	@GetMapping("admin/medicalrecord/medicalrecord/create")
	public String showCreateForm(Model model) {
		VadMedicalRecords medicalRecord = new VadMedicalRecords();
		model.addAttribute("medicalRecord", medicalRecord);

		// Lấy danh sách bệnh nhân, bác sĩ và cuộc hẹn để chọn trong form
		List<VadUser> users = userRepository.findAll();
		List<VadDoctors> doctors = doctorRepository.findAll();
		List<VadAppointments> appointments = appointmentRepository.findAll();

		model.addAttribute("users", users);
		model.addAttribute("doctors", doctors);
		model.addAttribute("appointments", appointments);

		return "admin/medicalrecord/create-medicalRecord";
	}

	// Xử lý form tạo mới hồ sơ bệnh nhân
	@PostMapping("admin/medicalrecord/medicalrecord/create")
	public String createMedicalRecord(@ModelAttribute("medicalRecord") VadMedicalRecords medicalRecord) {
		// Nếu form chỉ gửi id của các thực thể liên quan, cần tải đối tượng đầy đủ từ
		// database
		if (medicalRecord.getUser() != null && medicalRecord.getUser().getId() != null) {
			VadUser user = userRepository.findById(medicalRecord.getUser().getId()).orElseThrow(
					() -> new IllegalArgumentException("Invalid user Id:" + medicalRecord.getUser().getId()));
			medicalRecord.setUser(user);
		}
		if (medicalRecord.getDoctor() != null && medicalRecord.getDoctor().getId() != null) {
			VadDoctors doctor = doctorRepository.findById(medicalRecord.getDoctor().getId()).orElseThrow(
					() -> new IllegalArgumentException("Invalid doctor Id:" + medicalRecord.getDoctor().getId()));
			medicalRecord.setDoctor(doctor);
		}
		if (medicalRecord.getAppointment() != null && medicalRecord.getAppointment().getId() != null) {
			VadAppointments appointment = appointmentRepository.findById(medicalRecord.getAppointment().getId())
					.orElseThrow(() -> new IllegalArgumentException(
							"Invalid appointment Id:" + medicalRecord.getAppointment().getId()));
			medicalRecord.setAppointment(appointment);
		}

		// Thiết lập thời gian tạo hồ sơ nếu cần (có thể được đặt tự động)
		medicalRecord.setCreatedAt(LocalDateTime.now());

		// Lưu hồ sơ mới vào database
		medicalRecordsRepository.save(medicalRecord);
		return "redirect:/admin/medicalrecord/medicalrecord";
	}

	// Mapping cho form chỉnh sửa
	@GetMapping("admin/medicalrecord/medicalrecord/edit/{id}")
	public String showEditForm(@PathVariable Long id, Model model) {
		VadMedicalRecords medicalRecord = medicalRecordsRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid medical record Id:" + id));
		model.addAttribute("medicalRecord", medicalRecord);
		return "admin/medicalrecord/edit-medicalRecord";
	}

	@PostMapping("admin/medicalrecord/medicalrecord/edit/{id}")
	public String updateMedicalRecord(@PathVariable Long id,
	        @ModelAttribute("medicalRecord") VadMedicalRecords updatedRecord) {
	    // Lấy hồ sơ hiện tại từ database
	    VadMedicalRecords existingRecord = medicalRecordsRepository.findById(id)
	            .orElseThrow(() -> new IllegalArgumentException("Invalid medical record Id:" + id));

	    // Cập nhật các trường của hồ sơ bệnh nhân
	    existingRecord.setDiagnosis(updatedRecord.getDiagnosis());
	    existingRecord.setPrescription(updatedRecord.getPrescription());
	    existingRecord.setNotes(updatedRecord.getNotes());

	    // Nếu form gửi thông tin chỉnh sửa của cuộc hẹn, cập nhật ngày và giờ của appointment
	 // Cập nhật ngày và giờ của appointment nếu có dữ liệu mới
	    if (updatedRecord.getAppointment() != null) {
	        // Nếu người dùng nhập ngày mới thì cập nhật, nếu không thì giữ nguyên ngày cũ
	        if (updatedRecord.getAppointment().getVadDate() != null) {
	            existingRecord.getAppointment().setVadDate(updatedRecord.getAppointment().getVadDate());
	        }
	        // Tương tự đối với giờ
	        if (updatedRecord.getAppointment().getVadTime() != null) {
	            existingRecord.getAppointment().setVadTime(updatedRecord.getAppointment().getVadTime());
	        }
	    }

	    // Lưu lại hồ sơ mới (với appointment đã được cập nhật)
	    medicalRecordsRepository.save(existingRecord);
	    return "redirect:/admin/medicalrecord/medicalrecord";
	}


	// xóa bác sĩ
	@GetMapping("admin/medicalrecord/medicalrecord/delete/{id}")
	public String deleteMedicalRecord(@PathVariable("id") Long id) {
		medicalRecordsRepository.deleteById(id);
		return "redirect:/admin/medicalrecord/medicalrecord";
	}
}
