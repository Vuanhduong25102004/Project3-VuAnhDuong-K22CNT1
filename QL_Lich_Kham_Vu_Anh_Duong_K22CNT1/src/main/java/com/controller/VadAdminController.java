package com.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.model.VadAppointments;
import com.model.VadServices;
import com.model.VadUser;
import com.repository.VadAppointmentsRepository;
import com.repository.VadServicesRepository;
import com.repository.VadUserRepository;

import org.springframework.ui.Model;

@Controller
@RequestMapping("/admin")

public class VadAdminController {
	
	@Autowired
	private VadServicesRepository servicesRepository;
	
	@Autowired
	private VadUserRepository userRepository;
	
	@Autowired
	private VadAppointmentsRepository appointmentsRepository;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
    @GetMapping("dashboard")
    public String adminHome(Model model) {
        model.addAttribute("title", "Admin Dashboard");
        return "admin/dashboard";
    }
    
    @GetMapping("services/services")
    public String adminServices(Model model) {
        model.addAttribute("title", "Quản lý Dịch vụ");
        
        List<VadServices> services = servicesRepository.findAll();
		model.addAttribute("services", services);
		
        return "admin/services/services";
    }
    
    @GetMapping("services/services/create")
    public String showCreateForm(Model model) {
        model.addAttribute("service", new VadServices());
        return "admin/services/create-service"; // Trang form tạo mới
    }
    
    @PostMapping("services/services/create")
    public String createService(@ModelAttribute("service") VadServices service) {
        servicesRepository.save(service);
        return "redirect:/admin/services/services";
    }
    
    // UPDATE: Hiển thị form chỉnh sửa dịch vụ
    @GetMapping("services/services/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        Optional<VadServices> serviceOpt = servicesRepository.findById(id);
        if (serviceOpt.isPresent()) {
            model.addAttribute("service", serviceOpt.get());
            return "/admin/services/edit-service"; // Trang form chỉnh sửa
        }
        return "redirect:/admin/services";
    }

    // UPDATE: Xử lý form chỉnh sửa dịch vụ
    @PostMapping("services/services/edit/{id}")
    public String updateService(@PathVariable("id") Long id, @ModelAttribute("service") VadServices service) {
        service.setId(id); // Giả sử model của bạn có thuộc tính id
        servicesRepository.save(service);
        return "redirect:/admin/services/services";
    }

    // DELETE: Xóa dịch vụ
    @GetMapping("services/services/delete/{id}")
    public String deleteService(@PathVariable("id") Long id) {
        servicesRepository.deleteById(id);
        return "redirect:/admin/services/services";
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    @GetMapping("/users/users")
    public String adminUsers(Model model) {
        model.addAttribute("title", "Quản lý người dùng");
        
        List<VadUser> user = userRepository.findAll();
		model.addAttribute("users", user);
		
        return "admin/users/users";
    }
    
    // UPDATE: Hiển thị form chỉnh sửa người dùng
    @GetMapping("users/users/edit/{id}")
    public String showEditUserForm(@PathVariable("id") Long id, Model model) {
        Optional<VadUser> userOpt = userRepository.findById(id);
        if (userOpt.isPresent()) {
            model.addAttribute("user", userOpt.get());
            return "admin/users/edit-user"; // Trang form chỉnh sửa
        }
        return "redirect:/admin/users";
    }

    // UPDATE: Xử lý form chỉnh người dùng
    @PostMapping("users/users/edit/{id}")
    public String updateUser(@PathVariable("id") Long id, @ModelAttribute("user") VadUser user) {
        Optional<VadUser> existingUserOpt = userRepository.findById(id);
        if(existingUserOpt.isPresent()){
             VadUser existingUser = existingUserOpt.get();
             // Cập nhật các trường thông tin khác
             existingUser.setFullname(user.getFullname());
             existingUser.setEmail(user.getEmail());
             existingUser.setPhone(user.getPhone());
             
             // Nếu người dùng nhập mật khẩu mới (không rỗng), thì mã hóa mật khẩu đó
             if(user.getPassword() != null && !user.getPassword().trim().isEmpty()){
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
    @GetMapping("users/users/delete/{id}")
    public String deleteUsers(@PathVariable("id") Long id) {
        userRepository.deleteById(id);
        return "redirect:/admin/users/users";
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////
    
    @GetMapping("/appointments/appointments")
    public String adminAppointment(Model model) {
        model.addAttribute("title", "Quản lý lịch khám");
        
        List<VadAppointments> appointments = appointmentsRepository.findAll();
		model.addAttribute("appointments", appointments);
		
        return "admin/appointments/appointments";
    }
    
 // GET: Hiển thị form chỉnh sửa lịch khám tại URL: /admin/appointments/appointments/edit/{id}
    @GetMapping("/appointments/appointments/edit/{id}")
    public String showEditAppointmentForm(@PathVariable("id") Long id, Model model) {
        Optional<VadAppointments> appointmentOpt = appointmentsRepository.findById(id);
        if (appointmentOpt.isPresent()) {
            model.addAttribute("appointment", appointmentOpt.get());
            return "admin/appointments/edit-appointments"; // Template form chỉnh sửa
        }
        return "redirect:/admin/appointments/appointments";
    }

    // POST: Xử lý form chỉnh sửa lịch khám tại URL: /admin/appointments/appointments/edit/{id}
    @PostMapping("/appointments/appointments/edit/{id}")
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
    @GetMapping("appointments/appointments/delete/{id}")
    public String deleteAppointments(@PathVariable("id") Long id) {
    	appointmentsRepository.deleteById(id);
        return "redirect:/admin/appointments/appointments";
    }
    
}