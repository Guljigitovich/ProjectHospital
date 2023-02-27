package project.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Entity
@Table(name = "Hospitals")
@NoArgsConstructor
@Getter
@Setter

public class Hospital {
    @Id
    @SequenceGenerator(name = "hospital_gen",sequenceName = "hospital_seq",allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "hospital_gen")
    private Long id;
    private String name;
    private String address;
    private String image;
    @OneToMany(mappedBy = "hospital",cascade = CascadeType.ALL)
    private List<Doctor>doctors;
    @OneToMany(mappedBy = "hospital",cascade = CascadeType.ALL)
    private List<Patient>patients;
    @OneToMany(mappedBy = "hospital",cascade = CascadeType.ALL)
    private List<Department>departments;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Appointment>appointments;

    public Hospital(String name, String address,String image) {
        this.name = name;
        this.address = address;
        this.image = image;
    }

    public void addDepartment(Department department) {
        if (department != null){
            departments.add(department);
        }else {
            throw new NullPointerException();
        }
    }
    public void addAppointment(Appointment appointment){
        if(appointment != null){
            appointments.add(appointment);
        }else {
            throw new NullPointerException();
        }
    }
}
