package lk.ijse.mentalHealthTherapyCenter.bo;

import lk.ijse.mentalHealthTherapyCenter.bo.custom.impl.PatientBOImpl;
import lk.ijse.mentalHealthTherapyCenter.bo.custom.impl.TherapistBOImpl;
import lk.ijse.mentalHealthTherapyCenter.bo.custom.impl.TherapyProgramBOImpl;
import lk.ijse.mentalHealthTherapyCenter.bo.custom.impl.UserBOImpl;
import lombok.NoArgsConstructor;

@NoArgsConstructor

public class BOFactory {

    private static BOFactory boFactory;

    public static BOFactory getInstance() {
        return boFactory==null?boFactory=new BOFactory():boFactory;
    }

    public enum BOType {
        User, TherapyProgram, Patient, Therapist
    }

    public SuperBO getBO(BOFactory.BOType type) {
        return switch (type) {
            case User -> new UserBOImpl();
            case TherapyProgram -> new TherapyProgramBOImpl();
            case Patient -> new PatientBOImpl();
            case Therapist -> new TherapistBOImpl();
        };
    }
}
