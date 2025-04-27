package lk.ijse.mentalHealthTherapyCenter.dto;

import lk.ijse.mentalHealthTherapyCenter.entity.Registration;
import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class PaymentDTO {
    private int id;
    private Double amount;
    private LocalDate paymentDate;
    private String invoiceNumber;
    private String paymentMethod;
    private String status;
    private Registration registration;
}
