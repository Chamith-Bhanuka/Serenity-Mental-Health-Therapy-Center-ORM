package lk.ijse.mentalHealthTherapyCenter.dto;

import lk.ijse.mentalHealthTherapyCenter.entity.Patient;
import lk.ijse.mentalHealthTherapyCenter.entity.Session;
import lk.ijse.mentalHealthTherapyCenter.entity.Therapist;
import lk.ijse.mentalHealthTherapyCenter.entity.TherapyProgram;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class SessionDTO {
    private int id;
    private LocalDate sessionDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private String status;
    private Patient patient;
    private Therapist therapist;
    private TherapyProgram therapyProgram;

    public SessionDTO(Session session) {
        this.id = session.getId();
        this.sessionDate = session.getSessionDate();
        this.startTime = session.getStartTime();
        this.endTime = session.getEndTime();
        this.status = session.getStatus();
        this.patient = session.getPatient();
        this.therapist = session.getTherapist();
        this.therapyProgram = session.getTherapyProgram();
    }

}
