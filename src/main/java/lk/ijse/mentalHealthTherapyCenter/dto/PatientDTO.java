package lk.ijse.mentalHealthTherapyCenter.dto;

import lk.ijse.mentalHealthTherapyCenter.entity.Registration;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class PatientDTO {
    private int id;
    private String name;
    private String email;
    private String phone;
    private String address;
    private String gender;
    private int age;
    private String medicalHistory;
    private List<Registration> registrationList;
}
