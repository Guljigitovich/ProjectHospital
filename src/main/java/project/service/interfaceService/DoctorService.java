package project.service.interfaceService;

import project.model.Appointment;
import project.model.Department;
import project.model.Doctor;

import java.util.List;

public interface DoctorService {
    List<Doctor> getAllDoctors(Long hospitalId);
    void deleteByIdDoctor(Long id);
    void updateDoctor(Long id,Doctor newDoctor);
    List<Department>getAllDepartment(Long doctorId);
    List<Appointment>getAllAppointment(Long doctorId);
    void save (Long hospitalId, Doctor doctor);
    Doctor findById(Long doctorId);
    void assignToDepartment(Long doctorId, Doctor doctor);

    List<Department> getDoctorDepartments(Long doctorId);

    void deleteAssignDepartment(Long doctorId, Long departmentId);
}
