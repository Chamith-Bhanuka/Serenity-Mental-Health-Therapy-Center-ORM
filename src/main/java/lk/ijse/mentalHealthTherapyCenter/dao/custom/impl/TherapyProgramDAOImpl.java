package lk.ijse.mentalHealthTherapyCenter.dao.custom.impl;

import lk.ijse.mentalHealthTherapyCenter.config.FactoryConfiguration;
import lk.ijse.mentalHealthTherapyCenter.dao.custom.TherapyProgramDAO;
import lk.ijse.mentalHealthTherapyCenter.entity.TherapyProgram;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TherapyProgramDAOImpl implements TherapyProgramDAO {

    private final FactoryConfiguration factoryConfiguration = FactoryConfiguration.getInstance();

    @Override
    public boolean save(TherapyProgram therapyProgram) {
        Session session = factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();

        try {
            session.persist(therapyProgram);
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
    public boolean update(TherapyProgram therapyProgram) {
        Session session = factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();

        try {
            session.merge(therapyProgram);
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
            TherapyProgram therapyProgram = session.get(TherapyProgram.class, pk);

            if (therapyProgram == null) {
                return false;
            }
            session.remove(therapyProgram);
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
    public List<TherapyProgram> getAll() {
        Session session = factoryConfiguration.getSession();
        Query query = session.createQuery("FROM TherapyProgram", TherapyProgram.class);
        List<TherapyProgram> list = query.list();
        return list;
    }

    @Override
    public Optional<TherapyProgram> findByPk(String pk) {
        return Optional.empty();
    }

    @Override
    public Optional<String> getLastPk() {
        return Optional.empty();
    }


    //I add this for get selected therapy program to register patient
    @Override
    public List<TherapyProgram> getTherapyProgramsBySelectedIds(Session session, List<String> selectedIds) {
        List<TherapyProgram> foundPrograms = new ArrayList<>();
        for (String therapyProgramId : selectedIds) {
            TherapyProgram therapyProgram = session.find(TherapyProgram.class, therapyProgramId);
            if (therapyProgram != null) {
                foundPrograms.add(therapyProgram);
            } else {
                System.out.println("Therapy program with id " + therapyProgramId + " not found. Skipping this record.");
            }
        }
        return foundPrograms;
    }



}
