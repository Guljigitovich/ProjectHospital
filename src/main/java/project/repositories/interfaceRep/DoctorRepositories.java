package project.repositories.interfaceRep;

import project.model.Appointment;
import project.model.Department;
import project.model.Doctor;

import java.util.List;

public interface DoctorRepositories {
    List<Doctor>getAllDoctors(Long hospitalId);
    void deleteByIdDoctor(Long id);
    void updateDoctor(Long id,Doctor newDoctor);
   void save(Doctor doctor);
   Doctor findById(Long id);
    void assignToDepartment(Doctor doctor);
    List<Department>getAllDepartments(Long doctorId);
    List<Appointment>getAllAppointments(Long doctorId);

    void deleteAssignDepartment(Doctor doctor);
}
