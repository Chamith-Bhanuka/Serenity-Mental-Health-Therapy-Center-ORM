package lk.ijse.mentalHealthTherapyCenter.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "therapy_program")
public class TherapyProgram implements SuperEntity{
    @Id
    private String programId;

    private String programName;
    private String duration;
    private Double fee;

//    @OneToMany(mappedBy = "therapy_program", cascade = CascadeType.ALL)
//    private List<Registration> registrationList;

    @OneToMany(mappedBy = "program", cascade = CascadeType.ALL)
    private List<Session> sessions;
}
