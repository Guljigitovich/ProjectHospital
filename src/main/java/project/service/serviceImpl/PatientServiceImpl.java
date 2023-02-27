package project.service.serviceImpl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.model.Appointment;
import project.model.Hospital;
import project.model.Patient;
import project.repositories.interfaceRep.AppointmentRepositories;
import project.repositories.interfaceRep.HospitalRepositories;
import project.repositories.interfaceRep.PatientRepositories;
import project.service.interfaceService.PatientService;

import java.util.List;
@Service
@RequiredArgsConstructor
@Transactional
public class PatientServiceImpl implements PatientService {
    private final PatientRepositories patientRepositories;
    private final HospitalRepositories hospitalRepositories;
    private final AppointmentRepositories appointmentRepositories;

    @Override
    public void save(Long hospitalId,Patient patient) {
        Hospital hospital = hospitalRepositories.findById(hospitalId);
        patient.setHospital(hospital);
        patientRepositories.save(patient);

    }

    @Override
    public List<Patient> getAllPatients(Long id) {
        return this.patientRepositories.getAllPatients(id);
    }

    @Override
    public void deletePatientById(Long id) {
        Patient patient = patientRepositories.findById(id);
        Hospital hospital = patient.getHospital();
        List<Appointment> appointments = patient.getAppointments();
        appointments.forEach(a -> a.getPatient().setAppointments(null));
        appointments.forEach(a -> a.getDoctor().setAppointments(null));
        hospital.getAppointments().removeAll(appointments);
        for (int i = 0; i < appointments.size(); i++) {
            appointmentRepositories.deleteByIdAppointment(appointments.get(i).getId());
        }
       this.patientRepositories.deletePatientById(id);
    }

    @Override
    public void updatePatient(Long id, Patient newPatient) {
       this.patientRepositories.updatePatient(id, newPatient);
    }

    @Override
    public Patient findById(Long patientId) {
        return patientRepositories.findById(patientId);
    }
}
