package lk.ijse.mentalHealthTherapyCenter.bo.custom;

import lk.ijse.mentalHealthTherapyCenter.bo.SuperBO;
import lk.ijse.mentalHealthTherapyCenter.dto.TherapistDTO;
import lk.ijse.mentalHealthTherapyCenter.entity.Session;
import lk.ijse.mentalHealthTherapyCenter.entity.Therapist;
import lk.ijse.mentalHealthTherapyCenter.entity.TherapyProgram;

import java.util.List;

public interface TherapistBO extends SuperBO {
    boolean save(TherapistDTO therapistDTO);
    boolean update(TherapistDTO therapistDTO);
    boolean deleteByPk(String pk);
    List<TherapistDTO> getAll();
    void assignTherapyProgram(int therapistId, TherapyProgram therapyProgram);
    List<Session> getTherapistSchedule(int therapistId);
    List<TherapyProgram> getTherapyProgramsBySelectedIds(org.hibernate.Session session, List<String> selectedIds);
    boolean save(TherapistDTO therapistDTO, List<String> selectedIdList);
    boolean update(TherapistDTO therapistDTO, List<String> selectedIdList);
    List<TherapistDTO> getTherapistsByProgramId(String programId);
    Therapist findByPk(String pk);
}
