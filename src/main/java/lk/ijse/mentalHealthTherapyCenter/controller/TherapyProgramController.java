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
import lk.ijse.mentalHealthTherapyCenter.bo.custom.TherapyProgramBO;
import lk.ijse.mentalHealthTherapyCenter.dto.TherapyProgramDTO;
import lk.ijse.mentalHealthTherapyCenter.view.tdm.TherapyProgramTM;

import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class TherapyProgramController implements Initializable {


    @FXML
    private Button btnDelete;

    @FXML
    private Button btnReset;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;

    @FXML
    private ComboBox<String> cmbDuration;

    @FXML
    private TableColumn<String, TherapyProgramTM> colDuration;

    @FXML
    private TableColumn<Double, TherapyProgramTM> colFee;

    @FXML
    private TableColumn<String, TherapyProgramTM> colProgramId;

    @FXML
    private TableColumn<String, TherapyProgramTM> colProgramName;

    @FXML
    private TableView<TherapyProgramTM> tblProgram;

    @FXML
    private TextField txtDuration;

    @FXML
    private TextField txtFee;

    @FXML
    private TextField txtProgramId;

    @FXML
    private TextField txtProgramName;


    TherapyProgramBO therapyProgramBO = (TherapyProgramBO) BOFactory.getInstance().getBO(BOFactory.BOType.TherapyProgram);


    @FXML
    void onDeleteClick(ActionEvent event) {
        System.out.println("Delete Clicked");

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this program?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.YES) {

            boolean isDeleted = therapyProgramBO.deleteByPk(txtProgramId.getText());

            if (isDeleted) {
                new Alert(Alert.AlertType.INFORMATION, "Program Deleted Successfully").show();
                refreshPage();
            } else {
                new Alert(Alert.AlertType.ERROR, "Program Deletion Failed").show();
            }

        }

    }

    @FXML
    void onResetClick(ActionEvent event) {
        System.out.println("Reset Clicked");
        refreshPage();
    }

    @FXML
    void onSaveClick(ActionEvent event) {
        System.out.println("Save Clicked");

        String id = txtProgramId.getText();
        String name = txtProgramName.getText();
        String duration = txtDuration.getText() + cmbDuration.getValue();
        double fee = Double.parseDouble(txtFee.getText());

        TherapyProgramDTO therapyProgramDTO = new TherapyProgramDTO(id, name, duration, fee);

        boolean isSaved = therapyProgramBO.save(therapyProgramDTO);

        if (isSaved) {
            new Alert(Alert.AlertType.INFORMATION, "Program Saved Successfully").show();
            refreshPage();
        } else {
            new Alert(Alert.AlertType.ERROR, "Program Saving Failed").show();
        }

    }

    @FXML
    void onUpdateClick(ActionEvent event) {
        System.out.println("Update Clicked");

        String id = txtProgramId.getText();
        String name = txtProgramName.getText();
        String duration = txtDuration.getText() + cmbDuration.getValue();
        double fee = Double.parseDouble(txtFee.getText());

        TherapyProgramDTO therapyProgramDTO = new TherapyProgramDTO(id, name, duration, fee);

        boolean isUpdated = therapyProgramBO.update(therapyProgramDTO);

        if (isUpdated) {
            new Alert(Alert.AlertType.INFORMATION, "Program Updated Successfully").show();
            refreshPage();
        } else {
            new Alert(Alert.AlertType.ERROR, "Program Updating Failed").show();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setTableColumns();

        refreshPage();

        ObservableList<String> durations = FXCollections.observableArrayList("Months", "Weeks", "Days");
        cmbDuration.setItems(durations);
    }

    private void setTableColumns() {
        colProgramId.setCellValueFactory(new PropertyValueFactory<>("programId"));
        colProgramName.setCellValueFactory(new PropertyValueFactory<>("programName"));
        colDuration.setCellValueFactory(new PropertyValueFactory<>("duration"));
        colFee.setCellValueFactory(new PropertyValueFactory<>("fee"));
    }

    private void refreshPage() {
        refreshTable();

        txtProgramId.setText("");
        txtProgramName.setText("");
        txtDuration.setText("");
        txtFee.setText("");
        cmbDuration.getSelectionModel().clearSelection();

        btnSave.setDisable(false);

        btnDelete.setDisable(false);
        btnReset.setDisable(false);
        btnUpdate.setDisable(false);
    }

    private void refreshTable() {
        ArrayList<TherapyProgramDTO> therapyProgramDTOS = (ArrayList<TherapyProgramDTO>) therapyProgramBO.getAll();

        ObservableList<TherapyProgramTM> therapyProgramTMS = FXCollections.observableArrayList();

        for (TherapyProgramDTO therapyProgramDTO : therapyProgramDTOS) {
            TherapyProgramTM therapyProgramTM = new TherapyProgramTM(
                    therapyProgramDTO.getProgramId(),
                    therapyProgramDTO.getProgramName(),
                    therapyProgramDTO.getDuration(),
                    therapyProgramDTO.getFee()
            );
            therapyProgramTMS.add(therapyProgramTM);
        }
        tblProgram.setItems(therapyProgramTMS);
    }

    @FXML
    void onTblProgramClick(MouseEvent event) {
        TherapyProgramTM selectedItem = tblProgram.getSelectionModel().getSelectedItem();

        if (selectedItem != null) {
            txtProgramId.setText(selectedItem.getProgramId());
            txtProgramName.setText(selectedItem.getProgramName());
            txtDuration.setText(selectedItem.getDuration());
            txtFee.setText(selectedItem.getFee().toString());

            btnSave.setDisable(true);

            btnDelete.setDisable(false);
            btnReset.setDisable(false);
            btnUpdate.setDisable(false);
        }
    }
}
