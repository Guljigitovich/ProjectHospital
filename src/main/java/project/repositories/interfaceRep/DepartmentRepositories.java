package project.repositories.interfaceRep;

import project.model.Department;
import project.model.Doctor;

import java.util.List;

public interface DepartmentRepositories {

    List<Department>getAllDepartments(Long id);

    void deleteByIdDepartment(Long id);
    void updateDepartment(Long id,Department newDepartment);
    void assignToDepartment(Doctor doctor);

    void  save(Department department);

    Department findById(Long departmentId);
    List<Doctor>getDoctors(Long departmentId);

    String assignDoctorToDepartment(Long doctorId, Long departmentId);
}
