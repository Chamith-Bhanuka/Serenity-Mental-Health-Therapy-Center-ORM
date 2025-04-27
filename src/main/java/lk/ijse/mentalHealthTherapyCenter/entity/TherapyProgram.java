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
    private String duration;
    private Double fee;
    private String description;

    @ManyToMany(mappedBy = "therapyPrograms")
    private List<Therapist> therapists = new ArrayList<>();

    @OneToMany(mappedBy = "therapyProgram", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Registration> registrations = new ArrayList<>();

    @OneToMany(mappedBy = "therapyProgram", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Session> sessions = new ArrayList<>();
}
