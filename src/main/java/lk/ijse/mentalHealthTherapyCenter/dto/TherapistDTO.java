package lk.ijse.mentalHealthTherapyCenter.dto;

import lk.ijse.mentalHealthTherapyCenter.entity.Session;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString

public class TherapistDTO {
    private int id;
    private String name;
    private String email;
    private String phone;
    private String specialization;
    private List<Session> sessions;
}
