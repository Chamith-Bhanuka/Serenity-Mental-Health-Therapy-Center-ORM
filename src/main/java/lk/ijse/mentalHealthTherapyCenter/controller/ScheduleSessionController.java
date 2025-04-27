package lk.ijse.mentalHealthTherapyCenter.controller;


import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.util.StringConverter;

import javafx.event.ActionEvent;

import lk.ijse.mentalHealthTherapyCenter.bo.BOFactory;
import lk.ijse.mentalHealthTherapyCenter.bo.custom.PatientBO;
import lk.ijse.mentalHealthTherapyCenter.bo.custom.SessionBO;
import lk.ijse.mentalHealthTherapyCenter.bo.custom.TherapistBO;
import lk.ijse.mentalHealthTherapyCenter.dto.PatientDTO;
import lk.ijse.mentalHealthTherapyCenter.dto.SessionDTO;
import lk.ijse.mentalHealthTherapyCenter.dto.TherapistDTO;
import lk.ijse.mentalHealthTherapyCenter.entity.Registration;
import lk.ijse.mentalHealthTherapyCenter.entity.TherapyProgram;
import lk.ijse.mentalHealthTherapyCenter.exception.ConflictSchedulingException;
import lk.ijse.mentalHealthTherapyCenter.view.tdm.SessionTM;

