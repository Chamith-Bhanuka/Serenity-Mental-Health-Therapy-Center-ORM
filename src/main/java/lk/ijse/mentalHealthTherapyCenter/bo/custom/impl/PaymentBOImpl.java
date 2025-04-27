package lk.ijse.mentalHealthTherapyCenter.bo.custom.impl;

import lk.ijse.mentalHealthTherapyCenter.bo.BOFactory;
import lk.ijse.mentalHealthTherapyCenter.bo.custom.PaymentBO;
import lk.ijse.mentalHealthTherapyCenter.bo.custom.TherapyProgramBO;
import lk.ijse.mentalHealthTherapyCenter.config.FactoryConfiguration;
import lk.ijse.mentalHealthTherapyCenter.entity.TherapyProgram;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PaymentBOImpl implements PaymentBO {
    FactoryConfiguration factoryConfiguration = FactoryConfiguration.getInstance();
    TherapyProgramBO therapyProgramBO = (TherapyProgramBO) BOFactory.getInstance().getBO(BOFactory.BOType.TherapyProgram);
    @Override
    public List<TherapyProgram> getTherapyProgramsByIds(List<String> idList) {
        Session session = factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();

        List<TherapyProgram> listOfPrograms = new ArrayList<>();

        try {
            listOfPrograms = therapyProgramBO.getTherapyProgramsBySelectedIds(session, idList);
            transaction.commit();
            return listOfPrograms;
        } catch (Exception e) {
            transaction.rollback();
            System.out.println("fail to load therapy programs by paymentBoImpl");
            e.printStackTrace();
        } finally {
            session.close();
        }
        return listOfPrograms;
    }

    @Override
    public String generateInvoiceNumber() {
        return "INV-" + System.currentTimeMillis() + "-" + UUID.randomUUID().toString().substring(0, 4);
    }
}
