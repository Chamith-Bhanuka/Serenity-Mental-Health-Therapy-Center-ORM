package lk.ijse.mentalHealthTherapyCenter.bo.custom;

import lk.ijse.mentalHealthTherapyCenter.bo.SuperBO;
import lk.ijse.mentalHealthTherapyCenter.dto.PatientDTO;
import lk.ijse.mentalHealthTherapyCenter.entity.Patient;

import java.util.List;
import java.util.Optional;

public interface PatientBO extends SuperBO {
    boolean save(PatientDTO patientDTO, List<String> selectedIdList);
    boolean update(PatientDTO patientDTO, List<String> selectedIdList);
    boolean deleteByPk(String pk);
    List<PatientDTO> getAll();
    boolean save(Patient patient);
    boolean update(PatientDTO patientDTO);
    Patient findByPk(String pk);
}
