package project.repositories.implRep;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import project.model.Appointment;
import project.model.Department;
import project.model.Doctor;
import project.repositories.interfaceRep.DoctorRepositories;

import java.util.List;
@Repository
@Transactional
@RequiredArgsConstructor
public class DoctorRepImpl implements DoctorRepositories {
@PersistenceContext
private final EntityManager entityManager;

    @Override
    public List<Doctor> getAllDoctors(Long hospitalId) {
        return entityManager
                .createQuery("select d from Doctor d join d.hospital h where h.id = :id", Doctor.class)
                .setParameter("id", hospitalId)
                .getResultList();
    }

    @Override
    public void deleteByIdDoctor(Long id) {
        entityManager
                .createQuery("delete from Doctor where id = :id")
                .setParameter("id",id)
                .executeUpdate();
    }

    @Override
    public void updateDoctor(Long id, Doctor newDoctor) {
        Doctor doctor = entityManager.find(Doctor.class, id);
        doctor.setFirstName(newDoctor.getFirstName());
        doctor.setLastName(newDoctor.getLastName());
        doctor.setEmail(newDoctor.getEmail());
        doctor.setPosition(newDoctor.getPosition());
        entityManager.merge(doctor);
    }

    @Override
    public void save(Doctor doctor) {
        entityManager.persist(doctor);
    }

    @Override
    public Doctor findById(Long id) {
        return entityManager.find(Doctor.class,id);
    }

    @Override
    public void assignToDepartment(Doctor doctor) {
        entityManager.merge(doctor);
    }

    @Override
    public List<Department> getAllDepartments(Long doctorId) {
        return  entityManager.createQuery("from Department d join d.doctors do where do.id=:id", Department.class)
                .setParameter("id",doctorId).getResultList();

    }

    @Override
    public List<Appointment> getAllAppointments(Long doctorId) {
        return entityManager.createQuery("from Appointment a join a.doctor d where d.id=:id", Appointment.class)
                .setParameter("id",doctorId).getResultList();
    }

    @Override
    public void deleteAssignDepartment(Doctor doctor) {
        entityManager.merge(doctor);
    }
}
