package project.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import project.exceptions.MyException;
import project.model.Department;
import project.service.interfaceService.DepartmentService;
import project.service.interfaceService.HospitalService;

import java.util.List;

@Controller
@RequestMapping("/departments")
public class DepartmentController {
    private final DepartmentService departmentService;


    @Autowired
    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping("/{hospitalId}")
    public String getAll(@PathVariable Long hospitalId, Model model) {
        List<Department> allDepartments = departmentService.getAllDepartments(hospitalId);
        model.addAttribute("departments", allDepartments);
        model.addAttribute("hospitalId", hospitalId);
        return "department/getAllDepartment";
    }


    @GetMapping("/{hospitalId}/new")
    public String createDepartment(Model model, @PathVariable Long hospitalId) {
        model.addAttribute("department", new Department());
        model.addAttribute("hospitalId", hospitalId);
        return "department/newDepartment";
    }

    @PostMapping("/{hospitalId}/saveDepartment")
    public String save(@PathVariable("hospitalId") Long hospitalId, @ModelAttribute("newDepartment") Department department) {
        departmentService.save(hospitalId, department);
        return "redirect:/departments/{hospitalId}";
//        try {
//            departmentService.save(hospitalId, department);
//            return "redirect:/departments" + hospitalId ;
//        } catch (DataIntegrityViolationException e) {
//            model.addAttribute("Name", "This department already exists in the database");
//            model.addAttribute("hospitalId", hospitalId);
//            return "department/newDepartment";
//        }
    }

//    @GetMapping("/{departmentId}/edit")
//    public String findById(@PathVariable Long departmentId, Model model, @PathVariable Long id) {
//        try {
//            Department department = departmentService.findById(departmentId);
//
//            model.addAttribute("department", department);
//            return "department/update_department";
//        } catch (MyException e) {
//            return e.getMessage();
//        }
//    }
//
//    @PutMapping("{departmentId}/update")
//    public String updateDepartment(@PathVariable Long departmentId, @ModelAttribute("department") Department department, Model model) {
//        try {
//            departmentService.updateDepartment(departmentId, department);
//
//            return "redirect:/{id}/departments";
//        } catch (DataIntegrityViolationException e) {
//            model.addAttribute("Name", "This department already exists in the database");
//            return "department/update_department";
//        }
//    }
//
//    @GetMapping("/{departmentId}/doctors")
//    public String getDoctors(@PathVariable("id") Long id,
//                             @PathVariable("departmentId") Long departmentId, Model model) {
//        try {
//            model.addAttribute("department", departmentService.findById(departmentId));
//            model.addAttribute("doctors", departmentService.getDoctors(id, departmentId));
//            return "department/doctors";
//        } catch (MyException e) {
//            return e.getMessage();
//        }
//    }

    @GetMapping("/{departmentId}/edit")
    public String findById(@PathVariable("departmentId") Long departmentId, Model model) {
        Department department = departmentService.findById(departmentId);
        model.addAttribute("department", department);
        model.addAttribute("hospitalId", department.getHospital().getId());
        return "department/update_department";

    }
    @PutMapping("/{hospitalId}/{departmentId}/update")
    public String updateDepartment(@PathVariable("departmentId") Long departmentId,@PathVariable("hospitalId") Long hospitalId
            ,@ModelAttribute ("department") Department department
    ){
        departmentService.updateDepartment(departmentId,department);
        return "redirect:/departments/"+hospitalId;
    }

    @DeleteMapping("/{hospitalId}/{departmentId}/delete")
    public String deleteDepartment(@PathVariable("hospitalId") Long hospitalId, @PathVariable("departmentId") Long departmentId) {
        departmentService.deleteByIdDepartment(departmentId);
        return "redirect:/departments/" + hospitalId;
    }


}
