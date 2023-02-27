package project.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import project.model.Patient;
import project.service.interfaceService.PatientService;

import java.util.List;

@Controller
@RequestMapping("/patients")
public class PatientController {
    private final PatientService patientService;

    @Autowired
    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping("/{hospitalId}")
    public String getAll(@PathVariable Long hospitalId, Model model) {
        List<Patient> allPatients = patientService.getAllPatients(hospitalId);
        model.addAttribute("patients", allPatients);
        model.addAttribute("hospitalId", hospitalId);
        return "patient/getAllPatient";
    }

    @GetMapping("/{hospitalId}/new")
    public String createPatient(@PathVariable Long hospitalId, Model model) {
        model.addAttribute("patient", new Patient());
        model.addAttribute("hospitalId", hospitalId);
        return "patient/newPatient";
    }
    @PostMapping("/{hospitalId}/savePatient")
    public String savePatient( @PathVariable Long hospitalId,
                              @ModelAttribute("newPatient") Patient patient){
        patientService.save(hospitalId,patient);
        return "redirect:/patients/{hospitalId}";
    }
    @GetMapping("/{patientId}/edit")
    public String findById(@PathVariable Long patientId,Model model){
        Patient patient = patientService.findById(patientId);
        model.addAttribute("patient",patient);
        model.addAttribute("hospitalId",patient.getHospital().getId());
        return "patient/update_patient";
    }

    @PutMapping("/{hospitalId}/{patientId}/update")
    public String updatePatient(@PathVariable Long hospitalId, @PathVariable Long patientId,
                                @ModelAttribute("patient") Patient patient){
        patientService.updatePatient(patientId,patient);
        return "redirect:/patients/"+hospitalId;
    }

    @GetMapping("/{hospitalId}/{patientId}/delete")
    public String delete(@PathVariable Long hospitalId, @PathVariable Long patientId){
        patientService.deletePatientById(patientId);
        return "redirect:/patients/"+hospitalId;
    }
}
