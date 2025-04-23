package lk.ijse.mentalHealthTherapyCenter.bo.custom.impl;

import lk.ijse.mentalHealthTherapyCenter.bo.custom.UserBO;
import lk.ijse.mentalHealthTherapyCenter.dao.DAOFactory;
import lk.ijse.mentalHealthTherapyCenter.dao.custom.UserDAO;
import lk.ijse.mentalHealthTherapyCenter.dto.UserDTO;
import lk.ijse.mentalHealthTherapyCenter.entity.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserBOImpl implements UserBO {

    UserDAO dao = DAOFactory.getInstance().getDAO(DAOFactory.DAOType.User);

    @Override
    public boolean save(UserDTO customerDTO) {
        User user = new User();

        user.setId(customerDTO.getId());
        user.setName(customerDTO.getName());
        user.setPassword(customerDTO.getPassword());
        user.setRole(customerDTO.getRole());
        user.setContact(customerDTO.getContact());
        user.setEmail(customerDTO.getEmail());

        return dao.save(user);
    }

    @Override
    public boolean update(UserDTO customerDTO) {
        User user = new User();

        user.setId(customerDTO.getId());
        user.setName(customerDTO.getName());
        user.setPassword(customerDTO.getPassword());
        user.setRole(customerDTO.getRole());
        user.setContact(customerDTO.getContact());
        user.setEmail(customerDTO.getEmail());

        return dao.update(user);
    }

    @Override
    public boolean deleteByPk(String pk) {
        return dao.deleteByPk(pk);
    }

    @Override
    public List<UserDTO> getAll() {
        ArrayList<UserDTO> userDTOArrayList = new ArrayList<>();
        List<User> entityList = dao.getAll();

        for (User user : entityList) {
            userDTOArrayList.add(new UserDTO(
                    user.getId(),
                    user.getName(),
                    user.getPassword(),
                    user.getRole(),
                    user.getContact(),
                    user.getEmail()
            ));
        }
        return userDTOArrayList;
    }

    @Override
    public Optional<UserDTO> findByPk(String pk) {
        return Optional.empty();
    }

    @Override
    public Optional<String> getLastPk() {
        return Optional.empty();
    }

    @Override
    public ArrayList<String> getAllCustomerIds() {
        return null;
    }

    @Override
    public String getRoleByUsername(String username) {
        return dao.getRoleByUsername(username);
    }
}
