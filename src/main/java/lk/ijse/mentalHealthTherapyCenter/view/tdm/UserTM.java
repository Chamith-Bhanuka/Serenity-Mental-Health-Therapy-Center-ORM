package lk.ijse.mentalHealthTherapyCenter.view.tdm;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class UserTM {
    private int id;
    private String name;
    private String password;
    private String role;
    private String contact;
    private String email;
}
