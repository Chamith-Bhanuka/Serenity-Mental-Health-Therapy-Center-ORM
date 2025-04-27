package lk.ijse.mentalHealthTherapyCenter.dto;

import lk.ijse.mentalHealthTherapyCenter.entity.Patient;
import lk.ijse.mentalHealthTherapyCenter.entity.Payment;
import lk.ijse.mentalHealthTherapyCenter.entity.TherapyProgram;
import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class RegistrationDTO {
    private Long id;
    private LocalDate registrationDate;
    private Patient patient;
    private TherapyProgram therapyProgram;
    private Payment payment;
}
