package project.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import project.model.Doctor;
import project.service.interfaceService.DepartmentService;
import project.service.interfaceService.DoctorService;

import javax.print.Doc;
import java.util.List;

@Controller
@RequestMapping("/doctors")
public class DoctorController {
    private final DoctorService doctorService;
    private final DepartmentService departmentService;

    @Autowired
    public DoctorController(DoctorService doctorService, DepartmentService departmentService) {
        this.doctorService = doctorService;
        this.departmentService = departmentService;
    }

    @GetMapping("/{hospitalId}")
    public String getAllDoctors(@PathVariable Long hospitalId, Model model) {
        List<Doctor> doctors = doctorService.getAllDoctors(hospitalId);
        model.addAttribute("doctors", doctors);
        model.addAttribute("hospitalId", hospitalId);
        return "doctor/getAllDoctor";
    }

    @GetMapping("/{hospitalId}/new")
    public String createDoctor(@PathVariable Long hospitalId, Model model) {
        model.addAttribute("hospitalId", hospitalId);
        model.addAttribute("doctor", new Doctor());
        return "doctor/newDoctor";
    }

    @PostMapping("/{hospitalId}/saveDoctor")
    public String saveDoctor(@PathVariable("hospitalId") Long hospitalId,
                             @ModelAttribute("newDoctor") Doctor doctor) {
        doctorService.save(hospitalId, doctor);
        return "redirect:/doctors/{hospitalId}";
    }

    @GetMapping("/{doctorId}/edit")
    public String findById(@PathVariable Long doctorId, Model model) {
        Doctor doctor = doctorService.findById(doctorId);
        model.addAttribute("doctor", doctor);
        model.addAttribute("hospitalId", doctor.getHospital().getId());
        return "doctor/update_doctor";
    }

    @PutMapping("/{hospitalId}/{doctorId}/update")
    public String update(@PathVariable("doctorId") Long doctorId, @PathVariable("hospitalId") Long hospitalId,
                         @ModelAttribute("doctor") Doctor doctor) {
        doctorService.updateDoctor(doctorId, doctor);
        return "redirect:/doctors/" + hospitalId;
    }

    @DeleteMapping("/{hospitalId}/{doctorId}/delete")
    public String delete(@PathVariable("doctorId") Long doctorId, @PathVariable("hospitalId") Long hospitalId) {
        doctorService.deleteByIdDoctor(doctorId);
        return "redirect:/doctors/" + hospitalId;
    }

    @GetMapping("/{hospitalId}/{doctorId}/departments")
    public String getDoctorDepartments(@PathVariable("hospitalId") Long hospitalId,
                                       @PathVariable("doctorId") Long doctorId,
                                       Model model) {
        model.addAttribute("departments", doctorService.getDoctorDepartments(doctorId));
        return "doctor/departments";
    }
    @GetMapping("/{hospitalId}/{doctorId}/assignDepartment")
    public String assign(@PathVariable("hospitalId") Long hospitalId,
                         @PathVariable("doctorId") Long doctorId,
                         Model model){
        model.addAttribute("doctor", new Doctor());
        model.addAttribute("departments", departmentService.getAllDepartments(hospitalId));
        return "doctor/assignToDepartment";
    }
    @PostMapping("/{hospitalId}/{doctorId}/saveAssignDepartment")
    public String saveDep(@PathVariable("hospitalId") Long hospitalId,
                          @PathVariable("doctorId") Long doctorId,
                          @ModelAttribute("doctor") Doctor doctor){
        doctorService.assignToDepartment(doctorId, doctor);
        return "redirect:/doctors/{hospitalId}/{doctorId}/departments";
    }
    @DeleteMapping("/{hospitalId}/{doctorId}/{departmentId}/deleteDepartment")
    public String deleteDepartment(@PathVariable("hospitalId") Long hospitalId,
                                   @PathVariable("doctorId") Long doctorId,
                                   @PathVariable("departmentId") Long departmentId){
        doctorService.deleteAssignDepartment(doctorId, departmentId);
        return "redirect:/doctors/{hospitalId}/{doctorId}/departments";
    }
}

