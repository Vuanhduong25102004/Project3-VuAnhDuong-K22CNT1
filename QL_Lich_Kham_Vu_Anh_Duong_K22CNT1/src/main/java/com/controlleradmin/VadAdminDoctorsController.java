package com.controlleradmin;

import java.io.File;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.model.VadDoctors;
import com.repository.VadDoctorsRepository;

@Controller
public class VadAdminDoctorsController {

	@Autowired
	public VadDoctorsRepository doctorsRepository;

	@GetMapping("admin/doctors/doctors")
	public String adminDoctors(Model model) {
		model.addAttribute("title", "Quản lý thông tin bác sĩ");

		List<VadDoctors> doctors = doctorsRepository.findAll();
		model.addAttribute("doctors", doctors);

		return "admin/doctors/doctors";
	}

	@GetMapping("admin/doctors/doctors/create")
	public String showCreateDoctorform(Model model) {
		model.addAttribute("doctor", new VadDoctors());
		return "admin/doctors/create-doctor";
	}

	@PostMapping("admin/doctors/doctors/create")
	public String createDoctor(@ModelAttribute("doctor") VadDoctors doctor,
			@RequestParam("photoFile") MultipartFile photoFile) {
		if (!photoFile.isEmpty()) {
			try {
				String uploadsDir = "src/main/resources/static/images/";
				String realPathtoUploads = new File(uploadsDir).getAbsolutePath();
				// tạo thư mục nếu chưa tồn tại
				File uploadDir = new File(realPathtoUploads);
				if (!uploadDir.exists()) {
					uploadDir.mkdirs();
				}

				// lấy tên file gốc
				String orgName = photoFile.getOriginalFilename();
				// tạo đường dẫn lưu file
				String filePath = realPathtoUploads + File.separator + orgName;
				File dest = new File(filePath);
				// lưu file vào đĩa
				photoFile.transferTo(dest);

				doctor.setPhoto(orgName);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		doctorsRepository.save(doctor);

		return "redirect:/admin/doctors/doctors";
	}

	// mở form chỉnh sửa bác sĩ
	@GetMapping("admin/doctors/doctors/edit/{id}")
	public String showEditDoctorForm(@PathVariable("id") Long id, Model model) {
		Optional<VadDoctors> doctorOpt = doctorsRepository.findById(id);

		if (doctorOpt.isPresent()) {
			model.addAttribute("doctor", doctorOpt.get());
			return "/admin/doctors/edit-doctor";
		}
		return "redirect:/admin/doctors";
	}

	@PostMapping("admin/doctors/doctors/edit/{id}")
	public String updateDoctor(@PathVariable("id") Long id, @ModelAttribute("doctor") VadDoctors doctor, @RequestParam(value = "photoFile", required = false) MultipartFile photoFile) {
		
		//nếu ngjuoi72 dùng upload ảnh mới thì xử lý upload, nguoc lại giữa nguyên 
		if (photoFile != null && !photoFile.isEmpty()) {
			try {
				String uploadsDir = "src/main/resources/static/images/";
				String realPathtoUploads = new File(uploadsDir).getAbsolutePath();
				
				File uploadDir = new File(realPathtoUploads);
				if (!uploadDir.exists()) {
					uploadDir.mkdirs();
				}
				// Lấy tên file gốc
	            String orgName = photoFile.getOriginalFilename();
	            // Tạo đường dẫn lưu file
	            String filePath = realPathtoUploads + File.separator + orgName;
	            File dest = new File(filePath);
	            // Lưu file vào đĩa
	            photoFile.transferTo(dest);
	            
	            // Cập nhật ảnh bác sĩ
	            doctor.setPhoto(orgName);
			}catch(Exception e) {
				e.printStackTrace();
			}
		}else {
			//nếu không có ảnh mới thì giữ nguyên ảnh cũ
			Optional<VadDoctors> existingDoctorOpt = doctorsRepository.findById(id);
			if(existingDoctorOpt.isPresent()) {
				doctor.setPhoto(existingDoctorOpt.get().getPhoto());
			}
		}
		
		doctor.setId(id);
		doctorsRepository.save(doctor);
		return "redirect:/admin/doctors/doctors";
	}
	
	//xóa bác sĩ
	@GetMapping("admin/doctors/doctors/delete/{id}")
	public String deleteDoctor(@PathVariable("id") Long id) {
		doctorsRepository.deleteById(id);
		return "redirect:/admin/doctors/doctors";
	}

}
