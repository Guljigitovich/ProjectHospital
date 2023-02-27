package project.service.interfaceService;

import project.model.Hospital;
import project.model.Patient;

import java.util.List;

public interface PatientService {
    void save (Long hospitalId,Patient patient);
    List<Patient> getAllPatients(Long id);
    void deletePatientById(Long id);
    void updatePatient(Long id,Patient newPatient);

    Patient findById(Long patientId);
}
