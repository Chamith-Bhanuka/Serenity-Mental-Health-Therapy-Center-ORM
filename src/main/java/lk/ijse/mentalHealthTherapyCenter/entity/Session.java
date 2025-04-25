package lk.ijse.mentalHealthTherapyCenter.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "session")

public class Session implements SuperEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    // Date of the appointment
    private LocalDate sessionDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private String status; // scheduled, canceled, completed

    // the patient participating in this session
    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    // the therapist assigned to this session
    @ManyToOne
    @JoinColumn(name = "therapist_id", nullable = false)
    private Therapist therapist;

    // the therapy program related to this session
    @ManyToOne
    @JoinColumn(name = "program_id" , nullable = false)
    private TherapyProgram therapyProgram;

}
