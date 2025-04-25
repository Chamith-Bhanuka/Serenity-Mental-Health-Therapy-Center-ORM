package lk.ijse.mentalHealthTherapyCenter.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import lk.ijse.mentalHealthTherapyCenter.bo.BOFactory;
import lk.ijse.mentalHealthTherapyCenter.bo.custom.TherapistBO;
import lk.ijse.mentalHealthTherapyCenter.entity.Session;
import lombok.Setter;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class TherapistScheduleAvailabilityController implements Initializable {

    @FXML
    private Label lblTherapistId;

    @FXML
    private Label txtName;

    @FXML
    private TextArea txtHistoryArea;

    int therapistId;
    String therapistName;

    TherapistBO therapistBO = (TherapistBO) BOFactory.getInstance().getBO(BOFactory.BOType.Therapist);

    public void setUp(int therapistId, String therapistName) {
        this.therapistId = therapistId;
        lblTherapistId.setText(String.valueOf(therapistId));

        this.therapistName = therapistName;
        this.txtName.setText("Dr. " + therapistName);

        System.out.println("therapistID: " + therapistId);
        List<Session> sessionList = therapistBO.getTherapistSchedule(therapistId);
        showTherapistSchedule(sessionList);
        System.out.println("Session list: " + sessionList);
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    private void showTherapistSchedule(List<Session> sessionList) {
        if (sessionList == null || sessionList.isEmpty()) {
            txtHistoryArea.setText("""
                📋 No sessions scheduled.
                
                Please check back later or assign new sessions.
                """);
            return;
        }

        StringBuilder historyText = new StringBuilder();

        for (Session session : sessionList) {
            historyText.append("──────────────\n");
            historyText.append("🗓 Date: ").append(session.getSessionDate()).append("\n");
            historyText.append("⏰ Time: ").append(session.getStartTime()).append(" - ").append(session.getEndTime()).append("\n");
            historyText.append("👤 Patient: ").append(session.getPatient().getName()).append("\n");
            historyText.append("💼 Program: ").append(session.getTherapyProgram().getProgramName()).append("\n");
            historyText.append("📌 Status: ").append(session.getStatus()).append("\n");
            historyText.append("──────────────\n\n");
        }

        txtHistoryArea.setText(historyText.toString());
    }



}
