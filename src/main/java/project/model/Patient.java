package project.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.enums.Gender;

import java.util.ArrayList;
import java.util.List;
@Entity
@Table(name = "Patients")
@Getter
@Setter
@NoArgsConstructor

public class Patient {
    @Id
    @SequenceGenerator(name = "patient_gen",sequenceName = "patient_seq",allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "patient_gen")
    private Long id;
    @Column(name = "first_name", nullable = false)
    private String firstName;
    @Column(name = "last_name",nullable = false)
    private String lastName;
    @Column(name = "phone_number",nullable = false)
    private String phoneNumber;
    @Column(length = 1000)
    private String image;
    private Gender gender;
    @Column(unique = true)
    private String email;
    @ManyToOne(cascade = {CascadeType.MERGE,CascadeType.DETACH,CascadeType.PERSIST,CascadeType.REFRESH})
    private Hospital hospital;
    @OneToMany(mappedBy = "patient",cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Appointment>appointments;
    @Transient
    private Long hospitalId;
    public void addAppointment(Appointment appointment){
        if (appointments == null){
            appointments = new ArrayList<>();
        }
        appointments.add(appointment);
    }

    public Patient(String firstName, String lastName, String phoneNumber, Gender gender, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.email = email;
    }
}
