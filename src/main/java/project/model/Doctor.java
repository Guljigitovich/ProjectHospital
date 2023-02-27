package project.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
@Entity
@Table(name = "Doctors")
@Getter
@Setter
@NoArgsConstructor

public class Doctor {
    @Id
    @SequenceGenerator(name = "doctor_gen",sequenceName = "doctor_seq",allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "doctor_gen")
    private Long id;
    @Column(name = "first_name", nullable = false)
    private String firstName;
    @Column(name = "last_name",nullable = false)
    private String lastName;
    private String position;
    @Column(length = 1000)
    private String image;
    @Column(unique = true)
    private String email;
    @ManyToMany(mappedBy = "doctors",cascade = {CascadeType.MERGE,CascadeType.DETACH,CascadeType.PERSIST,CascadeType.REFRESH})
    private List<Department>departments;

    public void  addDepartment(Department department){
        if (departments == null){
            departments = new ArrayList<>();
        }
        departments.add(department);
    }
    public void addAppointment(Appointment appointment){
        if (appointments == null){
            appointments = new ArrayList<>();
        }
        appointments.add(appointment);
    }
    @OneToMany(mappedBy = "doctor",cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Appointment>appointments;
    @ManyToOne(cascade = {CascadeType.MERGE,CascadeType.DETACH,CascadeType.PERSIST,CascadeType.REFRESH})
    private Hospital hospital;
    @Transient
    private Long departmentId;

    public Doctor(String firstName, String lastName, String position, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.position = position;
        this.email = email;
    }
}
