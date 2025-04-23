package lk.ijse.mentalHealthTherapyCenter.bo.custom;

import lk.ijse.mentalHealthTherapyCenter.bo.SuperBO;
import lk.ijse.mentalHealthTherapyCenter.dto.UserDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface UserBO extends SuperBO {
    boolean save(UserDTO customerDTO);
    boolean update(UserDTO customerDTO);
    boolean deleteByPk(String pk);
    List<UserDTO> getAll();
    Optional<UserDTO> findByPk(String pk);
    Optional<String> getLastPk();
    ArrayList<String> getAllCustomerIds();
    String getRoleByUsername(String username);
}
