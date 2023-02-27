package project.model;

import com.sun.istack.NotNull;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.print.Doc;
import java.time.LocalDate;
@Entity
@Table(name = "Appointments")
@NoArgsConstructor
@Getter
@Setter

public class Appointment {
    @Id
    @SequenceGenerator(name = "appointment_gen",sequenceName = "appointment_seq",allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "appointment_gen")
    private Long id;
    private LocalDate date;
    @ManyToOne(cascade = {CascadeType.REFRESH,CascadeType.MERGE,CascadeType.DETACH,CascadeType.PERSIST},fetch = FetchType.EAGER)
    private Patient patient;
    @ManyToOne(cascade = {CascadeType.DETACH,CascadeType.PERSIST,CascadeType.MERGE,CascadeType.REFRESH})
    private Doctor doctor;
    @ManyToOne(cascade = {CascadeType.MERGE,CascadeType.DETACH,CascadeType.PERSIST,CascadeType.REFRESH})
    private Department department;

    public Appointment(LocalDate date) {
        this.date = date;
    }
    @Transient
    private Long doctorId;
    @Transient
    private  Long departmentId;
    @Transient
    private Long patientId;



}
