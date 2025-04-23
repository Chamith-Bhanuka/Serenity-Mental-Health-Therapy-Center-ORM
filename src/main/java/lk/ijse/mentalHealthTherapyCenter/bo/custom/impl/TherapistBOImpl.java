package lk.ijse.mentalHealthTherapyCenter.bo.custom.impl;

import lk.ijse.mentalHealthTherapyCenter.bo.custom.TherapistBO;
import lk.ijse.mentalHealthTherapyCenter.dao.DAOFactory;
import lk.ijse.mentalHealthTherapyCenter.dao.custom.TherapistDAO;
import lk.ijse.mentalHealthTherapyCenter.dto.TherapistDTO;
import lk.ijse.mentalHealthTherapyCenter.entity.Therapist;

import java.util.ArrayList;
import java.util.List;

public class TherapistBOImpl implements TherapistBO {

    TherapistDAO dao = DAOFactory.getInstance().getDAO(DAOFactory.DAOType.Therapist);

    @Override
    public boolean save(TherapistDTO therapistDTO) {
        Therapist therapist = new Therapist();

//        therapist.setId(therapistDTO.getId());
        therapist.setName(therapistDTO.getName());
        therapist.setEmail(therapistDTO.getEmail());
        therapist.setPhone(therapistDTO.getPhone());
        therapist.setSpecialization(therapistDTO.getSpecialization());

        return dao.save(therapist);
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
                    therapist.getSessions()
            ));
        }
        return therapistDTOArrayList;
    }
}
