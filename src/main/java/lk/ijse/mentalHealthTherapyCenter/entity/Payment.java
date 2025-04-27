package lk.ijse.mentalHealthTherapyCenter.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "payment")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class Payment implements SuperEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private Double amount;
    private LocalDate paymentDate;

    @Column(unique = true)
    private String invoiceNumber;

    private String paymentMethod;

    private String status;

    @OneToOne
    @JoinColumn(name = "registration_id", nullable = false)
    private Registration registration;
}
