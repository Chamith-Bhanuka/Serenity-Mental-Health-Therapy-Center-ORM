package lk.ijse.mentalHealthTherapyCenter.dao.custom.impl;

import lk.ijse.mentalHealthTherapyCenter.config.FactoryConfiguration;
import lk.ijse.mentalHealthTherapyCenter.dao.QueryDAO;
import lk.ijse.mentalHealthTherapyCenter.entity.Patient;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class QueryDAOImpl implements QueryDAO {

    FactoryConfiguration factoryConfiguration = FactoryConfiguration.getInstance();

    @Override
    public List<Patient> getPatientsEnrolledInAllTherapyPrograms() {


        Session session = factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();
        List<Patient> patients = new ArrayList<>();

        try {
            transaction = session.beginTransaction();

            String hql = "select p " +
                    "from Patient p " +
                    "where (select count(distinct r.therapyProgram.id) " +
                    "       from Registration r " +
                    "       where r.patient = p) = " +
                    "      (select count(tp.id) from TherapyProgram tp)";

            Query<Patient> query = session.createQuery(hql, Patient.class);
            patients = query.getResultList();

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }

        return patients;
    }

    @Override
    public List<Patient> getPatientsWithEnrolledTherapyPrograms() {
        Session session = factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();

        List<Patient> patients = new ArrayList<>();

        try {

            String hql = "select distinct p " +
                    "from Patient p " +
                    "join fetch p.registrations r " +
                    "join fetch r.therapyProgram tp";

            Query<Patient> query = session.createQuery(hql, Patient.class);
            patients = query.getResultList();

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }

        return patients;
    }
}
