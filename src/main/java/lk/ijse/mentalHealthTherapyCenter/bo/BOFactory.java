package lk.ijse.mentalHealthTherapyCenter.bo;

import lk.ijse.mentalHealthTherapyCenter.bo.custom.impl.*;
import lombok.NoArgsConstructor;

@NoArgsConstructor

public class BOFactory {

    private static BOFactory boFactory;

    public static BOFactory getInstance() {
        return boFactory==null?boFactory=new BOFactory():boFactory;
    }

    public enum BOType {
        User, TherapyProgram, Patient, Therapist, Registration, Payment, Session
    }

    public SuperBO getBO(BOFactory.BOType type) {
        return switch (type) {
            case User -> new UserBOImpl();
            case TherapyProgram -> new TherapyProgramBOImpl();
            case Patient -> new PatientBOImpl();
            case Therapist -> new TherapistBOImpl();
            case Registration -> new RegistrationBOImpl();
            case Payment -> new PaymentBOImpl();
            case Session -> new SessionBOImpl();
        };
    }
}
