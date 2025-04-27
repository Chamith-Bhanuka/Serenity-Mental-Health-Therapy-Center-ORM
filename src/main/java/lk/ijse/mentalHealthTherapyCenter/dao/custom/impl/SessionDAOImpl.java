package lk.ijse.mentalHealthTherapyCenter.dao.custom.impl;

import lk.ijse.mentalHealthTherapyCenter.config.FactoryConfiguration;
import lk.ijse.mentalHealthTherapyCenter.dao.custom.SessionDAO;
import lk.ijse.mentalHealthTherapyCenter.entity.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Optional;

public class SessionDAOImpl implements SessionDAO {

    private final FactoryConfiguration factoryConfiguration = FactoryConfiguration.getInstance();

    @Override
    public boolean save(Session sessionEntity) {
        org.hibernate.Session session = factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();

        try {
            session.persist(sessionEntity);
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
    public boolean update(Session sessionEntity) {
        org.hibernate.Session session = factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();

        try {
            session.merge(sessionEntity);
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
        org.hibernate.Session session = factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();

        try {
            Session sessionEntity = session.get(Session.class, pk);
            if (sessionEntity != null) {
                session.remove(sessionEntity);
            }
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
    public List<Session> getAll() {
        org.hibernate.Session session = factoryConfiguration.getSession();
        try {
            return session.createQuery("from Session").getResultList();
        } finally {
            session.close();
        }
    }

    @Override
    public Optional<Session> findByPk(String pk) {
        org.hibernate.Session session = factoryConfiguration.getSession();
        try {
            return Optional.ofNullable(session.get(Session.class, pk));
        } finally {
            session.close();
        }
    }

    @Override
    public Optional<String> getLastPk() {
        return Optional.empty();
    }

    @Override
    public List<Session> getSessionsByTherapist(int therapistId) {
        org.hibernate.Session session = factoryConfiguration.getSession();
        try {
            Query<Session> query = session.createQuery(
                    "FROM Session s WHERE s.therapist.id = :therapistId", Session.class);
            query.setParameter("therapistId", therapistId);
            return query.getResultList();
        } finally {
            session.close();
        }
    }

    @Override
    public List<Session> getSessionsByPatient(int patientId) {
        org.hibernate.Session session = factoryConfiguration.getSession();
        try {
            Query<Session> query = session.createQuery(
                    "FROM Session s WHERE s.patient.id = :patientId", Session.class);
            query.setParameter("patientId", patientId);
            return query.getResultList();
        } finally {
            session.close();
        }
    }
}
