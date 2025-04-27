package lk.ijse.mentalHealthTherapyCenter.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import lk.ijse.mentalHealthTherapyCenter.dto.PaymentDTO;

import java.net.URL;
import java.util.ResourceBundle;

public class InvoiceController implements Initializable {

    @FXML
    private TextArea txtBillArea;


    PaymentDTO payment = new PaymentDTO();

    public void setUp(PaymentDTO payment) {
        this.payment = payment;

        StringBuilder sb = new StringBuilder();

        sb.append("Invoice Details\n\n");

        sb.append("✅ Invoice Number: ").append(payment.getInvoiceNumber()).append("\n\n");
        sb.append("🆔 Payment ID: ").append(payment.getId()).append("\n\n");
        sb.append("💰 Amount: $").append(String.format("%.2f", payment.getAmount())).append("\n\n");
        sb.append("📅 Payment Date: ").append(payment.getPaymentDate()).append("\n\n");
        sb.append("✔️ Status: ").append(payment.getStatus()).append("\n\n");
        sb.append("💳 Payment Method: ").append(payment.getPaymentMethod()).append("\n\n");


        txtBillArea.setText(sb.toString());

        txtBillArea.setEditable(false);
        txtBillArea.setWrapText(true);
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        txtBillArea.setStyle("-fx-font-size: 15px;");
    }
}
