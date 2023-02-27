package project.service.interfaceService;

import project.model.Department;
import project.model.Doctor;

import java.util.List;

public interface DepartmentService {
    void save (Long hospitalId,Department department);
    List<Department>getAllDepartments(Long id);
    String assignDoctorToDepartment(Long doctorId,Long departmentId);
    void deleteByIdDepartment(Long id);
    void updateDepartment(Long id,Department newDepartment);

    Department findById(Long departmentId);
    List<Doctor>getDoctors(Long id,Long departmentId);
}
