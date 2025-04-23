package lk.ijse.mentalHealthTherapyCenter.controller;

import javafx.beans.property.BooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import lk.ijse.mentalHealthTherapyCenter.bo.BOFactory;
import lk.ijse.mentalHealthTherapyCenter.bo.custom.PatientBO;
import lk.ijse.mentalHealthTherapyCenter.bo.custom.TherapyProgramBO;
import lk.ijse.mentalHealthTherapyCenter.dto.PatientDTO;
import lk.ijse.mentalHealthTherapyCenter.dto.TherapyProgramDTO;
import lk.ijse.mentalHealthTherapyCenter.entity.Registration;
import lk.ijse.mentalHealthTherapyCenter.entity.TherapyProgram;
import lk.ijse.mentalHealthTherapyCenter.view.tdm.PatientTM;
import lk.ijse.mentalHealthTherapyCenter.view.tdm.ProgramSelectTM;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class PatientRegistrationController implements Initializable {

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnRegister;

    @FXML
    private Button btnReset;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableColumn<PatientTM, String> colAddress;

    @FXML
    private TableColumn<PatientTM, Integer> colAge;

    @FXML
    private TableColumn<PatientTM, String> colEmail;

    @FXML
    private TableColumn<ProgramSelectTM, Double> colFee;

    @FXML
    private TableColumn<ProgramSelectTM, Boolean> colAction;

    @FXML
    private TableColumn<PatientTM, String> colGender;

    @FXML
    private TableColumn<PatientTM, Integer> colPatientId;

    @FXML
    private TableColumn<PatientTM, String> colPatientName;

    @FXML
    private TableColumn<PatientTM, String> colPhone;

    @FXML
    private TableColumn<ProgramSelectTM, String> colProgramID;

    @FXML
    private TableColumn<ProgramSelectTM, String> colProgramName;

    @FXML
    private Label lblTotalBilling;

    @FXML
    private TableView<PatientTM> tblPatient;

    @FXML
    private TableView<ProgramSelectTM> tblTherapyPrograms;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtAge;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtGender;

    @FXML
    private TextField txtPatientId;

    @FXML
    private TextField txtPatientName;

    @FXML
    private TextField txtPhone;

    @FXML
    private Button btnAddProgram;


    TherapyProgramBO therapyProgramBO = (TherapyProgramBO) BOFactory.getInstance().getBO(BOFactory.BOType.TherapyProgram);
    PatientBO patientBO = (PatientBO) BOFactory.getInstance().getBO(BOFactory.BOType.Patient);

    List<String> selectedIdList = new ArrayList<>();

    @FXML
    void onAddProgramsClick(ActionEvent event) {
        System.out.println("Add programs clicked");
        selectedIdList = getSelectedIds();
        System.out.println("This is selected ids: " + selectedIdList);

        btnAddProgram.setDisable(true);

    }

    @FXML
    void onDeleteClick(ActionEvent event) {
        System.out.println("Delete clicked");

    }

    @FXML
    void onRegisterPatientClicked(ActionEvent event) {
        System.out.println("Register patient clicked");

        int id = Integer.parseInt(txtPatientId.getText());
        String name = txtPatientName.getText();
        String email = txtEmail.getText();
        String phone = txtPhone.getText();
        String address = txtAddress.getText();
        String gender = txtGender.getText();
        int age = Integer.parseInt(txtAge.getText());

        List<Registration> registrationList = new ArrayList<>();
        PatientDTO patientDTO = new PatientDTO(id, name, email, phone, address, gender, age, registrationList);

        boolean isSaved = patientBO.save(patientDTO, selectedIdList);
        System.out.println("boolean save value: " + isSaved);

        if (isSaved) {
            new Alert(Alert.AlertType.INFORMATION, "Patient Registered successfully..!").show();
            refreshPage();
        } else {
            new Alert(Alert.AlertType.ERROR, "Failed to Register Patient..!", ButtonType.OK).show();

        }


    }

    @FXML
    void onResetClick(ActionEvent event) {
        System.out.println("Reset patient clicked");
        refreshPage();

    }

    @FXML
    void onUpdateClick(ActionEvent event) {
        System.out.println("Update patient clicked");

    }

    private void setCellValues() {
        colProgramID.setCellValueFactory(new PropertyValueFactory<>("programId"));
        colProgramName.setCellValueFactory(new PropertyValueFactory<>("programName"));
        colFee.setCellValueFactory(new PropertyValueFactory<>("fee"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("checked"));

        colAction.setCellFactory(CheckBoxTableCell.forTableColumn(colAction));
        colAction.setCellValueFactory(cellData-> cellData.getValue().checkedProperty());
        tblTherapyPrograms.setEditable(true);

        loadDataToProgramsTbl();
    }

    private void loadDataToProgramsTbl() {
        List<TherapyProgramDTO> therapyProgramDTOList = therapyProgramBO.getAll();

        for (TherapyProgramDTO therapyProgramDTO : therapyProgramDTOList) {
            tblTherapyPrograms.getItems().add(new ProgramSelectTM(therapyProgramDTO.getProgramId(), therapyProgramDTO.getProgramName(), therapyProgramDTO.getFee()));
        }
    }

    private List<String> getSelectedIds() {
        return tblTherapyPrograms.getItems() == null ?
                List.of() : // Return an empty list if null
                tblTherapyPrograms.getItems().stream()
                        .filter(ProgramSelectTM::isChecked)
                        .map(ProgramSelectTM::getProgramId)
                        .toList();
    }

    private void setCellValuesForTblPatient() {
        colPatientId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colPatientName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colGender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        colAge.setCellValueFactory(new PropertyValueFactory<>("age"));
    }

    private void refreshTable() {
        ArrayList<PatientDTO> patientDTOS = (ArrayList<PatientDTO>) patientBO.getAll();

        ObservableList<PatientTM> patientTMS = FXCollections.observableArrayList();

        for (PatientDTO patientDTO : patientDTOS) {
            PatientTM patientTM = new PatientTM(
                    patientDTO.getId(),
                    patientDTO.getName(),
                    patientDTO.getEmail(),
                    patientDTO.getPhone(),
                    patientDTO.getAddress(),
                    patientDTO.getGender(),
                    patientDTO.getAge()
            );
            patientTMS.add(patientTM);
        }
        tblPatient.setItems(patientTMS);
    }

    private void refreshPage() {
        refreshTable();

        txtPatientId.setText("");
        txtPatientName.setText("");
        txtEmail.setText("");
        txtPhone.setText("");
        txtAddress.setText("");
        txtGender.setText("");
        txtAge.setText("");
        tblTherapyPrograms.getSelectionModel().clearSelection();

        btnRegister.setDisable(false);
        btnAddProgram.setDisable(false);

        btnReset.setDisable(true);
        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);
    }

    @FXML
    void onPatientTableClick(MouseEvent event) {
        PatientTM selectedItem = tblPatient.getSelectionModel().getSelectedItem();

        if (selectedItem != null) {
            txtPatientId.setText(String.valueOf(selectedItem.getId()));
            txtPatientName.setText(selectedItem.getName());
            txtEmail.setText(selectedItem.getEmail());
            txtPhone.setText(selectedItem.getPhone());
            txtAddress.setText(selectedItem.getAddress());
            txtGender.setText(selectedItem.getGender());
            txtAge.setText(String.valueOf(selectedItem.getAge()));

            btnRegister.setDisable(true);

            btnReset.setDisable(false);
            btnUpdate.setDisable(false);
            btnDelete.setDisable(false);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setCellValues();

        setCellValuesForTblPatient();

        refreshPage();
    }
}
