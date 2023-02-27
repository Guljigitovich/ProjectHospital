package project.repositories.implRep;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import project.model.Hospital;
import project.model.Patient;
import project.repositories.interfaceRep.PatientRepositories;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
@RequiredArgsConstructor
public class PatientRepImpl implements PatientRepositories {
    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public Patient save(Patient newPatient) {
        entityManager.persist(newPatient);
        return newPatient;
    }

    @Override
    public List<Patient> getAllPatients(Long id) {
        return entityManager.createQuery("select p from Patient p where p.hospital.id=:id", Patient.class).setParameter("id",id).getResultList();
    }

    @Override
    public void deletePatientById(Long id) {
        entityManager.createQuery("delete from Patient where id=:id").setParameter("id",id).executeUpdate();
    }

    @Override
    public void updatePatient(Long id, Patient newPatient) {
        Patient patient1 = entityManager.find(Patient.class, id);
        patient1.setFirstName(newPatient.getFirstName());
        patient1.setLastName(newPatient.getLastName());
        patient1.setPhoneNumber(newPatient.getPhoneNumber());
        patient1.setEmail(newPatient.getEmail());
        patient1.setGender(newPatient.getGender());
        entityManager.merge(patient1);
    }

    @Override
    public Patient findById(Long patientId) {

        return entityManager.find(Patient.class, patientId);
    }


}
