package lk.ijse.mentalHealthTherapyCenter.bo.custom.impl;

import lk.ijse.mentalHealthTherapyCenter.bo.custom.TherapistBO;
import lk.ijse.mentalHealthTherapyCenter.bo.custom.TherapyProgramBO;
import lk.ijse.mentalHealthTherapyCenter.config.FactoryConfiguration;
import lk.ijse.mentalHealthTherapyCenter.dao.DAOFactory;
import lk.ijse.mentalHealthTherapyCenter.dao.custom.TherapistDAO;
import lk.ijse.mentalHealthTherapyCenter.dao.custom.TherapyProgramDAO;
import lk.ijse.mentalHealthTherapyCenter.dto.TherapistDTO;
import lk.ijse.mentalHealthTherapyCenter.entity.Session;
import lk.ijse.mentalHealthTherapyCenter.entity.Therapist;
import lk.ijse.mentalHealthTherapyCenter.entity.TherapyProgram;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class TherapistBOImpl implements TherapistBO {

    TherapistDAO dao = DAOFactory.getInstance().getDAO(DAOFactory.DAOType.Therapist);
    TherapyProgramDAO therapyProgramDAO = DAOFactory.getInstance().getDAO(DAOFactory.DAOType.TherapyProgram);

    @Override
    public boolean save(TherapistDTO therapistDTO) {
        return false;
    }

    @Override
    public boolean update(TherapistDTO therapistDTO) {
        Therapist therapist = new Therapist();

        therapist.setId(therapistDTO.getId());
        therapist.setName(therapistDTO.getName());
        therapist.setEmail(therapistDTO.getEmail());
        therapist.setPhone(therapistDTO.getPhone());
        therapist.setSpecialization(therapistDTO.getSpecialization());

        return dao.update(therapist);
    }

    @Override
    public boolean deleteByPk(String pk) {
        return dao.deleteByPk(pk);
    }

    @Override
    public List<TherapistDTO> getAll() {
        ArrayList<TherapistDTO> therapistDTOArrayList = new ArrayList<>();
        List<Therapist> therapists = dao.getAll();

        for (Therapist therapist : therapists) {
            therapistDTOArrayList.add(new TherapistDTO(
                    therapist.getId(),
                    therapist.getName(),
                    therapist.getEmail(),
                    therapist.getPhone(),
                    therapist.getSpecialization(),
                    therapist.getTherapyPrograms()
            ));
        }
        return therapistDTOArrayList;
    }

    @Override
    public void assignTherapyProgram(int therapistId, TherapyProgram therapyProgram) {
        dao.assignTherapyProgramToTherapist(therapistId, therapyProgram);
    }

    @Override
    public List<Session> getTherapistSchedule(int therapistId) {
        return dao.getTherapistSchedule(therapistId);
    }

    @Override
    public List<TherapyProgram> getTherapyProgramsBySelectedIds(org.hibernate.Session session, List<String> selectedIds) {
        return therapyProgramDAO.getTherapyProgramsBySelectedIds(session, selectedIds);
    }

    @Override
    public boolean save(TherapistDTO therapistDTO, List<String> selectedIdList) {
        org.hibernate.Session session  = FactoryConfiguration.getInstance().getSession();

        List<TherapyProgram> therapyPrograms = therapyProgramDAO.getTherapyProgramsBySelectedIds(session,selectedIdList);

        Therapist therapist = new Therapist();

        therapist.setName(therapistDTO.getName());
        therapist.setEmail(therapistDTO.getEmail());
        therapist.setPhone(therapistDTO.getPhone());
        therapist.setSpecialization(therapistDTO.getSpecialization());
        therapist.setTherapyPrograms(therapyPrograms);

        return dao.save(session, therapist);
    }

    @Override
    public boolean update(TherapistDTO therapistDTO, List<String> selectedIdList) {
        org.hibernate.Session session  = FactoryConfiguration.getInstance().getSession();

        List<TherapyProgram> therapyPrograms = therapyProgramDAO.getTherapyProgramsBySelectedIds(session,selectedIdList);

        Therapist therapist = new Therapist();

        therapist.setId(therapistDTO.getId());
        therapist.setName(therapistDTO.getName());
        therapist.setEmail(therapistDTO.getEmail());
        therapist.setPhone(therapistDTO.getPhone());
        therapist.setSpecialization(therapistDTO.getSpecialization());
        therapist.setTherapyPrograms(therapyPrograms);

        return dao.update(session, therapist);
    }
}
