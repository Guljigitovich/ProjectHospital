package project.service.interfaceService;

import project.model.Appointment;

import java.util.List;

public interface AppointmentService {
    void saveAppointment(Long hospitalId,Appointment appointment);

    void deleteByIdAppointment(Long hospitalId, Long appointmentId);
    void updateAppointment(Long id,Appointment newAppointment);
    Appointment findById(Long appointmentId);

    List<Appointment>getAllAppointment(Long id);


}
