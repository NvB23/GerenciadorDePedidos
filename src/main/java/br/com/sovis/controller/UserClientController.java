package br.com.sovis.controller;

import br.com.sovis.dao.UserClientDAO;

import java.sql.SQLException;
import java.util.ArrayList;

public class UserClientController {
    private final UserClientDAO userClientDAO = new UserClientDAO();
    public ArrayList<Long> getUserClientByIdClient(Long idClient) throws SQLException {
        return userClientDAO.getUserClientByIdClient(String.valueOf(idClient));
    }
}
