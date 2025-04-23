package lk.ijse.mentalHealthTherapyCenter.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "registration")
public class Registration {

    @EmbeddedId
    private RegistrationId id;

    private Date date;
    private Double payment;

    @ManyToOne
    @MapsId("patientId")
    @JoinColumn(name = "patient_id", referencedColumnName = "patient_id")
    private Patient patient;

    @ManyToOne
    @MapsId("therapyProgramId")
    @JoinColumn(name = "program_id", referencedColumnName = "programId")
    private TherapyProgram therapy_program;

}
