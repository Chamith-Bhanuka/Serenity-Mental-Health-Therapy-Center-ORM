package lk.ijse.mentalHealthTherapyCenter.dao.custom;

import lk.ijse.mentalHealthTherapyCenter.dao.CrudDAO;
import lk.ijse.mentalHealthTherapyCenter.entity.Session;
import lk.ijse.mentalHealthTherapyCenter.entity.Therapist;
import lk.ijse.mentalHealthTherapyCenter.entity.TherapyProgram;

import java.util.List;

public interface TherapistDAO extends CrudDAO<Therapist, String> {
    Therapist getTherapistById(int therapistId);
    void assignTherapyProgramToTherapist(int therapistId, TherapyProgram therapyProgram);
    List<Session> getTherapistSchedule(int therapistId);
    boolean save(org.hibernate.Session session, Therapist therapist);
    boolean update(org.hibernate.Session session, Therapist therapist);
}
