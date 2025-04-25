package lk.ijse.mentalHealthTherapyCenter.dao.custom.impl;

import lk.ijse.mentalHealthTherapyCenter.config.FactoryConfiguration;
import lk.ijse.mentalHealthTherapyCenter.dao.custom.PatientDAO;
import lk.ijse.mentalHealthTherapyCenter.entity.Patient;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Optional;

public class PatientDAOImpl implements PatientDAO {

    private final FactoryConfiguration factoryConfiguration = FactoryConfiguration.getInstance();

    @Override
    public boolean save(Patient patient) {
        Session session = factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();

        try {
            session.persist(patient);
            transaction.commit();
            return true;
        } catch (Exception e) {
            transaction.rollback();
            return false;
        } finally {
            session.close();
        }
    }

    @Override
    public boolean update(Patient patient) {
        Session session = factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();

        try {
            session.merge(patient);
            transaction.commit();
            return true;
        } catch (Exception e) {
            transaction.rollback();
            return false;
        } finally {
            session.close();
        }
    }

    @Override
    public boolean deleteByPk(String pk) {
        Session session = factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();

        try {
            Patient patient = session.get(Patient.class, pk);

            if (patient == null) {
                return false;
            }
            session.remove(patient);
            transaction.commit();
            return true;
        } catch (Exception e) {
            transaction.rollback();
            return false;
        } finally {
            session.close();
        }
    }

    @Override
    public List<Patient> getAll() {
        Session session = factoryConfiguration.getSession();
        Query query = session.createQuery("FROM Patient", Patient.class);
        List<Patient> patients = query.list();
        return patients;
    }

    @Override
    public Optional<Patient> findByPk(String pk) {
        return Optional.empty();
    }

    @Override
    public Optional<String> getLastPk() {
        return Optional.empty();
    }

    @Override
    public boolean save(Patient patient, Session session, Transaction transaction) {
        try {
            System.out.println("Persisting patient: " + patient.getId());
            System.out.println("Patient selected programs: " + patient.getRegistrations());
            session.persist(patient);
            transaction.commit();
            System.out.println("Transaction committed");
            return true;
        } catch (Exception e) {
            transaction.rollback();
            System.out.println("transaction rollback from dao");
            e.printStackTrace();
            return false;
        } finally {
            session.close();
        }
    }
}
