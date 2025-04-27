package lk.ijse.mentalHealthTherapyCenter.dao.custom.impl;

import lk.ijse.mentalHealthTherapyCenter.config.FactoryConfiguration;
import lk.ijse.mentalHealthTherapyCenter.dao.custom.RegistrationDAO;
import lk.ijse.mentalHealthTherapyCenter.entity.Registration;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Optional;

public class RegistrationDAOImpl implements RegistrationDAO {

    private final FactoryConfiguration factoryConfiguration = FactoryConfiguration.getInstance();

    @Override
    public boolean save(Registration registration) {
        System.out.println("registration to dao impl: " + registration.getTherapyProgram());
        Session session = factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();

        try {
            session.persist(registration);
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
    public boolean update(Registration registration) {
        Session session = factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();

        try {
            session.merge(registration);
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
            Registration registration = session.get(Registration.class, pk);

            if (registration == null) {
                return false;
            }
            session.remove(registration);
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
    public List<Registration> getAll() {
        Session session = factoryConfiguration.getSession();
        try {
            return session.createQuery("from Registration", Registration.class).getResultList();
        } finally {
            session.close();
        }
    }

    @Override
    public Optional<Registration> findByPk(String pk) {
        Session session = factoryConfiguration.getSession();
        try {
            return Optional.ofNullable(session.find(Registration.class, pk));
        } finally {
            session.close();
        }
    }

    @Override
    public Optional<String> getLastPk() {
        return Optional.empty();
    }
}
