package lk.ijse.mentalHealthTherapyCenter.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import lk.ijse.mentalHealthTherapyCenter.entity.Registration;
import lombok.Getter;
import lombok.Setter;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

@Setter
public class PaymentController implements Initializable {

    @FXML
    private Button btnProceed;

    @FXML
    private ComboBox<String> cmbPaymentMethod;

    @FXML
    private HBox hbxCashPayment;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtAge;

    @FXML
    private TextField txtCVCNumber;

    @FXML
    private TextField txtCardHolderName;

    @FXML
    private TextField txtCardNumber;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtExpMonth;

    @FXML
    private TextField txtExpYear;

    @FXML
    private TextField txtGender;

    @FXML
    private TextArea txtHistory;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtPhone;

    @FXML
    private TextArea txtPrograms;

    @FXML
    private VBox vbxCardPayment;

    @FXML
    private VBox vbxMainForm;

    @FXML
    private VBox vbxMsg;

    @FXML
    private Button btnProceedFromCard;

    @FXML
    private ImageView imgCard;

    @FXML
    private VBox rootPane;



    @FXML
    void onCardResetClick(ActionEvent event) {

    }

    @FXML
    void handleProceedAction(ActionEvent event) {

    }

    @Getter
    private String name;
    private String email;
    private String phone;
    private String address;
    private String gender;
    private String medicalHistory;
    private int age;
    private List<String> selectedIdList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Platform.runLater(() -> cmbPaymentMethod.requestFocus());

        cmbPaymentMethod.getItems().addAll("CASH", "CARD");

        cmbPaymentMethod.setOnAction(event -> {
            String selected = cmbPaymentMethod.getSelectionModel().getSelectedItem();
            if (selected.equals("CASH")) {
                btnProceed.setVisible(true);
                vbxCardPayment.setVisible(false);
                hbxCashPayment.setVisible(true);
            } else if (selected.equals("CARD")) {
                btnProceed.setVisible(false);
                vbxCardPayment.setVisible(true);
                hbxCashPayment.setVisible(false);
            }
        });

    }

    public void setup(String patientName, String patientEmail, String patientPhone, String patientAddress, String patientGender, String medicalHistory, int age, List<String> selectedIdList) {
        this.name = patientName;
        txtName.setText(patientName);

        this.email = patientEmail;
        txtEmail.setText(patientEmail);

        this.phone = patientPhone;
        txtPhone.setText(patientPhone);

        this.address = patientAddress;
        txtAddress.setText(patientAddress);

        this.gender = patientGender;
        txtGender.setText(patientGender);

        this.medicalHistory = medicalHistory;
        txtHistory.setText(medicalHistory);

        this.age = age;
        txtAge.setText(String.valueOf(age));

        this.selectedIdList = selectedIdList;
        updateTextArea();
        System.out.println("PaymentController setup with selectedIdList: " + this.selectedIdList);

    }

    private void updateTextArea() {
        String formattedText = String.join("\n", selectedIdList); // Joins list items with line breaks
        txtPrograms.setText(formattedText);
    }


}
