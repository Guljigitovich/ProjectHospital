package project.repositories.interfaceRep;

import project.model.Hospital;

import java.util.List;
import java.util.Optional;

public interface HospitalRepositories {
    void save (Hospital hospital);
    List<Hospital>getAllHospitals();
    void deleteHospitalById(Long id);
    void updateHospital(Long id , Hospital hospital);

    Hospital findById(Long hospitalId);
}
