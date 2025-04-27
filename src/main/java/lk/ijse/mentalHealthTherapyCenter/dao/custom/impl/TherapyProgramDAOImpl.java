package lk.ijse.mentalHealthTherapyCenter.dao.custom.impl;

import lk.ijse.mentalHealthTherapyCenter.config.FactoryConfiguration;
import lk.ijse.mentalHealthTherapyCenter.dao.custom.TherapyProgramDAO;
import lk.ijse.mentalHealthTherapyCenter.entity.Therapist;
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

            for (Therapist therapist : new ArrayList<>(therapyProgram.getTherapists())) {
                // Remove the program from the therapist's list.
                therapist.getTherapyPrograms().remove(therapyProgram);
                session.merge(therapist);
            }


            // clear all associations
            therapyProgram.getTherapists().clear();
            session.merge(therapyProgram);
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
        Session session = factoryConfiguration.getSession();

        try {
            TherapyProgram program = session.find(TherapyProgram.class, pk);
            return Optional.ofNullable(program);
        } finally {
            session.close();
        }
    }

    @Override
    public Optional<String> getLastPk() {
        Session session = factoryConfiguration.getSession();
        try {
            List <String> result = session.createQuery(
                    "SELECT tp.programId FROM TherapyProgram tp ORDER BY tp.programId DESC",
                    String.class)
                    .setMaxResults(1)
                    .getResultList();

            if (result.isEmpty()) {
                return Optional.empty();
            } else {
                return Optional.of(result.get(0));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            session.close();
        }
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
