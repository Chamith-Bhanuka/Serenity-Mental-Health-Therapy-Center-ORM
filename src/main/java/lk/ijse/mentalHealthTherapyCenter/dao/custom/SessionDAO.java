package lk.ijse.mentalHealthTherapyCenter.dao.custom;

import lk.ijse.mentalHealthTherapyCenter.dao.CrudDAO;
import lk.ijse.mentalHealthTherapyCenter.entity.Session;

import java.util.List;

public interface SessionDAO extends CrudDAO<Session, String> {
    List<Session> getSessionsByTherapist(int therapistId);
    List<Session> getSessionsByPatient(int patientId);
}
