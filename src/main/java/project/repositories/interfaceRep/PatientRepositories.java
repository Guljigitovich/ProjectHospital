package project.repositories.interfaceRep;

import project.model.Hospital;
import project.model.Patient;

import java.util.List;
import java.util.Optional;

public interface PatientRepositories {
   Patient save(Patient newPatient);
    List<Patient>getAllPatients(Long id);
    void deletePatientById(Long id);
    void updatePatient(Long id,Patient newPatient);


    Patient findById(Long patientId);
}
