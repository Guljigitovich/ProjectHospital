package project.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import project.exceptions.MyException;
import project.model.Hospital;
import project.service.interfaceService.*;

@Controller
@RequestMapping("/hospitals")
public class HospitalController {
    private final HospitalService hospitalService;

    @Autowired
    public HospitalController(HospitalService hospitalService) {
        this.hospitalService = hospitalService;

    }

    @GetMapping()
    String getAll(Model model) {
        model.addAttribute("hospitals", hospitalService.getAllHospitals());
        return "hospital/getAllHospital";
    }


    @GetMapping("/new")
    public String createHospital(Model model) {
        model.addAttribute("newHospital", new Hospital());
        return "hospital/newHospital";
    }

    @PostMapping("/saveHospital")
    public String saveHospital(@ModelAttribute("newHospital") Hospital hospital) {
        hospitalService.save(hospital);
        return "redirect:/hospitals";
    }

    @DeleteMapping("{hospitalId}/delete")
    public String deleteById(@PathVariable("hospitalId") Long id) {
        hospitalService.deleteHospitalById(id);
        return "redirect: /hospitals";
    }

    @GetMapping("/{hospitalId}/edit")
    public String findById(@PathVariable Long hospitalId, Model model) {
        try {
            Hospital hospital = hospitalService.findById(hospitalId);

            model.addAttribute("hospital", hospital);
            return "hospital/update_hospital";
        } catch (MyException e) {

            return e.getMessage();
        }
    }

    @PutMapping("/{hospitalId}/update")
    public String updateHospital(@PathVariable Long hospitalId, @ModelAttribute("hospital") Hospital hospital) {
        hospitalService.updateHospital(hospitalId, hospital);
        return "redirect:/hospitals";
    }

    @GetMapping("/departments/{hospitalId}")
    public String departmentsPage(@PathVariable("hospitalId") Long hospitalId, Model model) {
        Hospital hospital = hospitalService.findById(hospitalId);
        model.addAttribute("departments", hospital.getDepartments());
        return "open";
    }


}
