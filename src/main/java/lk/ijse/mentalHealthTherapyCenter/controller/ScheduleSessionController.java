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
import lk.ijse.mentalHealthTherapyCenter.bo.BOFactory;
import lk.ijse.mentalHealthTherapyCenter.bo.custom.PatientBO;
import lk.ijse.mentalHealthTherapyCenter.dto.PatientDTO;

import java.net.URL;

import java.util.List;
import java.util.ResourceBundle;

public class ScheduleSessionController implements Initializable {

    PatientBO patientBO = (PatientBO) BOFactory.getInstance().getBO(BOFactory.BOType.Patient);

    @FXML
    private ComboBox<PatientDTO> searchCombo;

    private final ObservableList<PatientDTO> masterPatientList = FXCollections.observableArrayList();

    private boolean ignoreTextChange = false;

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
    }

    private void loadPatientsToComboBox() {
        List<PatientDTO> patients = patientBO.getAll();
        masterPatientList.setAll(patients);
    }

}
