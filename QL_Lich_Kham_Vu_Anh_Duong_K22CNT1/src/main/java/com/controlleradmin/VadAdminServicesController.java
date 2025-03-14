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

import com.model.VadServices;
import com.repository.VadServicesRepository;

@Controller
public class VadAdminServicesController {
	
	@Autowired
	private VadServicesRepository servicesRepository;
	
	@GetMapping("admin/services/services")
    public String adminServices(Model model) {
        model.addAttribute("title", "Quản lý thông tin dịch vụ");
        
        List<VadServices> services = servicesRepository.findAll();
		model.addAttribute("services", services);
		
        return "admin/services/services";
    }
    
    @GetMapping("admin/services/services/create")
    public String showCreateForm(Model model) {
        model.addAttribute("service", new VadServices());
        return "admin/services/create-service"; // Trang form tạo mới
    }
    
    @PostMapping("admin/services/services/create")
    public String createService(@ModelAttribute("service") VadServices service) {
        servicesRepository.save(service);
        return "redirect:/admin/services/services";
    }
    
    // UPDATE: Hiển thị form chỉnh sửa dịch vụ
    @GetMapping("admin/services/services/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        Optional<VadServices> serviceOpt = servicesRepository.findById(id);
        if (serviceOpt.isPresent()) {
            model.addAttribute("service", serviceOpt.get());
            return "/admin/services/edit-service"; // Trang form chỉnh sửa
        }
        return "redirect:/admin/services";
    }

    // UPDATE: Xử lý form chỉnh sửa dịch vụ
    @PostMapping("admin/services/services/edit/{id}")
    public String updateService(@PathVariable("id") Long id, @ModelAttribute("service") VadServices service) {
        service.setId(id); // Giả sử model của bạn có thuộc tính id
        servicesRepository.save(service);
        return "redirect:/admin/services/services";
    }

    // DELETE: Xóa dịch vụ
    @GetMapping("admin/services/services/delete/{id}")
    public String deleteService(@PathVariable("id") Long id) {
        servicesRepository.deleteById(id);
        return "redirect:/admin/services/services";
    }
}
