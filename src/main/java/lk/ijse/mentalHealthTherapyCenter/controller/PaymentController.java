package lk.ijse.mentalHealthTherapyCenter.controller;

import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import javafx.util.Duration;
import lk.ijse.mentalHealthTherapyCenter.AppInitializer;
import lk.ijse.mentalHealthTherapyCenter.bo.BOFactory;
import lk.ijse.mentalHealthTherapyCenter.bo.custom.PatientBO;
import lk.ijse.mentalHealthTherapyCenter.bo.custom.PaymentBO;
import lk.ijse.mentalHealthTherapyCenter.bo.custom.RegistrationBO;
import lk.ijse.mentalHealthTherapyCenter.bo.custom.TherapyProgramBO;
import lk.ijse.mentalHealthTherapyCenter.dto.PatientDTO;
import lk.ijse.mentalHealthTherapyCenter.dto.PaymentDTO;
import lk.ijse.mentalHealthTherapyCenter.entity.Patient;
import lk.ijse.mentalHealthTherapyCenter.entity.Registration;
import lk.ijse.mentalHealthTherapyCenter.entity.TherapyProgram;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
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

    /*
    * payment controller
    *  appeared by action result of onRegisterPatientClicked*/

    PaymentBO paymentBO = (PaymentBO) BOFactory.getInstance().getBO(BOFactory.BOType.Payment);
    PatientBO patientBO = (PatientBO) BOFactory.getInstance().getBO(BOFactory.BOType.Patient);
    TherapyProgramBO therapyProgramBO = (TherapyProgramBO) BOFactory.getInstance().getBO(BOFactory.BOType.TherapyProgram);
    RegistrationBO registrationBO = (RegistrationBO) BOFactory.getInstance().getBO(BOFactory.BOType.Registration);

    PaymentDTO paymentDTO = new PaymentDTO();

    @FXML
    void onCardResetClick(ActionEvent event) {

    }

    @FXML
    void handleProceedAction(ActionEvent event) {
        System.out.println("proceed clicked");

        String name = txtName.getText();
        String email = txtEmail.getText();
        String phone = txtPhone.getText();
        String address = txtAddress.getText();
        String gender = txtGender.getText();
        String age = txtAge.getText();
        String history = txtHistory.getText();

        if (name.isEmpty() || email.isEmpty() || phone.isEmpty() || address.isEmpty() || gender.isEmpty() || age.isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Unable to save without compulsory data", ButtonType.OK).show();
            return;
        }

        int intAge;

        try {
            intAge = Integer.parseInt(age);
        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.WARNING, "Invalid age", ButtonType.OK).show();
            return;
        }

        Patient patient = new Patient();
        patient.setName(name);
        patient.setEmail(email);
        patient.setPhone(phone);
        patient.setAddress(address);
        patient.setGender(gender);
        patient.setAge(intAge);
        patient.setMedicalHistory(history);

        patientBO.save(patient);

        List<TherapyProgram> programs = paymentBO.getTherapyProgramsByIds(selectedIdList);

        double payment = therapyProgramBO.calculateTotalFee(programs);

        if (cmbPaymentMethod.getSelectionModel().getSelectedItem() == null) {
            return;
        }

        paymentDTO.setAmount(payment);
        paymentDTO.setPaymentDate(LocalDate.now());
        paymentDTO.setInvoiceNumber(paymentBO.generateInvoiceNumber());
        paymentDTO.setPaymentMethod(cmbPaymentMethod.getSelectionModel().getSelectedItem());
        paymentDTO.setStatus("completed");

        registrationBO.addRegistration(programs, paymentDTO, patient);

        vbxMainForm.setVisible(false);
        vbxMsg.setVisible(true);

//        PauseTransition pause = new PauseTransition(Duration.seconds(1.5));
//        pause.setOnFinished(e -> {
//            vbxMsg.setVisible(false);
//            vbxMainForm.setVisible(true);
//        });
//        pause.play();

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

    @FXML
    void btnPrintBillClick(ActionEvent event) throws IOException {
        System.out.println("printBillClick");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/invoice.fxml"));
        Scene scene = new Scene(loader.load());
        Stage stage = new Stage();
        stage.setTitle("Payment-Invoice");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/images/icon1.png")));
        stage.show();

        InvoiceController invoiceController = loader.getController();
        invoiceController.setUp(paymentDTO);

    }


}
