package lk.ijse.mentalHealthTherapyCenter.view.tdm;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class TherapyProgramTM {
    private String programId;
    private String programName;
    private String duration;
    private Double fee;
}
