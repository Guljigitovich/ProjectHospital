package project.repositories.implRep;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import project.model.Hospital;
import project.repositories.interfaceRep.HospitalRepositories;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
@RequiredArgsConstructor
public class HospitalRepImpl implements HospitalRepositories {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void save(Hospital hospital) {
        entityManager.persist(hospital);

    }

    @Override
    public List<Hospital> getAllHospitals() {
        return entityManager.createQuery("select h from Hospital h", Hospital.class).getResultList();
    }

    @Override
    public void deleteHospitalById(Long id) {
//        entityManager.createQuery("delete from Hospital where id=:id").setParameter("id",id).executeUpdate();
        entityManager.remove(entityManager.find(Hospital.class, id));
    }

    @Override
    @Transactional
    public void updateHospital(Long id, Hospital newHospital) {
        Hospital hospital = entityManager.find(Hospital.class, id);
        hospital.setAddress(newHospital.getAddress());
        hospital.setName(newHospital.getName());
        hospital.setImage(newHospital.getImage());
        entityManager.merge(hospital);
    }

    @Override
    public Hospital findById(Long hospitalId) {
        return entityManager.find(Hospital.class, hospitalId);
    }

}
