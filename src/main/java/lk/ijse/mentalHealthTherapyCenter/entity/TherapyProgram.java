package lk.ijse.mentalHealthTherapyCenter.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "therapy_program")
public class TherapyProgram implements SuperEntity{
    @Id
    @Column(name = "program_id", nullable = false)
    private String programId;

    private String programName;
    private String duration; // weeks, months
    private Double fee;
    private String description; //details about the program

    @ManyToMany(mappedBy = "therapyPrograms")
    private List<Therapist> therapists = new ArrayList<>();

    // registrations for the program
    @OneToMany(mappedBy = "therapyProgram", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Registration> registrations = new ArrayList<>();

    // sessions schedule under this program
    @OneToMany(mappedBy = "therapyProgram", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Session> sessions = new ArrayList<>();
}
