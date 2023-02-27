package project.repositories.implRep;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import project.model.Department;
import project.model.Doctor;
import project.model.Hospital;
import project.repositories.interfaceRep.DepartmentRepositories;

import java.util.List;
@Repository
@Transactional
@RequiredArgsConstructor
public class DepartmentRepImpl implements DepartmentRepositories {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Department> getAllDepartments(Long id) {
        return entityManager.createQuery("select d from Department d  where d.hospital.id=:id", Department.class).setParameter("id",id).getResultList();
    }


    @Override
    public void deleteByIdDepartment(Long id) {
       entityManager
               .createQuery("delete from Department where id=:id").setParameter("id",id)
               .executeUpdate();

    }

    @Override
    public void updateDepartment(Long id, Department newDepartment) {
        Department department = entityManager.find(Department.class, id);
        department.setName(newDepartment.getName());
        department.setImage(newDepartment.getImage());
        entityManager.merge(department);
    }

    @Override
    public void assignToDepartment(Doctor doctor) {
    entityManager.merge(doctor);
    }

    @Override
    public void save(Department department) {
        entityManager.persist(department);
    }

    @Override
    public Department findById(Long departmentId) {
        return entityManager.find(Department.class,departmentId);
    }

    @Override
    public List<Doctor> getDoctors(Long departmentId) {
        return entityManager.createQuery("select d from Doctor d join d.departments de where de.id=:id",
                Doctor.class).setParameter("id",departmentId).getResultList();
    }

    @Override
    public String assignDoctorToDepartment(Long doctorId, Long departmentId) {
        return null;
    }
}
