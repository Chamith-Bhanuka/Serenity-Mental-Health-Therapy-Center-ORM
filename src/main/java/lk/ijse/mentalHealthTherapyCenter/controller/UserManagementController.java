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
import lk.ijse.mentalHealthTherapyCenter.bo.custom.UserBO;
import lk.ijse.mentalHealthTherapyCenter.dto.UserDTO;
import lk.ijse.mentalHealthTherapyCenter.util.PasswordUtil;
import lk.ijse.mentalHealthTherapyCenter.view.tdm.UserTM;

import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class UserManagementController implements Initializable {

    UserBO userBO = (UserBO) BOFactory.getInstance().getBO(BOFactory.BOType.User);

    int selectedId = 0;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnReset;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;

    @FXML
    private ComboBox<String> cmbRole;

    @FXML
    private TableColumn<UserTM, String> colContact;

    @FXML
    private TableColumn<UserTM, String> colEmail;

    @FXML
    private TableColumn<UserTM, String> colName;

    @FXML
    private TableColumn<UserTM, String> colPassword;

    @FXML
    private TableColumn<UserTM, String> colRole;

    @FXML
    private TableColumn<UserTM, Integer> colId;

    @FXML
    private TableView<UserTM> tblUser;

    @FXML
    private TextField txtContact;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtPassword;

    @FXML
    private TextField txtUserName;

    @FXML
    void onDeleteClick(ActionEvent event) {
        System.out.println("delete clicked");

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this user?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> buttonType = alert.showAndWait();

        if (buttonType.get() == ButtonType.YES) {

            boolean isDeleted = userBO.deleteByPk(String.valueOf(selectedId));

            if (isDeleted) {
                new Alert(Alert.AlertType.INFORMATION, "User deleted successfully").show();
                refreshPage();
            } else {
                new Alert(Alert.AlertType.ERROR, "User could not be deleted").show();
            }
        }
    }

    @FXML
    void onResetClick(ActionEvent event) {
        System.out.println("reset clicked");
        refreshPage();
    }

    @FXML
    void onSaveClick(ActionEvent event) {
        System.out.println("save clicked");

        int id = 0;
        String name = txtUserName.getText();

        //Hash password before store
        String password = txtPassword.getText();
        String hashedPassword = PasswordUtil.hashPassword(password);

        String role = cmbRole.getSelectionModel().getSelectedItem().toString();
        String contact = txtContact.getText();
        String email = txtEmail.getText();

        UserDTO userDTO = new UserDTO(id, name, hashedPassword, role, contact, email);

        boolean isSaved = userBO.save(userDTO);

        if (isSaved) {
            new Alert(Alert.AlertType.INFORMATION, "User saved successfully..!", ButtonType.OK).show();
            refreshPage();
        } else {
            new Alert(Alert.AlertType.ERROR, "Failed to save user..!", ButtonType.OK).show();
        }
    }

    @FXML
    void onUpdateClick(ActionEvent event) {
        System.out.println("update clicked");

        int id = selectedId;
        String name = txtUserName.getText();
        String password = txtPassword.getText();
        String role = cmbRole.getSelectionModel().getSelectedItem().toString();
        String contact = txtContact.getText();
        String email = txtEmail.getText();

        UserDTO userDTO = new UserDTO(id, name, password, role, contact, email);

        boolean isUpdated = userBO.update(userDTO);

        if (isUpdated) {
            new Alert(Alert.AlertType.INFORMATION, "User updated successfully..!", ButtonType.OK).show();
            refreshPage();
        } else {
            new Alert(Alert.AlertType.ERROR, "Failed to update user..!", ButtonType.OK).show();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        setTableColumns();

        refreshPage();

        ObservableList<String> roles = FXCollections.observableArrayList("Admin", "Receptionist");
        cmbRole.setItems(roles);

    }

    private void setTableColumns() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colPassword.setCellValueFactory(new PropertyValueFactory<>("password"));
        colRole.setCellValueFactory(new PropertyValueFactory<>("role"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
    }

    private void refreshPage() {
        refreshTable();

        txtUserName.setText("");
        txtPassword.setText("");
        txtContact.setText("");
        txtEmail.setText("");
        cmbRole.getSelectionModel().clearSelection();

        btnSave.setDisable(false);

        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);
        btnReset.setDisable(true);
    }

    private void refreshTable() {
        ArrayList<UserDTO> userDTOS = (ArrayList<UserDTO>) userBO.getAll();

        ObservableList<UserTM> userTMS = FXCollections.observableArrayList();

        for (UserDTO userDTO : userDTOS) {
            UserTM userTM = new UserTM(
                    userDTO.getId(),
                    userDTO.getName(),
                    userDTO.getPassword(),
                    userDTO.getRole(),
                    userDTO.getContact(),
                    userDTO.getEmail()
            );
            userTMS.add(userTM);
        }
        tblUser.setItems(userTMS);
    }

    @FXML
    void onTblUserClick(MouseEvent event) {
        UserTM selectedItem = tblUser.getSelectionModel().getSelectedItem();

        if (selectedItem != null) {
            selectedId = selectedItem.getId();
            txtUserName.setText(selectedItem.getName());
            txtPassword.setText(selectedItem.getPassword());
            cmbRole.getSelectionModel().select(selectedItem.getRole());
            txtContact.setText(selectedItem.getContact());
            txtEmail.setText(selectedItem.getEmail());

            btnSave.setDisable(true);

            btnUpdate.setDisable(false);
            btnDelete.setDisable(false);
            btnReset.setDisable(false);

            txtPassword.setEditable(false);
        }
    }
}
