package lk.ijse.mentalHealthTherapyCenter.bo.custom;

import lk.ijse.mentalHealthTherapyCenter.bo.SuperBO;
import lk.ijse.mentalHealthTherapyCenter.dto.TherapyProgramDTO;
import lk.ijse.mentalHealthTherapyCenter.dto.UserDTO;
import lk.ijse.mentalHealthTherapyCenter.entity.TherapyProgram;
import org.hibernate.Session;

import java.util.List;
import java.util.Optional;

public interface TherapyProgramBO extends SuperBO {
    boolean save(TherapyProgramDTO therapyProgramDTO);
    boolean update(TherapyProgramDTO therapyProgramDTO);
    boolean deleteByPk(String pk);
    List<TherapyProgramDTO> getAll();
    public String generateNewID();
    public List<TherapyProgram> getTherapyProgramsBySelectedIds(Session session, List<String> selectedIds);
    double calculateTotalFee(List<TherapyProgram> therapyPrograms);
    TherapyProgram findByPk(String pk);
}
