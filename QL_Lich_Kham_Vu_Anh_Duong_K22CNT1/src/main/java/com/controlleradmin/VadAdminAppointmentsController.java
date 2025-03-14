package com.controlleradmin;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.model.VadAppointments;
import com.repository.VadAppointmentsRepository;

@Controller
public class VadAdminAppointmentsController {

	@Autowired
	private VadAppointmentsRepository appointmentsRepository;

	@GetMapping("admin/appointments/appointments")
	public String adminAppointment(Model model) {
		model.addAttribute("title", "Quản lý lịch khám");

		List<VadAppointments> appointments = appointmentsRepository.findAll();
		model.addAttribute("appointments", appointments);

		return "admin/appointments/appointments";
	}

	// GET: Hiển thị form chỉnh sửa lịch khám tại URL:
	// /admin/appointments/appointments/edit/{id}
	@GetMapping("admin/appointments/appointments/edit/{id}")
	public String showEditAppointmentForm(@PathVariable("id") Long id, Model model) {
		Optional<VadAppointments> appointmentOpt = appointmentsRepository.findById(id);
		if (appointmentOpt.isPresent()) {
			model.addAttribute("appointment", appointmentOpt.get());
			return "admin/appointments/edit-appointments"; // Template form chỉnh sửa
		}
		return "redirect:/admin/appointments/appointments";
	}

	// POST: Xử lý form chỉnh sửa lịch khám tại URL:
	// /admin/appointments/appointments/edit/{id}
	@PostMapping("admin/appointments/appointments/edit/{id}")
	public String updateAppointments(@PathVariable("id") Long id,
			@ModelAttribute("appointment") VadAppointments appointmentForm) {

		// Lấy đối tượng lịch khám ban đầu từ DB
		VadAppointments appointment = appointmentsRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid appointment id:" + id));

		// Nếu trường ngày từ form không null thì cập nhật, nếu null giữ lại giá trị cũ
		if (appointmentForm.getVadDate() != null) {
			appointment.setVadDate(appointmentForm.getVadDate());
		}
		// Các trường khác (giờ, trạng thái, …)
		appointment.setVadTime(appointmentForm.getVadTime());
		appointment.setVadStatus(appointmentForm.getVadStatus());

		// Không thay đổi thuộc tính doctor (giá trị cũ được giữ lại)

		appointmentsRepository.save(appointment);
		return "redirect:/admin/appointments/appointments";
	}

	// DELETE: Xóa lịch
	@GetMapping("admin/appointments/appointments/delete/{id}")
	public String deleteAppointments(@PathVariable("id") Long id) {
		appointmentsRepository.deleteById(id);
		return "redirect:/admin/appointments/appointments";
	}

}
