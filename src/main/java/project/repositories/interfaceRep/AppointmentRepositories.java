package project.repositories.interfaceRep;

import project.model.Appointment;

import java.util.List;

public interface AppointmentRepositories {
    void saveAppointment(Appointment appointment);
    List<Appointment>getAllAppointment(Long id);
    void deleteByIdAppointment(Long id);
    void updateAppointment(Long id,Appointment newAppointment);
    Appointment findById(Long appointmentId);


}
