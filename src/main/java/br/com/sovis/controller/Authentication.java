package br.com.sovis.controller;

import br.com.sovis.dao.UserDAO;
import br.com.sovis.model.User;

import java.sql.SQLException;

public class Authentication {
    private final UserDAO userDAO = new UserDAO();

    public boolean login(String email, String password) throws SQLException {
        User result = userDAO.getUserByEmailSenha(email, password);
        if (result != null) {
            return true;
        }
        return false;
    }
}
