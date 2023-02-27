package project.repositories.implRep;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import project.model.Appointment;
import project.repositories.interfaceRep.AppointmentRepositories;

import java.util.List;
@Repository
@Transactional
@RequiredArgsConstructor
public class AppointmentRepImpl implements AppointmentRepositories {
  @PersistenceContext
  private final EntityManager entityManager;

    @Override
    public void saveAppointment(Appointment appointment) {
        entityManager.persist(appointment);
    }

    @Override
    public List<Appointment> getAllAppointment(Long id) {
        return entityManager.createQuery("select a from Hospital h join h.appointments a where h.id=:id",
                        Appointment.class)
                .setParameter("id", id)
                .getResultList();
    }

    @Override
    public void deleteByIdAppointment(Long id) {
       entityManager.createQuery("delete from Appointment where id = :id")
               .setParameter("id",id).executeUpdate();
    }

    @Override
    public void updateAppointment(Long id, Appointment newAppointment) {
        Appointment appointment = entityManager.find(Appointment.class, id);
        appointment.setDate(newAppointment.getDate());
        entityManager.merge(appointment);
    }

    @Override
    public Appointment findById(Long appointmentId) {
        return entityManager.find(Appointment.class,appointmentId);
    }
}
