package lk.ijse.mentalHealthTherapyCenter.bo.custom;

import lk.ijse.mentalHealthTherapyCenter.bo.SuperBO;
import lk.ijse.mentalHealthTherapyCenter.dto.PatientDTO;
import lk.ijse.mentalHealthTherapyCenter.dto.PaymentDTO;
import lk.ijse.mentalHealthTherapyCenter.dto.RegistrationDTO;
import lk.ijse.mentalHealthTherapyCenter.entity.Patient;
import lk.ijse.mentalHealthTherapyCenter.entity.Registration;
import lk.ijse.mentalHealthTherapyCenter.entity.TherapyProgram;

import java.util.List;
import java.util.Optional;

public interface RegistrationBO extends SuperBO {
    void addRegistration(List<TherapyProgram> selectedPrograms, PaymentDTO payment, Patient patient);
    void updateRegistration(RegistrationDTO registrationDTO);
    void deleteRegistration(Long registrationId);
    Registration getRegistrationById(Long registrationId);
    List<RegistrationDTO> getAllRegistrations();
    Patient getSavedPatient(Patient patient);
    Optional<Registration> findByPk(String pk);
}
