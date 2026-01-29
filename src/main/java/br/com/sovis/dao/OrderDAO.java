package br.com.sovis.dao;

import br.com.sovis.db.Database;
import br.com.sovis.model.Client;
import br.com.sovis.model.Order;
import br.com.sovis.model.enums.OrderStatus;
import totalcross.sql.Connection;
import totalcross.sql.PreparedStatement;
import totalcross.sql.Statement;
import totalcross.sql.ResultSet;

import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDAO {
    public Long createOrder(String idClient, String totalValue, String orderDate, String orderStatus) throws SQLException {
        Connection connection = Database.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO pedido(idCliente, valorTotal, dataPedido, statusPedido) VALUES(?, ?, ?, ?);");

        preparedStatement.setString(1, idClient);
        preparedStatement.setString(2, totalValue);
        preparedStatement.setString(3, orderDate);
        preparedStatement.setString(4, orderStatus);

        int i = preparedStatement.executeUpdate();

        if (i<= 0) {
            preparedStatement.close();
            connection.close();
            return null;
        }

        PreparedStatement preparedStatementId = connection.prepareStatement("SELECT MAX(id) FROM pedido");

        ResultSet resultSet = preparedStatementId.executeQuery();
        Long id = null;
        if (resultSet.next()) {
            id = resultSet.getLong(1);
        }

        preparedStatementId.close();
        preparedStatement.close();
        connection.close();
        return id;
    }

    public Long updateOrder(String id, String idClient, String totalValue, String orderDate, String orderStatus) throws SQLException {
        Connection connection = Database.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(
                "UPDATE pedido SET idCliente = ?, valorTotal = ?, dataPedido = ?, statusPedido = ? WHERE id = ?;");

        preparedStatement.setString(1, idClient);
        preparedStatement.setString(2, totalValue);
        preparedStatement.setString(3, orderDate);
        preparedStatement.setString(4, orderStatus);
        preparedStatement.setString(5, id);

        int i = preparedStatement.executeUpdate();

        if (i <= 0) {
            preparedStatement.close();
            connection.close();
            return null;
        }

        preparedStatement.close();
        connection.close();
        return Long.parseLong(id);
    }

    public void deleteOrder(String id) throws SQLException {
        Connection connection = Database.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(
                "DELETE FROM pedido WHERE id = ?;");

        preparedStatement.setString(1, id);

        preparedStatement.executeUpdate();

        preparedStatement.close();
        connection.close();
    }

    public ArrayList<Order> getOrders() throws SQLException {
        ArrayList<Order> orders = new ArrayList<>();
        Connection connection = Database.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(
                "SELECT * FROM pedido;");

        while (resultSet.next()) {
            String id = resultSet.getString("id");
            String idClient = resultSet.getString("idCliente");
            String totalValue = resultSet.getString("valorTotal");
            String orderDate = resultSet.getString("dataPedido");
            String statusPedido = resultSet.getString("statusPedido");
            Client client = new ClientDAO().getClient(idClient);
            Order order = new Order(
                    Long.parseLong(id),
                    client,
                    Double.parseDouble(totalValue),
                    orderDate, OrderStatus.valueOf(statusPedido)
            );
            orders.add(order);
        }

        resultSet.close();
        statement.close();
        connection.close();

        return orders;
    }

    public Order getOrderById(String idPassed) throws SQLException {
        Connection connection = Database.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM pedido WHERE id = ?;");
        preparedStatement.setString(1, idPassed);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            String id = resultSet.getString("id");
            String idClient = resultSet.getString("idCliente");
            String totalValue = resultSet.getString("valorTotal");
            String orderDate = resultSet.getString("dataPedido");
            String statusOrder = resultSet.getString("statusPedido");

            Client client = new ClientDAO().getClient(idClient);

            return new Order(
                    Long.parseLong(id),
                    client,
                    Double.parseDouble(totalValue),
                    orderDate,
                    OrderStatus.valueOf(statusOrder)
                );
        }

        resultSet.close();
        preparedStatement.close();
        connection.close();

        return null;
    }

    public void updateStatusOrder(String idPassed, String orderStatus) throws SQLException {
        Connection connection = Database.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(
                "UPDATE pedido SET statusPedido = ? WHERE id = ?;");
        preparedStatement.setString(1, orderStatus);
        preparedStatement.setString(2, idPassed);

        preparedStatement.executeUpdate();

        preparedStatement.close();
        connection.close();
    }
}
