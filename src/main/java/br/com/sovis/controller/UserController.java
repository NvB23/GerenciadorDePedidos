package br.com.sovis.controller;

import br.com.sovis.dao.UserDAO;
import br.com.sovis.model.User;

import java.sql.SQLException;
import java.util.ArrayList;

public class UserController {
    private final UserDAO userDAO = new UserDAO();

    public boolean createUser(User user) throws SQLException {
        return userDAO.createUser(
                user.getEmail(),
                user.getPassword()
        );
    }

    public boolean updateUser(Long id, User userModify) throws SQLException {
        return userDAO.updateUser(
                String.valueOf(id),
                userModify.getEmail(),
                userModify.getPassword()
        );
    }

    public boolean deleteUser(Long id) throws SQLException {
        return userDAO.deleteUser(String.valueOf(id));
    }

    public ArrayList<User> getUsers() throws SQLException {
        return userDAO.getUsers();
    }

    public User getUserById(Long id) throws SQLException {
        return userDAO.getUserById(String.valueOf(id));
    }
}
