package lk.ijse.mentalHealthTherapyCenter.view.tdm;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class PatientTM {
    private int id;
    private String name;
    private String email;
    private String phone;
    private String address;
    private String gender;
    private int age;
    private String medicalHistory;
//    private List<Registration> registrationList;
}
