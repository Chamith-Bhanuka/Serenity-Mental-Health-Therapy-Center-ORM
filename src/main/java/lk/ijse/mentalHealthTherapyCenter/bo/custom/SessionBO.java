package lk.ijse.mentalHealthTherapyCenter.bo.custom;

import lk.ijse.mentalHealthTherapyCenter.bo.SuperBO;
import lk.ijse.mentalHealthTherapyCenter.dto.SessionDTO;
import lk.ijse.mentalHealthTherapyCenter.dto.TherapistDTO;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface SessionBO extends SuperBO {
    boolean addSession(String programId, String patientId, int therapistId, SessionDTO sessionDTO);
    boolean updateSession(String programId, String patientId, int therapistId, SessionDTO sessionDTO);
    boolean deleteSession(int sessionId);
    SessionDTO getSessionById(int sessionId);
    List<SessionDTO> getAllSessions();
    List<SessionDTO> getSessionsByTherapist(int therapistId);
    List<SessionDTO> getSessionsByPatient(int patientId);

    // Business method: get available therapists for a session time slot
    List<TherapistDTO> getAvailableTherapists(LocalDate sessionDate, LocalTime startTime, LocalTime endTime);
}
