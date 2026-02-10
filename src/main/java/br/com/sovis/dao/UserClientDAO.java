package br.com.sovis.dao;

import br.com.sovis.db.Database;
import br.com.sovis.model.ItemOrder;
import br.com.sovis.model.Order;
import br.com.sovis.model.Product;
import totalcross.sql.Connection;
import totalcross.sql.PreparedStatement;
import totalcross.sql.ResultSet;
import totalcross.sql.Statement;

import java.sql.SQLException;
import java.util.ArrayList;

public class UserClientDAO {
    public void createUserClient(
            String idUser,
            String idClient
    ) throws SQLException {

        Connection connection = Database.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO usuario_cliente(idUsuario, idCliente) VALUES(?, ?);");

        preparedStatement.setString(1, idUser);
        preparedStatement.setString(2, idClient);

        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    public boolean updateUserClient(
            String id,
            String idOrder,
            String idProduct
    ) throws SQLException {

        Connection connection = Database.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(
                "UPDATE usuario_cliente SET idCliente = ? WHERE idUsuario = ?;");

        preparedStatement.setString(1, idOrder);
        preparedStatement.setString(2, idProduct);

        int i = preparedStatement.executeUpdate();


        preparedStatement.close();
        connection.close();

        if (i > 0) return true;
        else if (i < 0) return false;
        return false;
    }

    public boolean deleteUserClient(String id) throws SQLException {
        Connection connection = Database.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(
                "DELETE FROM item_pedido WHERE id = ?;");

        preparedStatement.setString(1, id);

        int i = preparedStatement.executeUpdate();
        preparedStatement.close();
        connection.close();

        if (i > 0) return true;
        else if (i < 0) return false;
        return false;
    }

    public ArrayList<ItemOrder> getUserClient() throws SQLException {
        ArrayList<ItemOrder> itemOrders = new ArrayList<>();
        Connection connection = Database.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(
                "SELECT * FROM item_pedido;");

        while (resultSet.next()) {
            String id = resultSet.getString("id");
            String idOrder = resultSet.getString("idPedido");
            String idProduct = resultSet.getString("idProduto");
            String quantity = resultSet.getString("quantidade");
            String itemValue = resultSet.getString("valorItem");

            Order order = new OrderDAO().getOrderById(idOrder);
            Product product = new ProductDAO().getProductById(idProduct);


            ItemOrder itemOrder = new ItemOrder(
                    Long.parseLong(id),
                    order,
                    product,
                    Integer.parseInt(quantity),
                    Double.parseDouble(itemValue)

            );
            itemOrders.add(itemOrder);
        }

        resultSet.close();
        statement.close();
        connection.close();

        return itemOrders;
    }

    public ItemOrder getUserClientById(String idPassed) throws SQLException {
        Connection connection = Database.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM item_pedido WHERE id = ?;");
        preparedStatement.setString(1, idPassed);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            String id = resultSet.getString("id");
            String idOrder = resultSet.getString("idPedido");
            String idProduct = resultSet.getString("idProduto");
            String quantity = resultSet.getString("quantidade");
            String itemValue = resultSet.getString("valorItem");

            Order order = new OrderDAO().getOrderById(idOrder);
            Product product = new ProductDAO().getProductById(idProduct);


            return new ItemOrder(
                    Long.parseLong(id),
                    order,
                    product,
                    Integer.parseInt(quantity),
                    Double.parseDouble(itemValue)

            );
        }

        resultSet.close();
        preparedStatement.close();
        connection.close();

        return null;
    }
}
