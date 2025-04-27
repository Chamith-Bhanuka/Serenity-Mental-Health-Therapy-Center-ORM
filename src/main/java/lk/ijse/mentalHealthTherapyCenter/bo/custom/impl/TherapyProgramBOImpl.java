package lk.ijse.mentalHealthTherapyCenter.bo.custom.impl;

import lk.ijse.mentalHealthTherapyCenter.bo.custom.TherapyProgramBO;
import lk.ijse.mentalHealthTherapyCenter.dao.DAOFactory;
import lk.ijse.mentalHealthTherapyCenter.dao.custom.TherapyProgramDAO;
import lk.ijse.mentalHealthTherapyCenter.dto.TherapyProgramDTO;
import lk.ijse.mentalHealthTherapyCenter.entity.TherapyProgram;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TherapyProgramBOImpl implements TherapyProgramBO {

    TherapyProgramDAO dao = DAOFactory.getInstance().getDAO(DAOFactory.DAOType.TherapyProgram);

    @Override
    public boolean save(TherapyProgramDTO therapyProgramDTO) {
        TherapyProgram therapyProgram = new TherapyProgram();

        therapyProgram.setProgramId(therapyProgramDTO.getProgramId());
        therapyProgram.setProgramName(therapyProgramDTO.getProgramName());
        therapyProgram.setDuration(therapyProgramDTO.getDuration());
        therapyProgram.setFee(therapyProgramDTO.getFee());
        therapyProgram.setDescription(therapyProgramDTO.getDescription());

        return dao.save(therapyProgram);
    }

    @Override
    public boolean update(TherapyProgramDTO therapyProgramDTO) {
        TherapyProgram therapyProgram = new TherapyProgram();

        therapyProgram.setProgramId(therapyProgramDTO.getProgramId());
        therapyProgram.setProgramName(therapyProgramDTO.getProgramName());
        therapyProgram.setDuration(therapyProgramDTO.getDuration());
        therapyProgram.setFee(therapyProgramDTO.getFee());
        therapyProgram.setDescription(therapyProgramDTO.getDescription());

        return dao.update(therapyProgram);
    }

    @Override
    public boolean deleteByPk(String pk) {
        return dao.deleteByPk(pk);
    }

    @Override
    public List<TherapyProgramDTO> getAll() {
        ArrayList<TherapyProgramDTO> therapyProgramDTOArrayList = new ArrayList<>();
        List<TherapyProgram> entityList = dao.getAll();

        for (TherapyProgram therapyProgram : entityList) {
            therapyProgramDTOArrayList.add(new TherapyProgramDTO(
                    therapyProgram.getProgramId(),
                    therapyProgram.getProgramName(),
                    therapyProgram.getDuration(),
                    therapyProgram.getFee(),
                    therapyProgram.getDescription()
            ));
        }
        return therapyProgramDTOArrayList;
    }

    @Override
    public String generateNewID() {
        Optional<String> lastPk = dao.getLastPk();
        String lastId = lastPk.orElse("TP001");

        if (lastId.startsWith("TP")) {

            String numericPart = lastId.substring(2);

            try {
                int numericId = Integer.parseInt(numericPart);

                int newIdIndex = numericId + 1;

                return String.format("TP%03d", newIdIndex);
            } catch (NumberFormatException e) {
                System.out.println("Invalid last id format");
                return "TP000";
            }
        }
        return "TP001";
    }

    @Override
    public List<TherapyProgram> getTherapyProgramsBySelectedIds(Session session, List<String> selectedIds) {
        return dao.getTherapyProgramsBySelectedIds(session, selectedIds);
    }

    @Override
    public double calculateTotalFee(List<TherapyProgram> therapyPrograms) {
        if (therapyPrograms == null || therapyPrograms.isEmpty()) {
            return 0.0;
        }
        return therapyPrograms.stream()
                .mapToDouble(TherapyProgram::getFee)
                .sum();
    }

    @Override
    public TherapyProgram findByPk(String pk) {
        return dao.findByPk(pk).orElse(null);
    }

}
