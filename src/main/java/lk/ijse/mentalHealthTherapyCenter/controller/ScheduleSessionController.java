package lk.ijse.mentalHealthTherapyCenter.controller;


import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import lk.ijse.mentalHealthTherapyCenter.bo.BOFactory;
import lk.ijse.mentalHealthTherapyCenter.bo.custom.PatientBO;
import lk.ijse.mentalHealthTherapyCenter.dto.PatientDTO;
import lk.ijse.mentalHealthTherapyCenter.entity.Registration;
import lk.ijse.mentalHealthTherapyCenter.entity.TherapyProgram;

import java.net.URL;

import java.util.ArrayList;
import java.util.List;
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
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<?, ?> colEndTime;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colPatientId;

    @FXML
    private TableColumn<?, ?> colProgramId;

    @FXML
    private TableColumn<?, ?> colStartTime;

    @FXML
    private TableColumn<?, ?> colStatus;

    @FXML
    private TableColumn<?, ?> colTheropistId;

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
    private ComboBox<?> searchCombo2;

    @FXML
    private Spinner<?> sprEnd;

    @FXML
    private Spinner<?> sprStart;

    @FXML
    private TableView<?> tblSessions;

    @FXML
    void onCancelSessionClick(ActionEvent event) {

    }

    @FXML
    void onRegProgramsClick(ActionEvent event) {

    }

    @FXML
    void onResetClick(ActionEvent event) {

    }

    @FXML
    void onResheduleClick(ActionEvent event) {

    }

    @FXML
    void onSaveSessionClick(ActionEvent event) {

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

    }

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


    }

    private void loadPatientsToComboBox() {
        List<PatientDTO> patients = patientBO.getAll();
        masterPatientList.setAll(patients);
    }

}
