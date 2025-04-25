package lk.ijse.mentalHealthTherapyCenter.bo.custom.impl;

import lk.ijse.mentalHealthTherapyCenter.bo.custom.PatientBO;
import lk.ijse.mentalHealthTherapyCenter.config.FactoryConfiguration;
import lk.ijse.mentalHealthTherapyCenter.dao.DAOFactory;
import lk.ijse.mentalHealthTherapyCenter.dao.custom.PatientDAO;
import lk.ijse.mentalHealthTherapyCenter.dao.custom.TherapyProgramDAO;
import lk.ijse.mentalHealthTherapyCenter.dto.PatientDTO;
import lk.ijse.mentalHealthTherapyCenter.entity.Patient;
import lk.ijse.mentalHealthTherapyCenter.entity.Registration;
import lk.ijse.mentalHealthTherapyCenter.entity.RegistrationId;
import lk.ijse.mentalHealthTherapyCenter.entity.TherapyProgram;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class PatientBOImpl implements PatientBO {

    PatientDAO dao = DAOFactory.getInstance().getDAO(DAOFactory.DAOType.Patient);
    TherapyProgramDAO therapyProgramDAO = DAOFactory.getInstance().getDAO(DAOFactory.DAOType.TherapyProgram);

    @Override
    public boolean save(PatientDTO patientDTO, List<String> selectedIdList) {

//        Session session = FactoryConfiguration.getInstance().getSession();
//        Transaction transaction = session.beginTransaction();
//
//        try {
//
//            Patient patient = new Patient();
//
////            patient.setId(patientDTO.getId());
//            patient.setName(patientDTO.getName());
//            patient.setEmail(patientDTO.getEmail());
//            patient.setPhone(patientDTO.getPhone());
//            patient.setAddress(patientDTO.getAddress());
//            patient.setGender(patientDTO.getGender());
//            patient.setAge(patientDTO.getAge());
//            patient.setMedicalHistory(patientDTO.getMedicalHistory());
//            patient.setRegistrationList(patientDTO.getRegistrationList());
//
//            //get registration list
//            List<Registration> registrations = new ArrayList<>();
//            patient.setRegistrationList(registrations);
//
//            List<TherapyProgram> selectedPrograms = therapyProgramDAO.getTherapyProgramsBySelectedIds(session, selectedIdList);
//            System.out.println("Here is therapy programs selected by patient: " + selectedPrograms);
//
//            for (TherapyProgram therapyProgram : selectedPrograms) {
//                Registration registration = new Registration();
//                RegistrationId regId = new RegistrationId();
//                regId.setPatientId(patient.getId());
//                regId.setTherapyProgramId(therapyProgram.getProgramId());
//                registration.setId(regId);
//                registration.setDate(Date.valueOf("2025-05-01"));
//                registration.setPayment(therapyProgram.getFee());
//                registration.setPatient(patient);
//                registration.setTherapy_program(therapyProgram);
//                registrations.add(registration);
//
//                // STEP 3: Persist the patient and cascade save the registrations
////                session.persist(patient);
////                session.getTransaction().commit();
//            }
//
//            return dao.save(patient, session, transaction);
//
//        } catch (Exception e) {
//            transaction.rollback();
//            System.out.println("<---- Unable to save patient ---->");
//            return false;
//
//        } finally {
////            session.close();
//            System.out.println("I'm here");
//        }
        return true;

    }

    @Override
    public boolean update(PatientDTO patientDTO, List<String> selectedIdList) {
        Patient patient = new Patient();

        patient.setId(patientDTO.getId());
        patient.setName(patientDTO.getName());
        patient.setEmail(patientDTO.getEmail());
        patient.setPhone(patientDTO.getPhone());
        patient.setAddress(patientDTO.getAddress());
        patient.setGender(patientDTO.getGender());
        patient.setAge(patientDTO.getAge());
        patient.setMedicalHistory(patientDTO.getMedicalHistory());
        patient.setRegistrations(patientDTO.getRegistrationList());

        return dao.update(patient);
    }

    @Override
    public boolean deleteByPk(String pk) {
        return dao.deleteByPk(pk);
    }

    @Override
    public List<PatientDTO> getAll() {
        ArrayList<PatientDTO> patientDTOArrayList = new ArrayList<>();
        List<Patient> entityList = dao.getAll();

        for (Patient patient : entityList) {
            patientDTOArrayList.add(new PatientDTO(
                    patient.getId(),
                    patient.getName(),
                    patient.getEmail(),
                    patient.getPhone(),
                    patient.getAddress(),
                    patient.getGender(),
                    patient.getAge(),
                    patient.getMedicalHistory(),
                    patient.getRegistrations()
            ));
        }
        return patientDTOArrayList;
    }
}
