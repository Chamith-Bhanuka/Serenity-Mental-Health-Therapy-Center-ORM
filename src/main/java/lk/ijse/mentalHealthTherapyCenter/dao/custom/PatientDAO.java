package lk.ijse.mentalHealthTherapyCenter.dao.custom;

import lk.ijse.mentalHealthTherapyCenter.dao.CrudDAO;
import lk.ijse.mentalHealthTherapyCenter.entity.Patient;
import org.hibernate.Session;
import org.hibernate.Transaction;

public interface PatientDAO extends CrudDAO<Patient, String> {
    boolean save(Patient patient, Session session, Transaction transaction);
}
