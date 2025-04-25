package lk.ijse.mentalHealthTherapyCenter.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString

public class TherapyProgramDTO {
    private String programId;
    private String programName;
    private String duration;
    private Double fee;
    private String description;
}
