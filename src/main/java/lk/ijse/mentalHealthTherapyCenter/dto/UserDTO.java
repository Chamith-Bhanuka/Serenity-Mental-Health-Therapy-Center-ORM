package lk.ijse.mentalHealthTherapyCenter.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserDTO {
    private int id;
    private String name;
    private String password;
    private String role;
    private String contact;
    private String email;
}
