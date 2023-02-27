package project.service.serviceImpl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.model.Appointment;
import project.model.Hospital;
import project.repositories.interfaceRep.*;
import project.service.interfaceService.AppointmentService;

import java.util.List;
@Service
@RequiredArgsConstructor
@Transactional


public class AppointmentServiceImpl implements AppointmentService {
    private final AppointmentRepositories appointmentRepositories;
    private final HospitalRepositories hospitalRepositories;
    private final DoctorRepositories doctorRepositories;
    private final PatientRepositories patientRepositories;
    private final DepartmentRepositories departmentRepositories;

    @Override
    public void saveAppointment(Long hospitalId,Appointment appointment) {
        Hospital hospital = hospitalRepositories.findById(hospitalId);
        appointment.setDoctor(doctorRepositories.findById(appointment.getDoctorId()));
        appointment.setDepartment(departmentRepositories.findById(appointment.getDepartmentId()));
        appointment.setPatient(patientRepositories.findById(appointment.getPatientId()));
        hospital.addAppointment(appointment);
        appointment.getPatient().addAppointment(appointment);
        appointment.getDoctor().addAppointment(appointment);
        appointmentRepositories.saveAppointment(appointment);
    }



    @Override
    public void deleteByIdAppointment(Long hospitalId, Long appointmentId) {
        Appointment appointment = appointmentRepositories.findById(appointmentId);
        Hospital hospital = hospitalRepositories.findById(hospitalId);
        appointment.getPatient().setAppointments(null);
        appointment.getDoctor().setAppointments(null);
        hospital.getAppointments().remove(appointment);
        appointmentRepositories.deleteByIdAppointment(appointmentId);

    }

    @Override
    public void updateAppointment(Long id, Appointment newAppointment) {
      this.appointmentRepositories.updateAppointment(id,newAppointment);
    }

    @Override
    public Appointment findById(Long appointmentId) {
        return appointmentRepositories.findById(appointmentId);
    }

    @Override
    public List<Appointment> getAllAppointment( Long id) {
        return this.appointmentRepositories.getAllAppointment(id);
    }
}
