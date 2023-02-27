package project.service.serviceImpl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.model.Appointment;
import project.model.Department;
import project.model.Doctor;
import project.model.Hospital;
import project.repositories.interfaceRep.AppointmentRepositories;
import project.repositories.interfaceRep.DepartmentRepositories;
import project.repositories.interfaceRep.HospitalRepositories;
import project.service.interfaceService.DepartmentService;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class DepartmentServiceImpl implements DepartmentService {
    private final DepartmentRepositories departmentRepositories;
    private final HospitalRepositories hospitalRepo;
    private final AppointmentRepositories appointmentRepositories;


    @Override
    public void save(Long hospitalId, Department department) {
        Hospital hospital = hospitalRepo.findById(hospitalId);
        department.setHospital(hospital);
        departmentRepositories.save(department);

    }

    @Override
    public List<Department> getAllDepartments(Long id) {
        return this.departmentRepositories.getAllDepartments(id);
    }

    @Override
    public String assignDoctorToDepartment(Long doctorId, Long departmentId) {
        return this.departmentRepositories.assignDoctorToDepartment(doctorId, departmentId);
    }

    @Override
    public void deleteByIdDepartment(Long id) {
        Department department = departmentRepositories.findById(id);
        Hospital hospital = department.getHospital();
        List<Appointment> appointments = hospital.getAppointments();

        appointments.forEach(a -> a.getDoctor().setAppointments(null));
        appointments.forEach(a -> a.getPatient().setAppointments(null));

        List<Appointment> appointmentList = appointments.stream().filter(s -> s.getDepartment().getId().equals(id)).toList();
        hospital.getAppointments().removeAll(appointmentList);
        appointmentList.forEach(s -> appointmentRepositories.deleteByIdAppointment(s.getId()));


//        List<Department> departments = department.getHospital().getDepartments();
//        departments.removeIf(s->s.getId().equals(id));

//        List<Doctor> doctors = department.getDoctors();
//        doctors.forEach(d->d.getDepartments().removeIf(s->s.getId().equals(id)));

        this.departmentRepositories.deleteByIdDepartment(id);
    }

    @Override
    public void updateDepartment(Long id, Department newDepartment) {
        this.departmentRepositories.updateDepartment(id, newDepartment);
    }

    @Override
    public Department findById(Long departmentId) {

        return departmentRepositories.findById(departmentId);
    }

    @Override
    public List<Doctor> getDoctors(Long id, Long departmentId) {
        return departmentRepositories.getDoctors(departmentId);
    }
}
