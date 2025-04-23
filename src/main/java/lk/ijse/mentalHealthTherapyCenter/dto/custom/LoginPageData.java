package lk.ijse.mentalHealthTherapyCenter.dto.custom;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class LoginPageData {

    private static LoginPageData instance;

    private String username;

    public static LoginPageData getInstance() {
        return instance == null ? instance = new LoginPageData() : instance;
    }

}
