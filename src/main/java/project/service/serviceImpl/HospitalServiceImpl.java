package project.service.serviceImpl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.model.Hospital;
import project.repositories.interfaceRep.HospitalRepositories;
import project.service.interfaceService.HospitalService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class HospitalServiceImpl implements HospitalService {
    private final HospitalRepositories hospitalRepositories;

    @Override
    public void save(Hospital hospital) {
        this.hospitalRepositories.save(hospital);
    }

    @Override
    public List<Hospital> getAllHospitals() {
        return this.hospitalRepositories.getAllHospitals();
    }

    @Override
    public void deleteHospitalById(Long id) {
        Hospital hospital = hospitalRepositories.findById(id);
        hospital.getDepartments().forEach(department -> department.setHospital(null));
        hospital.getPatients().forEach(patient -> patient.setHospital(null));
        hospital.getDoctors().forEach(doctor -> doctor.setHospital(null));
        this.hospitalRepositories.deleteHospitalById(id);
    }

    @Override
    public void updateHospital(Long id, Hospital hospital) {
        this.hospitalRepositories.updateHospital(id, hospital);
    }

    @Override
    public Hospital findById(Long id) {
        return hospitalRepositories.findById(id);
    }
}
