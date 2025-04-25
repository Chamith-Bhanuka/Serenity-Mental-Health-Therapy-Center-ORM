package lk.ijse.mentalHealthTherapyCenter.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "registration")
public class Registration implements SuperEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate registrationDate;

    //link to the patient making the registration
    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    //link to the therapy program
    @ManyToOne
    @JoinColumn(name = "program_id", nullable = false)
    private TherapyProgram therapyProgram;

    //One-to-one relationship with payment to track the upfront payment for this registration
    @OneToOne(mappedBy = "registration", cascade = CascadeType.ALL)
    private Payment payment;

}
