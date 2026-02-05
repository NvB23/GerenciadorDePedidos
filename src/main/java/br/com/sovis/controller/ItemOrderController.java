package br.com.sovis.controller;

import br.com.sovis.dao.ItemOrderDAO;
import br.com.sovis.model.ItemOrder;

import java.sql.SQLException;
import java.util.ArrayList;

public class ItemOrderController {
    private final ItemOrderDAO itemOrderDAO = new ItemOrderDAO();

    public ArrayList<ItemOrder> getItemOrdersById(Long idOrder) throws SQLException {
        return itemOrderDAO.getItemOderByIdOrder(String.valueOf(idOrder));
    }

    public ArrayList<ItemOrder> getItemOrders() throws SQLException {
        return itemOrderDAO.getItemOrders();
    }
}