import java.net.URL;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class ScheduleSessionController implements Initializable {

    PatientBO patientBO = (PatientBO) BOFactory.getInstance().getBO(BOFactory.BOType.Patient);

    @FXML
    private ComboBox<PatientDTO> searchCombo;

    private final ObservableList<PatientDTO> masterPatientList = FXCollections.observableArrayList();

    private boolean ignoreTextChange = false;

    @FXML
    private Button btnCancelSession;

    @FXML
    private Button btnReset;

    @FXML
    private Button btnReshedule;

    @FXML
    private Button btnSaveSession;

    @FXML
    private ComboBox<String> cmbRegPrograms;

    @FXML
    private TableColumn<SessionTM, LocalDate> colDate;

    @FXML
    private TableColumn<SessionTM, LocalTime> colEndTime;

    @FXML
    private TableColumn<SessionTM, Integer> colId;

    @FXML
    private TableColumn<SessionTM, Integer> colPatientId;

    @FXML
    private TableColumn<SessionTM, String> colProgramId;

    @FXML
    private TableColumn<SessionTM, LocalTime> colStartTime;

    @FXML
    private TableColumn<SessionTM, String> colStatus;

    @FXML
    private TableColumn<SessionTM, Integer> colTheropistId;

    @FXML
    private DatePicker dpkDate;

    @FXML
    private Label lblAge;

    @FXML
    private Label lblGender;

    @FXML
    private Label lblPatientAddress;

    @FXML
    private Label lblPatientId;

    @FXML
    private Label lblPatientName;

    @FXML
    private ComboBox<String> cmbTherapist;

    @FXML
    private Spinner<String> sprEnd;

    @FXML
    private Spinner<String> sprStart;

    @FXML
    private TableView<SessionTM> tblSessions;

    TherapistBO therapistBO = (TherapistBO) BOFactory.getInstance().getBO(BOFactory.BOType.Therapist);

    SessionBO sessionBO = (SessionBO) BOFactory.getInstance().getBO(BOFactory.BOType.Session);

    List<TherapistDTO> therapistDTOS = new ArrayList<>();

    @FXML
    void onCancelSessionClick(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to cancel this session?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.YES) {

            System.out.println("Cancel session clicked with: " + tblSessions.getSelectionModel().getSelectedItem().getId());

            boolean isDeleted = sessionBO.deleteSession(tblSessions.getSelectionModel().getSelectedItem().getId());

            if (isDeleted) {
                new Alert(Alert.AlertType.INFORMATION, "Session successfully cancelled").show();
                refreshPage();
            } else {
                new Alert(Alert.AlertType.ERROR, "Session could not be deleted").show();
            }
        }
    }

    @FXML
    void onResetClick(ActionEvent event) {
        refreshPage();
    }

    @FXML
    void onResheduleClick(ActionEvent event) {
        System.out.println("<-----------Update/Reschedule click---------->");
        String selectedStartTime = sprStart.getValue();
        LocalTime startTime = LocalTime.parse(selectedStartTime);

        String selectedEndTime = sprEnd.getValue();
        LocalTime endTime = LocalTime.parse(selectedEndTime);

        //check availability

        LocalDate date = dpkDate.getValue();

        if (date == null) {
            new Alert(Alert.AlertType.ERROR, "Please enter a valid date", ButtonType.OK).show();
            return;
        }

        List<SessionDTO> sessionsForTherapist = sessionBO.getSessionsByTherapist(therapistIdGlob);
        boolean isConflict = false;

        for (SessionDTO sessionDTO : sessionsForTherapist) {
            if (sessionDTO.getSessionDate().equals(date)) {
                if (timesOverlap(sessionDTO.getStartTime(), sessionDTO.getEndTime(), startTime, endTime)) {
                    isConflict = true;
                    break;
                }
            }
        }

        if (isConflict) {
            new Alert(Alert.AlertType.ERROR, "Therapist is not available for the selected time slot.", ButtonType.OK).show();
            throw new ConflictSchedulingException("Error: This time slot is already taken. Please choose another time.");
        }

        //book session
        String patientId = lblPatientId.getText();
        String programId = tblSessions.getSelectionModel().getSelectedItem().getTherapyProgram().getProgramId();
        int therapistId = tblSessions.getSelectionModel().getSelectedItem().getTherapist().getId();



        SessionDTO sessionDTO = new SessionDTO();

        sessionDTO.setId(tblSessions.getSelectionModel().getSelectedItem().getId());
        sessionDTO.setSessionDate(date);
        sessionDTO.setStartTime(startTime);
        sessionDTO.setEndTime(endTime);
        sessionDTO.setStatus("Re-Scheduled");

        System.out.println("selected program Id from controller:" + selectedTherapyProgramIdGlob);
        System.out.println("selected patient id from controller:" + patientId);
        System.out.println("selected therapist id from controller:" + therapistIdGlob);

        boolean isUpdated = sessionBO.updateSession(programId, patientId, therapistId, sessionDTO);

        if (isUpdated) {
            new Alert(Alert.AlertType.INFORMATION, "Session Reschedule Success..!", ButtonType.OK).show();
            refreshPage();
        } else {
            new Alert(Alert.AlertType.ERROR, "Session Reschedule Failed..!", ButtonType.OK).show();
        }

    }

    @FXML
    void onSaveSessionClick(ActionEvent event) {

        String selectedStartTime = sprStart.getValue();
        LocalTime startTime = LocalTime.parse(selectedStartTime);

        String selectedEndTime = sprEnd.getValue();
        LocalTime endTime = LocalTime.parse(selectedEndTime);

        //check availability

        LocalDate date = dpkDate.getValue();

        if (date == null) {
            new Alert(Alert.AlertType.INFORMATION, "Please enter a valid date", ButtonType.OK).show();
            return;
        }

        List<SessionDTO> sessionsForTherapist = sessionBO.getSessionsByTherapist(therapistIdGlob);
        boolean isConflict = false;

        for (SessionDTO sessionDTO : sessionsForTherapist) {
            if (sessionDTO.getSessionDate().equals(date)) {
                if (timesOverlap(sessionDTO.getStartTime(), sessionDTO.getEndTime(), startTime, endTime)) {
                    isConflict = true;
                    break;
                }
            }
        }

        if (isConflict) {
            new Alert(Alert.AlertType.ERROR, "Therapist is not available for the selected time slot.", ButtonType.OK).show();
            throw new ConflictSchedulingException("Error: This time slot is already taken. Please choose another time.");
        }

        //book session
        String patientId = lblPatientId.getText();

        SessionDTO sessionDTO = new SessionDTO();

        sessionDTO.setSessionDate(date);
        sessionDTO.setStartTime(startTime);
        sessionDTO.setEndTime(endTime);
        sessionDTO.setStatus("Scheduled");

        boolean isSaved = sessionBO.addSession(selectedTherapyProgramIdGlob, patientId, therapistIdGlob, sessionDTO);

        if (isSaved) {
            new Alert(Alert.AlertType.CONFIRMATION, "Session Schedule Success..!", ButtonType.OK).show();
            refreshPage();
        } else {
            new Alert(Alert.AlertType.ERROR, "Session Schedule Failed..!", ButtonType.OK).show();
        }


    }

    private boolean timesOverlap(LocalTime existingStart, LocalTime existingEnd, LocalTime newStart, LocalTime newEnd) {
        return existingStart.isBefore(newEnd) && newStart.isBefore(existingEnd);
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadPatientsToComboBox();

        FilteredList<PatientDTO> filteredPatients = new FilteredList<>(masterPatientList, p -> true);
        searchCombo.setItems(filteredPatients);
        searchCombo.setEditable(true);

        searchCombo.setConverter(new StringConverter<PatientDTO>() {
            @Override
            public String toString(PatientDTO patient) {
                return (patient == null) ? "" : patient.getId() + " - " + patient.getName();
            }

            @Override
            public PatientDTO fromString(String string) {
                for (PatientDTO p : masterPatientList) {
                    String display = p.getId() + " - " + p.getName();
                    if (display.equals(string)) {
                        return p;
                    }
                }
                return null;
            }
        });

        searchCombo.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                ignoreTextChange = true;
            }
        });

        TextField editor = searchCombo.getEditor();
        editor.textProperty().addListener((obs, oldValue, newValue) -> {

            if (ignoreTextChange) {
                ignoreTextChange = false;
                return;
            }

            Platform.runLater(() -> {

                searchCombo.hide();

                searchCombo.getSelectionModel().clearSelection();

                filteredPatients.setPredicate(patient -> {
                    if (newValue == null || newValue.trim().isEmpty()) {
                        return true;
                    }
                    String lowerCaseFilter = newValue.toLowerCase();
                    return String.valueOf(patient.getId()).contains(lowerCaseFilter)
                            || patient.getName().toLowerCase().contains(lowerCaseFilter);
                });

                if (!filteredPatients.isEmpty() && editor.isFocused()) {
                    searchCombo.show();
                } else {
                    searchCombo.hide();
                }
            });
        });

        searchCombo.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue != null) {

                PatientDTO selectedPatient = newValue;

                handlePatientSelection(selectedPatient);

            }
        });

        ObservableList<String> times = generateTimeStrings();
        sprStart.setValueFactory(new SpinnerValueFactory.ListSpinnerValueFactory<>(times));
        sprEnd.setValueFactory(new SpinnerValueFactory.ListSpinnerValueFactory<>(times));

        setCellValues();

        refreshPage();

    }

    String selectedTherapyProgramIdGlob = "";

    private void handlePatientSelection(PatientDTO patient) {

        System.out.println("Handling selection for patient: " + patient.getId() + " - " + patient.getName());

        lblPatientId.setText(String.valueOf(patient.getId()));
        lblPatientName.setText(patient.getName());
        lblPatientAddress.setText(patient.getAddress());
        lblGender.setText(patient.getGender());
        lblAge.setText(String.valueOf(patient.getAge()));

        // Extract therapy program list from the patient's registrations
        List<TherapyProgram> therapyProgramList = new ArrayList<>();

        if (patient.getRegistrationList() != null) {
            for (Registration registration : patient.getRegistrationList()) {

                if (registration.getTherapyProgram() != null) {
                    therapyProgramList.add(registration.getTherapyProgram());
                }
            }
        }

        ObservableList<String> therapyProgramNames = FXCollections.observableArrayList();

        if (patient.getRegistrationList() != null) {
            for (Registration registration : patient.getRegistrationList()) {
                if (registration.getTherapyProgram() != null) {
                    therapyProgramNames.add(registration.getTherapyProgram().getProgramName());
                }
            }
        }

        cmbRegPrograms.setItems(therapyProgramNames);


        // Add listener to get selected program ID
        cmbRegPrograms.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            for (TherapyProgram program : therapyProgramList) {
                if (program.getProgramName().equals(newValue)) {
                    selectedTherapyProgramIdGlob = program.getProgramId();
                    loadTherapistToComboBox();
                    System.out.println("Selected Therapy Program ID: " + selectedTherapyProgramIdGlob);
                    break;
                }
            }
        });



    }

    private void loadPatientsToComboBox() {
        List<PatientDTO> patients = patientBO.getAll();
        masterPatientList.setAll(patients);
    }

    int therapistIdGlob;

    private void loadTherapistToComboBox() {
        therapistDTOS = therapistBO.getTherapistsByProgramId(selectedTherapyProgramIdGlob);
        System.out.println("Therapist DTO: " + therapistDTOS.getFirst().getName());

        List<String> therapistNames = therapistDTOS.stream()
                .map(TherapistDTO::getName)
                .toList();

        cmbTherapist.setItems(FXCollections.observableArrayList(therapistNames));

        cmbTherapist.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            for (TherapistDTO dto : therapistDTOS) {
                if (dto.getName().equals(newValue)) {
                    therapistIdGlob = dto.getId();
                    System.out.println("Therapist ID globe: " + therapistIdGlob);
                    break;
                }
            }
        });
    }

    private ObservableList<String> generateTimeStrings() {
        ObservableList<String> timeList = FXCollections.observableArrayList();
        for (int hour = 0; hour < 24; hour++) {
            for (int minute = 0; minute < 60; minute += 30) {
                timeList.add(String.format("%02d:%02d", hour, minute));
            }
        }
        return timeList;
    }

    private void setCellValues() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("sessionDate"));
        colStartTime.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        colEndTime.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colPatientId.setCellValueFactory(cellData ->
                new SimpleIntegerProperty(cellData.getValue().getPatient().getId()).asObject()
        );
        colTheropistId.setCellValueFactory(cellData ->
                new SimpleIntegerProperty(cellData.getValue().getTherapist().getId()).asObject()
        );
        colProgramId.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getTherapyProgram().getProgramId())
        );



    }

    private void refreshPage() {
        refreshTable();

        cmbTherapist.getSelectionModel().clearSelection();
        cmbRegPrograms.getSelectionModel().clearSelection();

        dpkDate.setValue(null);
        sprStart.getValueFactory().setValue(null);
        sprEnd.getValueFactory().setValue(null);

        btnSaveSession.setDisable(false);

        btnReshedule.setDisable(true);
        btnReset.setDisable(true);
        btnCancelSession.setDisable(true);

    }

    private void refreshTable() {
        ArrayList<SessionDTO> sessionDTOS = (ArrayList<SessionDTO>) sessionBO.getAllSessions();

        ObservableList<SessionTM> sessionTMs = FXCollections.observableArrayList();

        for (SessionDTO sessionDTO : sessionDTOS) {
            SessionTM sessionTM = new SessionTM(
                    sessionDTO.getId(),
                    sessionDTO.getSessionDate(),
                    sessionDTO.getStartTime(),
                    sessionDTO.getEndTime(),
                    sessionDTO.getStatus(),
                    sessionDTO.getPatient(),
                    sessionDTO.getTherapist(),
                    sessionDTO.getTherapyProgram()
            );
            sessionTMs.add(sessionTM);
        }
        tblSessions.setItems(sessionTMs);
    }

    @FXML
    void onTblSessionClick(MouseEvent event) {
        SessionTM selectedItem = tblSessions.getSelectionModel().getSelectedItem();

        if (selectedItem != null) {
            lblPatientId.setText(String.valueOf(selectedItem.getPatient().getId()));
            lblPatientName.setText(selectedItem.getPatient().getName());
            lblPatientAddress.setText(selectedItem.getPatient().getAddress());
            lblGender.setText(selectedItem.getPatient().getGender());
            lblAge.setText(String.valueOf(selectedItem.getPatient().getAge()));

            cmbRegPrograms.setItems(FXCollections.observableArrayList(selectedItem.getTherapyProgram().getProgramName()));
            cmbTherapist.setItems(FXCollections.observableArrayList(selectedItem.getTherapist().getName()));
            dpkDate.setValue(selectedItem.getSessionDate());

            btnSaveSession.setDisable(true);

            btnReshedule.setDisable(false);
            btnReset.setDisable(false);
            btnCancelSession.setDisable(false);

        }

    }


}
