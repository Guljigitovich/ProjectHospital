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
import project.repositories.interfaceRep.DoctorRepositories;
import project.repositories.interfaceRep.HospitalRepositories;
import project.service.interfaceService.DepartmentService;
import project.service.interfaceService.DoctorService;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class DoctorServiceImpl implements DoctorService {
    private final DoctorRepositories doctorRepositories;
    private final HospitalRepositories hospitalRepositories;
    private final AppointmentRepositories appointmentRepositories;
    private final DepartmentRepositories departmentRepositories;

    @Override
    public List<Doctor> getAllDoctors(Long hospitalId) {
        return this.doctorRepositories.getAllDoctors(hospitalId);
    }

    @Override
    public void deleteByIdDoctor(Long id) {
        Doctor doctor = doctorRepositories.findById(id);
        Hospital hospital = doctor.getHospital();
        List<Appointment> appointments = doctor.getAppointments();
        appointments.forEach(a -> a.getDoctor().setAppointments(null));
        appointments.forEach(a -> a.getPatient().setAppointments(null));
        hospital.getAppointments().removeAll(appointments);

        for (int i = 0; i < appointments.size(); i++) {
            appointmentRepositories.deleteByIdAppointment(appointments.get(i).getId());
        }
        this.doctorRepositories.deleteByIdDoctor(id);
    }

    @Override
    public void updateDoctor(Long id, Doctor newDoctor) {
        this.doctorRepositories.updateDoctor(id, newDoctor);
    }

    @Override
    public List<Department> getAllDepartment(Long doctorId) {
        return doctorRepositories.getAllDepartments(doctorId);
    }

    @Override
    public List<Appointment> getAllAppointment(Long doctorId) {
        return doctorRepositories.getAllAppointments(doctorId);
    }

    @Override
    public void save(Long hospitalId, Doctor doctor) {
        Hospital hospital = hospitalRepositories.findById(hospitalId);
        doctor.setHospital(hospital);
        doctorRepositories.save(doctor);
    }

    @Override
    public Doctor findById(Long doctorId) {
        return doctorRepositories.findById(doctorId);
    }

    @Override
    public void assignToDepartment(Long doctorId, Doctor doctor) {
        Department department = departmentRepositories.findById(doctor.getDepartmentId());
        Doctor oldDoctor = doctorRepositories.findById(doctorId);
        department.addDoctor(oldDoctor);
        oldDoctor.addDepartment(department);
        doctorRepositories.assignToDepartment(oldDoctor);

    }

    @Override
    public List<Department> getDoctorDepartments(Long doctorId) {
        Doctor doctor = doctorRepositories.findById(doctorId);
        Long id = doctor.getHospital().getId();

        List<Department> doctorsDepartments = new ArrayList<>();
        for (Department allDepartment : departmentRepositories.getAllDepartments(id)) {
            for (Doctor allDepartmentDoctor : allDepartment.getDoctors()) {
                if (allDepartmentDoctor.getId().equals(doctorId)) {
                    doctorsDepartments.add(allDepartment);
                }
            }
        }

        return doctorsDepartments;

    }

    @Override
    public void deleteAssignDepartment(Long doctorId, Long departmentId) {
        Doctor doctor = doctorRepositories.findById(doctorId);
        Department department = departmentRepositories.findById(departmentId);
        department.getDoctors().remove(doctor);
        doctor.getDepartments().remove(department);
        doctorRepositories.deleteAssignDepartment(doctor);
    }
}
