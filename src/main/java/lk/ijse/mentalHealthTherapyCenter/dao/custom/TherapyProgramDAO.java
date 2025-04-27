package lk.ijse.mentalHealthTherapyCenter.dao.custom;

import lk.ijse.mentalHealthTherapyCenter.dao.CrudDAO;
import lk.ijse.mentalHealthTherapyCenter.entity.TherapyProgram;
import org.hibernate.Session;

import java.util.List;

public interface TherapyProgramDAO  extends CrudDAO<TherapyProgram,String> {
    List<TherapyProgram> getTherapyProgramsBySelectedIds(Session session, List<String> selectedIds);
}
