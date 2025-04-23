package lk.ijse.mentalHealthTherapyCenter.bo.custom;

import lk.ijse.mentalHealthTherapyCenter.bo.SuperBO;
import lk.ijse.mentalHealthTherapyCenter.dto.TherapistDTO;

import java.util.List;

public interface TherapistBO extends SuperBO {
    boolean save(TherapistDTO therapistDTO);
    boolean update(TherapistDTO therapistDTO);
    boolean deleteByPk(String pk);
    List<TherapistDTO> getAll();
}
