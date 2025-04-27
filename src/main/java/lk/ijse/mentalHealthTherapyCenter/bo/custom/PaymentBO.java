package lk.ijse.mentalHealthTherapyCenter.bo.custom;

import lk.ijse.mentalHealthTherapyCenter.bo.SuperBO;
import lk.ijse.mentalHealthTherapyCenter.entity.TherapyProgram;

import java.util.List;

public interface PaymentBO extends SuperBO {
    List<TherapyProgram> getTherapyProgramsByIds(List<String> idList);
    String generateInvoiceNumber();
}
