package lk.ijse.mentalHealthTherapyCenter.dao.custom.impl;

import lk.ijse.mentalHealthTherapyCenter.config.FactoryConfiguration;
import lk.ijse.mentalHealthTherapyCenter.dao.custom.TherapistDAO;
import lk.ijse.mentalHealthTherapyCenter.dto.TherapistDTO;
import lk.ijse.mentalHealthTherapyCenter.entity.Therapist;
import lk.ijse.mentalHealthTherapyCenter.entity.TherapyProgram;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Optional;

public class TherapistDAOImpl implements TherapistDAO {

    FactoryConfiguration factoryConfiguration = FactoryConfiguration.getInstance();

    @Override
    public boolean save(Therapist therapist) {
        return false;
    }

    @Override
    public boolean update(Therapist therapist) {
        Session session = factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();

        try {
            session.merge(therapist);
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
            Therapist therapist = session.get(Therapist.class, pk);

            if (therapist == null) {
                return false;
            }
            session.remove(therapist);
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
    public List<Therapist> getAll() {
        Session session = factoryConfiguration.getSession();
        Query query = session.createQuery("FROM Therapist", Therapist.class);
        List<Therapist> therapists = query.list();
        return therapists;
    }

    @Override
    public Optional<Therapist> findByPk(String pk) {
        Session session = factoryConfiguration.getSession();
        try {
            Therapist therapist = session.find(Therapist.class, pk);
            return Optional.ofNullable(therapist);
        } finally {
            session.close();
        }
    }

    @Override
    public Optional<String> getLastPk() {
        return Optional.empty();
    }


    //new

    @Override
    public Therapist getTherapistById(int therapistId) {
        Session session = factoryConfiguration.getSession();

        try {
            return session.find(Therapist.class, therapistId);
        } finally {
            session.close();
        }
    }

    @Override
    public void assignTherapyProgramToTherapist(int therapistId, TherapyProgram therapyProgram) {
        Session session = factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();

        try {
            transaction.begin();
            Therapist therapist = session.get(Therapist.class, therapistId);

            if (therapist != null) {
                therapist.getTherapyPrograms().add(therapyProgram);
                session.merge(therapist);
            }
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            System.out.println("Unable to assign therapist");
            e.printStackTrace();
        } finally {
            session.close();
        }

    }

    @Override
    public List<lk.ijse.mentalHealthTherapyCenter.entity.Session> getTherapistSchedule(int therapistId) {
        Session session = factoryConfiguration.getSession();

        try {
            String hql = "FROM Session s WHERE s.therapist.id = :therapistId";
            return session.createQuery(hql, lk.ijse.mentalHealthTherapyCenter.entity.Session.class)
                    .setParameter("therapistId", therapistId)
                    .getResultList();
        } finally {
            session.close();
        }
    }

    @Override
    public boolean save(Session session, Therapist therapist) {
        session = factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();

        try {
            session.persist(therapist);
            transaction.commit();
            return true;
        } catch (Exception e) {
            transaction.rollback();
            System.out.println("Unable to save therapist");
            e.printStackTrace();
            return false;
        } finally {
            session.close();
        }
    }

    @Override
    public boolean update(Session session, Therapist therapist) {
        session = factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();

        try {
            session.merge(therapist);
            transaction.commit();
            return true;
        } catch (Exception e) {
            transaction.rollback();
            System.out.println("Unable to update therapist");
            e.printStackTrace();
            return false;
        } finally {
            session.close();
        }
    }

    @Override
    public List<Therapist> getTherapistsByProgramId(String programId) {
        Session session = factoryConfiguration.getSession();

        try {
            String sql = "SELECT t.* FROM therapist t " +
                    "INNER JOIN therapist_program tp ON t.therapist_id = tp.therapist_id " +
                    "WHERE tp.program_id = :programId";
            Query<Therapist> query = session.createNativeQuery(sql, Therapist.class);
            query.setParameter("programId", programId);
            return query.getResultList();
        } finally {
            session.close();
        }
    }
}
