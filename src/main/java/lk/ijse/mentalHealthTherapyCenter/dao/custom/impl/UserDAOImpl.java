package lk.ijse.mentalHealthTherapyCenter.dao.custom.impl;

import lk.ijse.mentalHealthTherapyCenter.config.FactoryConfiguration;
import lk.ijse.mentalHealthTherapyCenter.dao.custom.UserDAO;
import lk.ijse.mentalHealthTherapyCenter.entity.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Optional;

public class UserDAOImpl implements UserDAO {

    private final FactoryConfiguration factoryConfiguration = FactoryConfiguration.getInstance();

    @Override
    public boolean save(User user) {
        Session session = factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();

        try {
            session.persist(user);
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
    public boolean update(User user) {
        Session session = factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();

        try {
            session.merge(user);
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
            User user = session.get(User.class, pk);
            if (user == null) {
                return false;
            }
            session.remove(user);
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
    public List<User> getAll() {
        Session session = factoryConfiguration.getSession();
        Query query = session.createQuery("from User", User.class);
        List<User> list = query.list();
        return list;
    }

    @Override
    public Optional<User> findByPk(String pk) {
        return Optional.empty();
    }

    @Override
    public Optional<String> getLastPk() {
        return Optional.empty();
    }

    @Override
    public String getRoleByUsername(String username) {
        Session session = factoryConfiguration.getSession();
        String role = null;

        try {
            Query<String> query = session.createQuery(
              "SELECT u.role FROM User u WHERE u.name = :username", String.class
            );
            query.setParameter("username", username);

            role = query.uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return role;
    }
}
