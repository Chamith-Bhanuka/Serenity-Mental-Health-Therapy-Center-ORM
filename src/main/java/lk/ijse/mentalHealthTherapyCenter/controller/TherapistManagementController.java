package lk.ijse.mentalHealthTherapyCenter.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import lk.ijse.mentalHealthTherapyCenter.bo.BOFactory;
import lk.ijse.mentalHealthTherapyCenter.bo.custom.TherapistBO;
import lk.ijse.mentalHealthTherapyCenter.dto.TherapistDTO;
import lk.ijse.mentalHealthTherapyCenter.entity.Session;
import lk.ijse.mentalHealthTherapyCenter.view.tdm.TherapistTM;

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
    private TextField txtId;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtPhone;

    @FXML
    private TextField txtSpecialization;

    TherapistBO therapistBO = (TherapistBO) BOFactory.getInstance().getBO(BOFactory.BOType.Therapist);

    @FXML
    void onDelete(ActionEvent event) {
        System.out.println("Delete clicked");

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this therapist?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.YES) {

            boolean isDeleted = therapistBO.deleteByPk(txtId.getText());

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

        int id  = Integer.parseInt(txtId.getText());
        String name = txtName.getText();
        String email = txtEmail.getText();
        String phone = txtPhone.getText();
        String specialization = txtSpecialization.getText();
        List<Session> sessionList = new ArrayList<>();

        TherapistDTO therapistDTO = new TherapistDTO(id, name, email, phone, specialization, sessionList);

        boolean isSaved = therapistBO.save(therapistDTO);

        if (isSaved) {
            new Alert(Alert.AlertType.INFORMATION, "Therapist Saved..!").show();
            refreshPage();
        } else {
            new Alert(Alert.AlertType.ERROR, "Therapist Not Saved..!").show();
        }
    }

    @FXML
    void onUpdate(ActionEvent event) {
        System.out.println("Update clicked");

        int id  = Integer.parseInt(txtId.getText());
        String name = txtName.getText();
        String email = txtEmail.getText();
        String phone = txtPhone.getText();
        String specialization = txtSpecialization.getText();
        List<Session> sessionList = new ArrayList<>();

        TherapistDTO therapistDTO = new TherapistDTO(id, name, email, phone, specialization, sessionList);

        boolean isUpdated = therapistBO.update(therapistDTO);

        if (isUpdated) {
            new Alert(Alert.AlertType.INFORMATION, "Therapist Updated..!").show();
            refreshPage();
        } else {
            new Alert(Alert.AlertType.ERROR, "Therapist Not Updated..!").show();
        }
    }

    @FXML
    void onTblTherapistClick(MouseEvent event) {
        TherapistTM selectedItem = tblTherapist.getSelectionModel().getSelectedItem();

        if (selectedItem != null) {
            txtId.setText(String.valueOf(selectedItem.getId()));
            txtName.setText(selectedItem.getName());
            txtPhone.setText(selectedItem.getPhone());
            txtEmail.setText(selectedItem.getEmail());
            txtSpecialization.setText(selectedItem.getSpecialization());

            btnSave.setDisable(true);

            btnUpdate.setDisable(false);
            btnDelete.setDisable(false);
            btnReset.setDisable(false);
        }
    }

    private void refreshPage() {
        refreshTable();

        txtId.setText("");
        txtName.setText("");
        txtPhone.setText("");
        txtEmail.setText("");
        txtSpecialization.setText("");

        btnSave.setDisable(false);

        btnDelete.setDisable(true);
        btnReset.setDisable(true);
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
    }

    private void setCellValues() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        colSpecialization.setCellValueFactory(new PropertyValueFactory<>("specialization"));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setCellValues();

        refreshPage();
    }
}
