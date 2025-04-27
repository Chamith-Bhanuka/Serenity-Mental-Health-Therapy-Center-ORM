package lk.ijse.mentalHealthTherapyCenter.bo.custom.impl;

import lk.ijse.mentalHealthTherapyCenter.bo.BOFactory;
import lk.ijse.mentalHealthTherapyCenter.bo.custom.PaymentBO;
import lk.ijse.mentalHealthTherapyCenter.bo.custom.RegistrationBO;
import lk.ijse.mentalHealthTherapyCenter.config.FactoryConfiguration;
import lk.ijse.mentalHealthTherapyCenter.dao.DAOFactory;
import lk.ijse.mentalHealthTherapyCenter.dao.custom.RegistrationDAO;
import lk.ijse.mentalHealthTherapyCenter.dto.PaymentDTO;
import lk.ijse.mentalHealthTherapyCenter.dto.RegistrationDTO;
import lk.ijse.mentalHealthTherapyCenter.entity.*;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RegistrationBOImpl implements RegistrationBO {

    FactoryConfiguration factoryConfiguration = FactoryConfiguration.getInstance();

    RegistrationDAO dao = DAOFactory.getInstance().getDAO(DAOFactory.DAOType.Registration);

    PaymentBO paymentBO = (PaymentBO) BOFactory.getInstance().getBO(BOFactory.BOType.Payment);

    @Override
    public void addRegistration(List<TherapyProgram> selectedPrograms, PaymentDTO paymentDTO, Patient patient) {
        Session session = factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();


        try {

            for (TherapyProgram program : selectedPrograms) {
                Registration registration = new Registration();
                registration.setPatient(patient);
                registration.setTherapyProgram(program);
                registration.setRegistrationDate(LocalDate.now());

                Payment payment = new Payment();
                payment.setAmount(paymentDTO.getAmount());
                payment.setPaymentDate(LocalDate.now());
                payment.setInvoiceNumber(paymentBO.generateInvoiceNumber()); //here
                payment.setPaymentMethod(paymentDTO.getPaymentMethod());
                payment.setStatus(paymentDTO.getStatus());

                registration.setPayment(payment);
                payment.setRegistration(registration);


                System.out.println("/***************************************************/");
                System.out.println("val of reg - " + registration.getRegistrationDate().toString());
                System.out.println("val of reg - " + patient.getName());
                System.out.println("val of reg - " + program.getProgramName());
                System.out.println("val of reg - " + payment.getInvoiceNumber());
                System.out.println("/***************************************************/");


                session.persist(registration);
            }
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
            System.out.println("<-----unable to add registration----->");
            throw new RuntimeException(e);
        } finally {
            session.close();
        }
    }

    @Override
    public void updateRegistration(RegistrationDTO registrationDTO) {
        Registration registration = new Registration();

        registration.setId(registrationDTO.getId());
        registration.setRegistrationDate(registrationDTO.getRegistrationDate());
        registration.setPatient(registrationDTO.getPatient());
        registration.setTherapyProgram(registrationDTO.getTherapyProgram());
        registration.setPayment(registrationDTO.getPayment());

        dao.update(registration);
    }

    @Override
    public void deleteRegistration(Long registrationId) {
        dao.deleteByPk(String.valueOf(registrationId));
    }

    @Override
    public Registration getRegistrationById(Long registrationId) {
        Optional<Registration> registrationOptional = dao.findByPk(String.valueOf(registrationId));
        return registrationOptional.orElse(null);
    }

    @Override
    public List<RegistrationDTO> getAllRegistrations() {
        ArrayList<RegistrationDTO> registrationDTOArrayList = new ArrayList<>();
        List<Registration> entityList = dao.getAll();

        for (Registration registration : entityList) {
            registrationDTOArrayList.add(new RegistrationDTO(
                    registration.getId(),
                    registration.getRegistrationDate(),
                    registration.getPatient(),
                    registration.getTherapyProgram(),
                    registration.getPayment()
            ));
        }
        return registrationDTOArrayList;
    }

    @Override
    public Patient getSavedPatient(Patient patient) {
        return null;
    }

    @Override
    public Optional<Registration> findByPk(String pk) {
        return dao.findByPk(pk);
    }
}
