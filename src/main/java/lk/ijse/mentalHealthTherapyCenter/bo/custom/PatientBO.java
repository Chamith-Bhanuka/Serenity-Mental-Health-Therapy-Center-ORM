package lk.ijse.mentalHealthTherapyCenter.bo.custom;

import lk.ijse.mentalHealthTherapyCenter.bo.SuperBO;
import lk.ijse.mentalHealthTherapyCenter.dto.PatientDTO;

import java.util.List;

public interface PatientBO extends SuperBO {
    boolean save(PatientDTO patientDTO, List<String> selectedIdList);
    boolean update(PatientDTO patientDTO);
    boolean deleteByPk(String pk);
    List<PatientDTO> getAll();
}
