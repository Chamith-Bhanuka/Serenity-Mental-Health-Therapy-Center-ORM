package lk.ijse.mentalHealthTherapyCenter.bo.custom.impl;

import lk.ijse.mentalHealthTherapyCenter.bo.BOFactory;
import lk.ijse.mentalHealthTherapyCenter.bo.custom.PatientBO;
import lk.ijse.mentalHealthTherapyCenter.bo.custom.SessionBO;
import lk.ijse.mentalHealthTherapyCenter.bo.custom.TherapistBO;
import lk.ijse.mentalHealthTherapyCenter.bo.custom.TherapyProgramBO;
import lk.ijse.mentalHealthTherapyCenter.dao.DAOFactory;
import lk.ijse.mentalHealthTherapyCenter.dao.custom.SessionDAO;
import lk.ijse.mentalHealthTherapyCenter.dto.SessionDTO;
import lk.ijse.mentalHealthTherapyCenter.dto.TherapistDTO;
import lk.ijse.mentalHealthTherapyCenter.entity.Patient;
import lk.ijse.mentalHealthTherapyCenter.entity.Session;
import lk.ijse.mentalHealthTherapyCenter.entity.Therapist;
import lk.ijse.mentalHealthTherapyCenter.entity.TherapyProgram;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SessionBOImpl implements SessionBO {

    SessionDAO sessionDAO = DAOFactory.getInstance().getDAO(DAOFactory.DAOType.Session);
    TherapistBO therapistBO = (TherapistBO) BOFactory.getInstance().getBO(BOFactory.BOType.Therapist);
    TherapyProgramBO therapyProgramBO = (TherapyProgramBO) BOFactory.getInstance().getBO(BOFactory.BOType.TherapyProgram);
    PatientBO patientBO = (PatientBO) BOFactory.getInstance().getBO(BOFactory.BOType.Patient);

    @Override
    public boolean addSession(String programId, String patientId, int therapistId, SessionDTO sessionDTO) {

        LocalDate date = sessionDTO.getSessionDate();
        LocalTime startTime = sessionDTO.getStartTime();
        LocalTime endTime = sessionDTO.getEndTime();
        String status = sessionDTO.getStatus();


        TherapyProgram selectedProgram = therapyProgramBO.findByPk(programId);
        Patient selectedPatient = patientBO.findByPk(patientId);
        Therapist selectedTherapist = therapistBO.findByPk(String.valueOf(therapistId));

        if (date == null || startTime == null || endTime == null || status == null || selectedProgram == null || selectedPatient == null || selectedTherapist == null) {
            System.out.println("return from add session of sessionBOImpl");
            System.out.println("date: " + date);
            System.out.println("startTime: " + startTime);
            System.out.println("endTime: " + endTime);
            System.out.println("status: " + status);
            System.out.println("therapist: " + selectedTherapist);
            System.out.println("selectedProgram: " + selectedProgram);
            System.out.println("selectedPatient: " + selectedPatient);

            return false;
        }

        Session session = new Session();
        session.setSessionDate(date);
        session.setStartTime(startTime);
        session.setEndTime(endTime);
        session.setStatus(status);
        session.setTherapyProgram(selectedProgram);
        session.setPatient(selectedPatient);
        session.setTherapist(selectedTherapist);

        return sessionDAO.save(session);
    }

    @Override
    public boolean updateSession(String programId, String patientId, int therapistId, SessionDTO sessionDTO) {
        int id = sessionDTO.getId();
        LocalDate date = sessionDTO.getSessionDate();
        LocalTime startTime = sessionDTO.getStartTime();
        LocalTime endTime = sessionDTO.getEndTime();
        String status = sessionDTO.getStatus();


        TherapyProgram selectedProgram = therapyProgramBO.findByPk(programId);
        Patient selectedPatient = patientBO.findByPk(patientId);
        Therapist selectedTherapist = therapistBO.findByPk(String.valueOf(therapistId));

        if (date == null || startTime == null || endTime == null || status == null || selectedProgram == null || selectedPatient == null || selectedTherapist == null) {
            System.out.println("return from add session of sessionBOImpl");
            System.out.println("date: " + date);
            System.out.println("startTime: " + startTime);
            System.out.println("endTime: " + endTime);
            System.out.println("status: " + status);
            System.out.println("therapist: " + selectedTherapist);
            System.out.println("selectedProgram: " + selectedProgram);
            System.out.println("selectedPatient: " + selectedPatient);

            return false;
        }

        Session session = new Session();
        session.setId(id);
        session.setSessionDate(date);
        session.setStartTime(startTime);
        session.setEndTime(endTime);
        session.setStatus(status);
        session.setTherapyProgram(selectedProgram);
        session.setPatient(selectedPatient);
        session.setTherapist(selectedTherapist);

        return sessionDAO.update(session);
    }

    @Override
    public boolean deleteSession(int sessionId) {
        return sessionDAO.deleteByPk(String.valueOf(sessionId));
    }

    @Override
    public SessionDTO getSessionById(int sessionId) {
        Optional<Session> session = sessionDAO.findByPk(String.valueOf(sessionId));

        SessionDTO sessionDTO = new SessionDTO();

        if (session.isPresent()) {
            sessionDTO.setId(session.get().getId());
            sessionDTO.setSessionDate(session.get().getSessionDate());
            sessionDTO.setStartTime(session.get().getStartTime());
            sessionDTO.setEndTime(session.get().getEndTime());
            sessionDTO.setStatus(session.get().getStatus());
            sessionDTO.setPatient(session.get().getPatient());
            sessionDTO.setTherapist(session.get().getTherapist());
            sessionDTO.setTherapyProgram(session.get().getTherapyProgram());
        }
        return sessionDTO;

    }

    @Override
    public List<SessionDTO> getAllSessions() {
        ArrayList<SessionDTO> sessionDTOArrayList = new ArrayList<>();
        List<Session> entityList = sessionDAO.getAll();

        for (Session session : entityList) {
            sessionDTOArrayList.add(new SessionDTO(
                    session.getId(),
                    session.getSessionDate(),
                    session.getStartTime(),
                    session.getEndTime(),
                    session.getStatus(),
                    session.getPatient(),
                    session.getTherapist(),
                    session.getTherapyProgram()
            ));
        }
        return sessionDTOArrayList;
    }

    @Override
    public List<SessionDTO> getSessionsByTherapist(int therapistId) {

        List<SessionDTO> sessionDTOArrayList = new ArrayList<>();
        List<Session> sessionList = sessionDAO.getSessionsByTherapist(therapistId);

        for (Session session : sessionList) {
            sessionDTOArrayList.add(new SessionDTO(session));
        }
        return sessionDTOArrayList;

    }

    @Override
    public List<SessionDTO> getSessionsByPatient(int patientId) {
        List<SessionDTO> sessionDTOArrayList = new ArrayList<>();
        List<Session> sessionList = sessionDAO.getSessionsByPatient(patientId);

        for (Session session : sessionList) {
            sessionDTOArrayList.add(new SessionDTO(session));
        }
        return sessionDTOArrayList;
    }

    @Override
    public List<TherapistDTO> getAvailableTherapists(LocalDate sessionDate, LocalTime newStart, LocalTime newEnd) {
        // Get all therapists (from your BO, DAO, etc.)
        List<TherapistDTO> allTherapists = therapistBO.getAll();
        List<TherapistDTO> availableTherapists = new ArrayList<>();

        // Loop through every therapist
        for (TherapistDTO therapist : allTherapists) {
            // Retrieve sessions for the given therapist
            List<Session> sessionsForTherapist = sessionDAO.getSessionsByTherapist(therapist.getId());
            boolean isAvailable = true;

            System.out.println("Checking therapist: " + therapist.getName());

            // Look at only the sessions on the same date as the desired session
            for (Session session : sessionsForTherapist) {
                if (session.getSessionDate().equals(sessionDate)) {
                    System.out.println("  Existing Session: "
                            + session.getStartTime() + " to " + session.getEndTime());
                    // If the new session overlaps with any existing session, mark as not available.
                    if (timesOverlap(session.getStartTime(), session.getEndTime(), newStart, newEnd)) {
                        System.out.println("  Overlap found with new time: "
                                + newStart + " to " + newEnd);
                        isAvailable = false;
                        break;
                    }
                }
            }
            if (isAvailable) {
                availableTherapists.add(therapist);
                System.out.println("  Therapist available");
            }
        }
        return availableTherapists;
    }

    /**
     * Check if two time intervals overlap.
     * For intervals [existingStart, existingEnd) and [newStart, newEnd),
     * they overlap if:
     *     existingStart < newEnd  &&  newStart < existingEnd
     */
    private boolean timesOverlap(LocalTime existingStart, LocalTime existingEnd, LocalTime newStart, LocalTime newEnd) {
        return existingStart.isBefore(newEnd) && newStart.isBefore(existingEnd);
    }

}
