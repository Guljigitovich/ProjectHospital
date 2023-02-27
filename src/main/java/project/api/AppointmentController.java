package project.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import project.exceptions.MyException;
import project.model.Appointment;
import project.service.interfaceService.*;

import javax.naming.LinkLoopException;
import java.util.List;

@Controller
@RequestMapping("/appointments")
public class AppointmentController {
private final AppointmentService appointmentService;
   private final DoctorService doctorService;
   private final PatientService patientService;
   private final DepartmentService departmentService;
   private final HospitalService hospitalService;

@Autowired
    public AppointmentController(AppointmentService appointmentService, DoctorService doctorService, PatientService patientService, DepartmentService departmentService, HospitalService hospitalService) {
        this.appointmentService = appointmentService;
    this.doctorService = doctorService;
    this.patientService = patientService;
    this.departmentService = departmentService;
    this.hospitalService = hospitalService;
}

    @GetMapping("/{hospitalId}")
    public String getAllAppointments(@PathVariable("hospitalId") Long hospitalId,Model model) {
        try {
            List<Appointment> allAppointment = appointmentService.getAllAppointment(hospitalId);
            model.addAttribute("appointments", allAppointment);
            model.addAttribute("hospitalId", hospitalId);
            return "appointment/getAllAppointment";
        }catch (MyException e){
            return e.getMessage();
        }
    }

    @GetMapping("/{hospitalId}/new")
    public String createAppointment(@PathVariable("hospitalId") Long hospitalId,Model model) {
        try {
            model.addAttribute("appointment", new Appointment());
            model.addAttribute("doctors", doctorService.getAllDoctors(hospitalId));
            model.addAttribute("patients", patientService.getAllPatients(hospitalId));
            model.addAttribute("departments", departmentService.getAllDepartments(hospitalId));
            return "appointment/newAppointment";
        }catch (MyException e){
            return e.getMessage();
        }
    }

    @PostMapping("/{hospitalId}/saveAppointment")
    public String saveAppointment(@PathVariable("hospitalId") Long hospitalId,
                                  @ModelAttribute("newAppointment")Appointment appointment,Model model){
    try {
        appointmentService.saveAppointment(hospitalId, appointment);
        model.addAttribute("doctors", doctorService.getAllDoctors(hospitalId));
        model.addAttribute("patients", patientService.getAllPatients(hospitalId));
        model.addAttribute("departments", departmentService.getAllDepartments(hospitalId));
        return "redirect:/appointments/{hospitalId}";
    }catch (MyException e){
        return e.getMessage();
    }
}

    @GetMapping("/{hospitalId}/{appointmentId}/edit")
    public String findById(@PathVariable("appointmentId") Long appointmentId, Model model,
                           @PathVariable("hospitalId") Long hospitalId) {
        try {
            model.addAttribute("appointment", appointmentService.findById(appointmentId));
            model.addAttribute("doctors", doctorService.getAllDoctors(hospitalId));
            model.addAttribute("patients", patientService.getAllPatients(hospitalId));
            model.addAttribute("departments", departmentService.getAllDepartments(hospitalId));
            return "appointment/update_appointment";
        } catch (MyException e){
             return e.getMessage();
        }
    }

    @PutMapping("/{hospitalId}/{appointmentId}/update")
    public String updateAppointment(@PathVariable("appointmentId") Long appointmentId,
                                    @ModelAttribute("appointment") Appointment appointment,
                                    @PathVariable("hospitalId") Long hospitalId) {
        try {
            appointmentService.updateAppointment(appointmentId, appointment);
            return "redirect:/appointments/" + hospitalId;
        }catch (MyException e){
            return e.getMessage();
        }
    }


    @DeleteMapping("/{hospitalId}/{appointmentId}/delete")
    public String deleteAppointment(@PathVariable("appointmentId") Long appointmentId,
                                    @PathVariable("hospitalId") Long hospitalId) {
        try {
            appointmentService.deleteByIdAppointment(hospitalId, appointmentId);
            return "redirect:/appointments/" + hospitalId;
        }catch (MyException e){
            return e.getMessage();
        }
    }

}
