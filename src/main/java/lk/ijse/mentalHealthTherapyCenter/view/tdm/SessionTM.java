package lk.ijse.mentalHealthTherapyCenter.view.tdm;

import lk.ijse.mentalHealthTherapyCenter.entity.Patient;
import lk.ijse.mentalHealthTherapyCenter.entity.Therapist;
import lk.ijse.mentalHealthTherapyCenter.entity.TherapyProgram;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class SessionTM {
    private int id;
    private LocalDate sessionDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private String status;
    private Patient patient;
    private Therapist therapist;
    private TherapyProgram therapyProgram;
}
