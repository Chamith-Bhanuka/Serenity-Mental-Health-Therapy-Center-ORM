package lk.ijse.mentalHealthTherapyCenter.dao;

import lk.ijse.mentalHealthTherapyCenter.dao.custom.impl.PatientDAOImpl;
import lk.ijse.mentalHealthTherapyCenter.dao.custom.impl.TherapistDAOImpl;
import lk.ijse.mentalHealthTherapyCenter.dao.custom.impl.TherapyProgramDAOImpl;
import lk.ijse.mentalHealthTherapyCenter.dao.custom.impl.UserDAOImpl;
import lombok.NoArgsConstructor;

@NoArgsConstructor

public class DAOFactory {
    private static DAOFactory daoFactory;

    public static DAOFactory getInstance() {
        return daoFactory == null ? daoFactory = new DAOFactory() : daoFactory;
    }

    public enum DAOType {
        User, TherapyProgram, Patient, Therapist
    }

    @SuppressWarnings("unchecked")
    public <T extends SuperDAO> T getDAO(DAOType type) {
        return switch (type) {
            case User -> (T) new UserDAOImpl();
            case TherapyProgram -> (T) new TherapyProgramDAOImpl();
            case Patient -> (T) new PatientDAOImpl();
            case Therapist -> (T) new TherapistDAOImpl();
        };
    }
}
