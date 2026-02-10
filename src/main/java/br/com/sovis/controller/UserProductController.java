package br.com.sovis.controller;

import br.com.sovis.dao.UserClientDAO;
import br.com.sovis.dao.UserProductDAO;

import java.sql.SQLException;
import java.util.ArrayList;

public class UserProductController {
    private final UserProductDAO userProductDAO = new UserProductDAO();
    public ArrayList<Long> getUserProductByIdProduct(Long idProduct) throws SQLException {
        return userProductDAO.getUserProductByIdProduct(String.valueOf(idProduct));
    }
}
