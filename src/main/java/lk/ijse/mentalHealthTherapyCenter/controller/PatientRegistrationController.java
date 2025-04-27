package lk.ijse.mentalHealthTherapyCenter.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import lk.ijse.mentalHealthTherapyCenter.bo.BOFactory;
import lk.ijse.mentalHealthTherapyCenter.bo.custom.PatientBO;
import lk.ijse.mentalHealthTherapyCenter.bo.custom.RegistrationBO;
import lk.ijse.mentalHealthTherapyCenter.bo.custom.TherapyProgramBO;
import lk.ijse.mentalHealthTherapyCenter.dto.PatientDTO;
import lk.ijse.mentalHealthTherapyCenter.dto.TherapyProgramDTO;
import lk.ijse.mentalHealthTherapyCenter.entity.Registration;
import lk.ijse.mentalHealthTherapyCenter.view.tdm.PatientTM;
import lk.ijse.mentalHealthTherapyCenter.view.tdm.ProgramSelectTM;

import java.io.IOException;
import java.net.URL;
import java.util.*;

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
    private TableColumn<PatientTM, String> colHistory;

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

    @FXML
    private TextArea txtMedicalHistory;


    TherapyProgramBO therapyProgramBO = (TherapyProgramBO) BOFactory.getInstance().getBO(BOFactory.BOType.TherapyProgram);

    PatientBO patientBO = (PatientBO) BOFactory.getInstance().getBO(BOFactory.BOType.Patient);

    RegistrationBO registrationBO = (RegistrationBO) BOFactory.getInstance().getBO(BOFactory.BOType.Registration);

    List<String> selectedIdList = new ArrayList<>();

    @FXML
    void onAddProgramsClick(ActionEvent event) {
        System.out.println("Add programs clicked");
        selectedIdList = getSelectedIds();
        System.out.println("This is selected ids: " + selectedIdList);


        if (selectedIdList!=null && !selectedIdList.isEmpty()) {
            btnAddProgram.setDisable(true);
            btnRegister.setDisable(false);
        } else {
            new Alert(Alert.AlertType.WARNING, "Please select at least one program").show();
        }

    }

    @FXML
    void onDeleteClick(ActionEvent event) {
        System.out.println("Delete clicked");

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this patient?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.YES) {
            boolean isDeleted = patientBO.deleteByPk(txtPatientId.getText());

            if (isDeleted) {
                new Alert(Alert.AlertType.INFORMATION, "Deleted successfully").show();
                refreshPage();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to delete patient").show();
            }
        }

    }

    @FXML
    void onRegisterPatientClicked(ActionEvent event) throws IOException {

        String namePattern = "^[A-Za-z ]+$";
        String emailPattern = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        String phonePattern = "^(\\d+)||((\\d+\\.)(\\d){2})$";
        String agePattern = "^\\d{1,3}$";
        String genderPattern = "^(?i)(male|female|other)$";

        boolean isValidName = txtPatientName.getText().matches(namePattern);
        boolean isValidEmail = txtEmail.getText().matches(emailPattern);
        boolean isValidPhone = txtPhone.getText().matches(phonePattern);
        boolean isValidAge = txtAge.getText().matches(agePattern);
        boolean isValidGender = txtGender.getText().matches(genderPattern);

        txtPatientName.setStyle(txtPatientName.getStyle() + ";-fx-border-color: #b2dfdb;");
        txtEmail.setStyle(txtEmail.getStyle() + ";-fx-border-color: #b2dfdb;");
        txtPhone.setStyle(txtPhone.getStyle() + ";-fx-border-color: #b2dfdb;");
        txtAge.setStyle(txtAge.getStyle() + ";-fx-border-color: #b2dfdb;");
        txtGender.setStyle(txtGender.getStyle() + ";-fx-border-color: #b2dfdb;");


        if (!isValidName) {
            txtPatientName.setStyle(txtPatientName.getStyle() + ";-fx-border-color: red;");
        }
        if (!isValidEmail) {
            txtEmail.setStyle(txtEmail.getStyle() + ";-fx-border-color: red;");
        }
        if (!isValidPhone) {
            txtPhone.setStyle(txtPhone.getStyle() + ";-fx-border-color: red;");

        }
        if (!isValidAge) {
            txtAge.setStyle(txtAge.getStyle() + ";-fx-border-color: red;");
        }
        if (!isValidGender) {
            txtGender.setStyle(txtGender.getStyle() + ";-fx-border-color: red;");
        }

        if (isValidAge && isValidEmail && isValidPhone && isValidGender && isValidName) {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/payment.fxml"));
            Parent load = loader.load();

            PaymentController paymentController = loader.getController();

            if (paymentController == null) {
                System.out.println("Error: PaymentController is NULL!");
            } else {
                System.out.println("Controller is found!");
            }

            String name = txtPatientName.getText();
            String email = txtEmail.getText();
            String phone = txtPhone.getText();
            String address = txtAddress.getText();
            String gender = txtGender.getText();
            String medicalHistory = txtMedicalHistory.getText();
            int age = Integer.parseInt(txtAge.getText());
            List<String> selectedIdList = getSelectedIds();

            paymentController.setup(name, email, phone, address, gender, medicalHistory, age, selectedIdList);

            Stage stage = new Stage();
            stage.setScene(new Scene(load));
            stage.setTitle("Patient Registration");

            stage.initModality(Modality.APPLICATION_MODAL);
            Window underWindow = btnAddProgram.getScene().getWindow();
            stage.initOwner(underWindow);

            stage.showAndWait();

            refreshPage();

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

        String namePattern = "^[A-Za-z ]+$";
        String emailPattern = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        String phonePattern = "^(\\d+)||((\\d+\\.)(\\d){2})$";
        String agePattern = "^\\d{1,3}$";
        String genderPattern = "^(?i)(male|female|other)$";

        boolean isValidName = txtPatientName.getText().matches(namePattern);
        boolean isValidEmail = txtEmail.getText().matches(emailPattern);
        boolean isValidPhone = txtPhone.getText().matches(phonePattern);
        boolean isValidAge = txtAge.getText().matches(agePattern);
        boolean isValidGender = txtGender.getText().matches(genderPattern);

        txtPatientName.setStyle(txtPatientName.getStyle() + ";-fx-border-color: #b2dfdb;");
        txtEmail.setStyle(txtEmail.getStyle() + ";-fx-border-color: #b2dfdb;");
        txtPhone.setStyle(txtPhone.getStyle() + ";-fx-border-color: #b2dfdb;");
        txtAge.setStyle(txtAge.getStyle() + ";-fx-border-color: #b2dfdb;");
        txtGender.setStyle(txtGender.getStyle() + ";-fx-border-color: #b2dfdb;");

        if (!isValidName) {
            txtPatientName.setStyle(txtPatientName.getStyle() + ";-fx-border-color: red;");
        }
        if (!isValidEmail) {
            txtEmail.setStyle(txtEmail.getStyle() + ";-fx-border-color: red;");
        }
        if (!isValidPhone) {
            txtPhone.setStyle(txtPhone.getStyle() + ";-fx-border-color: red;");

        }
        if (!isValidAge) {
            txtAge.setStyle(txtAge.getStyle() + ";-fx-border-color: red;");
        }
        if (!isValidGender) {
            txtGender.setStyle(txtGender.getStyle() + ";-fx-border-color: red;");
        }

        if (isValidAge && isValidEmail && isValidPhone && isValidGender && isValidName) {
            int id = Integer.parseInt(txtPatientId.getText());
            String name = txtPatientName.getText();
            String email = txtEmail.getText();
            String phone = txtPhone.getText();
            String address = txtAddress.getText();
            String gender = txtGender.getText();
            String medicalHistory = txtMedicalHistory.getText();
            int age = Integer.parseInt(txtAge.getText());

            Optional<Registration> optionalRegistration = registrationBO.findByPk(String.valueOf(id));
            List<Registration> registrationList = new ArrayList<>();

            optionalRegistration.ifPresent(registrationList::add);

            PatientDTO patientDTO = new PatientDTO(id, name, email, phone, address, gender, age, medicalHistory, registrationList);

            boolean isUpdated = patientBO.update(patientDTO);

            if (isUpdated) {
                new Alert(Alert.AlertType.INFORMATION, "Patient Updated successfully..!").show();
                refreshPage();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to Update Patient..!", ButtonType.OK).show();

            }
        }


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
        colHistory.setCellValueFactory(new PropertyValueFactory<>("medicalHistory"));
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
                    patientDTO.getAge(),
                    patientDTO.getMedicalHistory()
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
        txtMedicalHistory.setText("");
        tblTherapyPrograms.getSelectionModel().clearSelection();

        btnRegister.setDisable(true);
        btnAddProgram.setDisable(false);

        btnReset.setDisable(false);
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
            txtMedicalHistory.setText(selectedItem.getMedicalHistory());

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

        txtPatientId.setEditable(false);
    }
}
