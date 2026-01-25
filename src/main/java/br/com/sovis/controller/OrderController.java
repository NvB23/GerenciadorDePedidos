package br.com.sovis.controller;

import br.com.sovis.dao.ItemOrderDAO;
import br.com.sovis.dao.OrderDAO;
import br.com.sovis.dao.ProductDAO;
import br.com.sovis.model.ItemOrder;
import br.com.sovis.model.Order;
import br.com.sovis.model.Product;
import br.com.sovis.model.enums.OrderStatus;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class OrderController {
    private final OrderDAO orderDAO = new OrderDAO();
    private final ProductDAO productDAO = new ProductDAO();
    private final ItemOrderDAO itemOrderDAO = new ItemOrderDAO();

    public void createOrder(Order order, HashMap<Product, Integer> productsQuantity) throws SQLException {
        Double totalValue = 0.0;
        for (Map.Entry<Product, Integer> productQuant : productsQuantity.entrySet()) {
            totalValue += productQuant.getKey().getPrice() * productQuant.getValue();
        }

        Long idOrderInserted = orderDAO.createOrder(
                String.valueOf(order.getClient().getId()),
                String.valueOf(totalValue),
                order.getOrderDate(),
                String.valueOf(order.getStatusPedido())
        );

        if (idOrderInserted == null) {
            return;
        }

        order.setId(idOrderInserted);
        order.setTotalValue(totalValue);

        for (Map.Entry<Product, Integer> produtoQuant : productsQuantity.entrySet()) {
            ItemOrder itemOrder = new ItemOrder(
                    order,
                    produtoQuant.getKey(),
                    produtoQuant.getValue(),
                    produtoQuant.getKey().getPrice() * produtoQuant.getValue()
            );

            boolean itemOrderInserted = itemOrderDAO.createItemOrder(
                    String.valueOf(itemOrder.getOrder().getId()),
                    String.valueOf(itemOrder.getProduct().getId()),
                    String.valueOf(itemOrder.getQuantity()),
                    String.valueOf(itemOrder.getItemValue())
            );
            if (!itemOrderInserted) return;
        }

    }

    public void updateOrder(Long id, Order order, HashMap<Long, Integer> productsQuantity) throws SQLException {
        Double newTotalValue = 0.0;
        for (Map.Entry<Long, Integer> productQuant : productsQuantity.entrySet()) {
            newTotalValue += productDAO.getProductById(
                    String.valueOf(productQuant.getKey())).getPrice() * productQuant.getValue();
        }

        Long idOrderUpdated = orderDAO.updateOrder(
                String.valueOf(id),
                String.valueOf(order.getClient().getId()),
                String.valueOf(newTotalValue),
                order.getOrderDate(),
                String.valueOf(order.getStatusPedido())
        );

        if (idOrderUpdated == null) {
            return;
        }

        order.setId(idOrderUpdated);

        itemOrderDAO.deleteItemOrderByIdOrder(String.valueOf(order.getId()));

        for (Map.Entry<Long, Integer> productQuant : productsQuantity.entrySet()) {
            ItemOrder itemOrder = new ItemOrder(
                    order,
                    productDAO.getProductById(String.valueOf(productQuant.getKey())),
                    productQuant.getValue(),
                    productDAO.getProductById(
                            String.valueOf(productQuant.getKey())).getPrice() * productQuant.getValue()
            );

            boolean itemOrderUpdated = itemOrderDAO.createItemOrder(
                    String.valueOf(itemOrder.getOrder().getId()),
                    String.valueOf(itemOrder.getProduct().getId()),
                    String.valueOf(itemOrder.getQuantity()),
                    String.valueOf(itemOrder.getItemValue())
            );
            if (!itemOrderUpdated) return;
        }
    }

    public void deleteOrder(Long id) throws SQLException {
        ArrayList<ItemOrder> itemsForDelete = itemOrderDAO.getItemOderByIdOrder(String.valueOf(id));

        for (ItemOrder itemOrder : itemsForDelete) {
            if (!itemOrderDAO.deleteItemOrder(String.valueOf(itemOrder.getId()))) {
                return;
            }
        }

        orderDAO.deleteOrder(String.valueOf(id));

    }

    public ArrayList<Order> getOrders() throws SQLException {
        return orderDAO.getOrders();
    }

    public Order getOrdersById(Long id) throws SQLException {
        return orderDAO.getOrderById(String.valueOf(id));
    }

    public void updateOrderStatus(Long id, OrderStatus newOrderStatus) throws SQLException {
        orderDAO.updateStatusOrder(String.valueOf(id), String.valueOf(newOrderStatus));
    }
}
