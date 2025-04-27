package lk.ijse.mentalHealthTherapyCenter.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Embeddable
@Getter
@Setter
@EqualsAndHashCode
public class RegistrationId implements SuperEntity {
    @Column(name = "patient_id")
    private int patientId;

    @Column(name = "therapy_program_id")
    private String therapyProgramId;
}
