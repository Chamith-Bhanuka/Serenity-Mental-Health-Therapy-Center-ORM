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
import lk.ijse.mentalHealthTherapyCenter.bo.custom.TherapistBO;
import lk.ijse.mentalHealthTherapyCenter.bo.custom.TherapyProgramBO;
import lk.ijse.mentalHealthTherapyCenter.dto.TherapistDTO;
import lk.ijse.mentalHealthTherapyCenter.dto.TherapyProgramDTO;
import lk.ijse.mentalHealthTherapyCenter.entity.Session;
import lk.ijse.mentalHealthTherapyCenter.entity.TherapyProgram;
import lk.ijse.mentalHealthTherapyCenter.view.tdm.ProgramSelectTM;
import lk.ijse.mentalHealthTherapyCenter.view.tdm.TherapistTM;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class TherapistManagementController implements Initializable {

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnReset;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableColumn<String, TherapistTM> colEmail;

    @FXML
    private TableColumn<Integer, TherapistTM> colId;

    @FXML
    private TableColumn<String , TherapistTM> colName;

    @FXML
    private TableColumn<String, TherapistTM> colPhone;

    @FXML
    private TableColumn<String, TherapistTM> colSpecialization;

    @FXML
    private TableView<TherapistTM> tblTherapist;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtPhone;

    @FXML
    private TextField txtSpecialization;

    @FXML
    private TableColumn<ProgramSelectTM, String> colProgram;

    @FXML
    private TableColumn<ProgramSelectTM, String> colProgramId;

    @FXML
    private TableColumn<ProgramSelectTM, Boolean> colAction;

    @FXML
    private TableColumn<ProgramSelectTM, Double> colFee;

    @FXML
    private TableView<ProgramSelectTM> tblProgram;

    @FXML
    private Button btnSearch;


    TherapistBO therapistBO = (TherapistBO) BOFactory.getInstance().getBO(BOFactory.BOType.Therapist);
    TherapyProgramBO therapyProgramBO = (TherapyProgramBO) BOFactory.getInstance().getBO(BOFactory.BOType.TherapyProgram);

    List<String> selectedProgramIds = new ArrayList<>();

    @FXML
    void onDelete(ActionEvent event) {
        System.out.println("Delete clicked");

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this therapist?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.YES) {

            int selectedId = tblTherapist.getSelectionModel().getSelectedItem().getId();

            boolean isDeleted = therapistBO.deleteByPk(String.valueOf(selectedId));

            if (isDeleted) {
                new Alert(Alert.AlertType.INFORMATION, "Therapist Deleted successfully..!").show();
                refreshPage();
            } else {
                new Alert(Alert.AlertType.ERROR, "Therapist Not Deleted..!").show();
            }

        }

    }

    @FXML
    void onReset(ActionEvent event) {
        System.out.println("reset clicked");
        refreshPage();
    }

    @FXML
    void onSave(ActionEvent event) {
        System.out.println("Save clicked");

        int id = 0;
        String name = txtName.getText();
        String email = txtEmail.getText();
        String phone = txtPhone.getText();
        String specialization = txtSpecialization.getText();
        List<TherapyProgram> therapyProgramList = new ArrayList<>();

        String namePattern = "^[A-Za-z ]+$";
        String emailPattern = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        String phonePattern = "^(\\d+)||((\\d+\\.)(\\d){2})$";

        boolean isValidName = txtName.getText().matches(namePattern);
        boolean isValidEmail = txtEmail.getText().matches(emailPattern);
        boolean isValidPhone = txtPhone.getText().matches(phonePattern);

        txtName.setStyle(txtName.getStyle() + ";-fx-border-color: #b2dfdb;");
        txtEmail.setStyle(txtEmail.getStyle() + ";-fx-border-color: #b2dfdb;");
        txtPhone.setStyle(txtPhone.getStyle() + ";-fx-border-color: #b2dfdb;");


        if (!isValidName) {
            txtName.setStyle(txtName.getStyle() + ";-fx-border-color: red;");
        }
        if (!isValidEmail) {
            txtEmail.setStyle(txtEmail.getStyle() + ";-fx-border-color: red;");
        }
        if (!isValidPhone) {
            txtPhone.setStyle(txtPhone.getStyle() + ";-fx-border-color: red;");
        }

        if (isValidName && isValidEmail && isValidPhone) {
            TherapistDTO therapistDTO = new TherapistDTO(id, name, email, phone, specialization, therapyProgramList);

            boolean isSaved = therapistBO.save(therapistDTO, selectedProgramIds);

            if (isSaved) {
                new Alert(Alert.AlertType.INFORMATION, "Therapist Saved..!").show();
                refreshPage();
            } else {
                new Alert(Alert.AlertType.ERROR, "Therapist Not Saved..!").show();
            }
        }

    }

    @FXML
    void onUpdate(ActionEvent event) {
        System.out.println("Update clicked");

        int id = tblTherapist.getSelectionModel().getSelectedItem().getId();
        String name = txtName.getText();
        String email = txtEmail.getText();
        String phone = txtPhone.getText();
        String specialization = txtSpecialization.getText();
        List<TherapyProgram> therapyProgramList = new ArrayList<>();

        String namePattern = "^[A-Za-z ]+$";
        String emailPattern = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        String phonePattern = "^(\\d+)||((\\d+\\.)(\\d){2})$";

        boolean isValidName = txtName.getText().matches(namePattern);
        boolean isValidEmail = txtEmail.getText().matches(emailPattern);
        boolean isValidPhone = txtPhone.getText().matches(phonePattern);

        txtName.setStyle(txtName.getStyle() + ";-fx-border-color: #b2dfdb;");
        txtEmail.setStyle(txtEmail.getStyle() + ";-fx-border-color: #b2dfdb;");
        txtPhone.setStyle(txtPhone.getStyle() + ";-fx-border-color: #b2dfdb;");


        if (!isValidName) {
            txtName.setStyle(txtName.getStyle() + ";-fx-border-color: red;");
        }
        if (!isValidEmail) {
            txtEmail.setStyle(txtEmail.getStyle() + ";-fx-border-color: red;");
        }
        if (!isValidPhone) {
            txtPhone.setStyle(txtPhone.getStyle() + ";-fx-border-color: red;");
        }

        if (isValidName && isValidEmail && isValidPhone) {
            TherapistDTO therapistDTO = new TherapistDTO(id, name, email, phone, specialization, therapyProgramList);

            boolean isUpdated = therapistBO.update(therapistDTO, selectedProgramIds);

            if (isUpdated) {
                new Alert(Alert.AlertType.INFORMATION, "Therapist Updated..!").show();
                refreshPage();
            } else {
                new Alert(Alert.AlertType.ERROR, "Therapist Not Updated..!").show();
            }
        }

    }

    @FXML
    void onTblTherapistClick(MouseEvent event) {
        TherapistTM selectedItem = tblTherapist.getSelectionModel().getSelectedItem();

        if (selectedItem != null) {
            txtName.setText(selectedItem.getName());
            txtPhone.setText(selectedItem.getPhone());
            txtEmail.setText(selectedItem.getEmail());
            txtSpecialization.setText(selectedItem.getSpecialization());

            btnSave.setDisable(true);
            btnSave.setVisible(false);
            btnSearch.setVisible(true);

            btnUpdate.setDisable(false);
            btnDelete.setDisable(false);
            btnReset.setDisable(false);
        }
    }

    private void refreshPage() {
        refreshTable();

        txtName.setText("");
        txtPhone.setText("");
        txtEmail.setText("");
        txtSpecialization.setText("");

        btnSave.setDisable(false);
        btnSave.setVisible(true);
        btnSearch.setVisible(false);

        btnDelete.setDisable(true);
        btnReset.setDisable(false);
        btnUpdate.setDisable(true);
    }

    private void refreshTable() {
        ArrayList<TherapistDTO> therapistDTOS = (ArrayList<TherapistDTO>) therapistBO.getAll();

        ObservableList<TherapistTM> therapistTMS = FXCollections.observableArrayList();

        for (TherapistDTO therapistDTO : therapistDTOS) {
            TherapistTM therapistTM = new TherapistTM(
                    therapistDTO.getId(),
                    therapistDTO.getName(),
                    therapistDTO.getEmail(),
                    therapistDTO.getPhone(),
                    therapistDTO.getSpecialization()
            );
            therapistTMS.add(therapistTM);
        }
        tblTherapist.setItems(therapistTMS);

        //reset check boxes
        resetCheckboxes();
    }

    private void setCellValues() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        colSpecialization.setCellValueFactory(new PropertyValueFactory<>("specialization"));
    }

    private void setCellValuesForProgramSelector() {
        colProgramId.setCellValueFactory(new PropertyValueFactory<>("programId"));
        colProgram.setCellValueFactory(new PropertyValueFactory<>("programName"));
        colFee.setCellValueFactory(new PropertyValueFactory<>("fee"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("checked"));

        colAction.setCellFactory(CheckBoxTableCell.forTableColumn(colAction));
        colAction.setCellValueFactory(cellData-> cellData.getValue().checkedProperty());
        tblProgram.setEditable(true);

        loadDataToProgramsTbl();

    }

    private void loadDataToProgramsTbl() {
        List<TherapyProgramDTO> therapyProgramDTOList = therapyProgramBO.getAll();

        for (TherapyProgramDTO therapyProgramDTO : therapyProgramDTOList) {
            tblProgram.getItems().add(new ProgramSelectTM(therapyProgramDTO.getProgramId(), therapyProgramDTO.getProgramName(), therapyProgramDTO.getFee()));
        }
    }

    private void resetCheckboxes() {
        for (ProgramSelectTM item : tblProgram.getItems()) {
            item.setChecked(false);
        }
        tblProgram.getSelectionModel().clearSelection();
    }

    private void addSelectionListener(ProgramSelectTM item) {
        item.checkedProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal) {
                if (!selectedProgramIds.contains(item.getProgramId())) {
                    selectedProgramIds.add(item.getProgramId());
                    System.out.println("add id: " + item.getProgramId());
                }
            } else {
                selectedProgramIds.remove(item.getProgramId());
                System.out.println("remove id: " + item.getProgramId());
            }
        });
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setCellValues();

        setCellValuesForProgramSelector();

        refreshPage();

        tblProgram.getItems().forEach(this::addSelectionListener);
    }

    @FXML
    void onBtnSearchClick(ActionEvent event) throws IOException {
        System.out.println("search clicked");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/therapistScheduleAndAvailability.fxml"));
        Parent load = loader.load();

        TherapistScheduleAvailabilityController controller = loader.getController();
        controller.setUp(tblTherapist.getSelectionModel().getSelectedItem().getId(), tblTherapist.getSelectionModel().getSelectedItem().getName());


        Stage stage = new Stage();
        stage.setScene(new Scene(load));
        stage.setTitle("Therapist Schedule and Availability");

        stage.initModality(Modality.APPLICATION_MODAL);
        Window underWindow = btnSearch.getScene().getWindow();
        stage.initOwner(underWindow);

        stage.showAndWait();

    }
}
