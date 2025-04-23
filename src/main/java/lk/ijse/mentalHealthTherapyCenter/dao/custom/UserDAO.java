package lk.ijse.mentalHealthTherapyCenter.dao.custom;

import lk.ijse.mentalHealthTherapyCenter.dao.CrudDAO;
import lk.ijse.mentalHealthTherapyCenter.entity.User;

public interface UserDAO extends CrudDAO<User, String> {
    String getRoleByUsername(String username);
}
