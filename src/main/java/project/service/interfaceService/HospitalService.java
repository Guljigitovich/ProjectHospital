package project.service.interfaceService;

import project.model.Hospital;

import java.util.List;
import java.util.Optional;

public interface HospitalService {
    void save(Hospital hospital);

    List<Hospital> getAllHospitals();

    void deleteHospitalById(Long id);

    void updateHospital(Long id, Hospital hospital);

    Hospital findById(Long id);
}
