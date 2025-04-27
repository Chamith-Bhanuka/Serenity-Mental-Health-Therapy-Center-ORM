package lk.ijse.mentalHealthTherapyCenter.dao;

import lk.ijse.mentalHealthTherapyCenter.entity.Patient;

import java.util.List;

public interface QueryDAO extends SuperDAO {
    List<Patient> getPatientsEnrolledInAllTherapyPrograms();
    List<Patient> getPatientsWithEnrolledTherapyPrograms();
}