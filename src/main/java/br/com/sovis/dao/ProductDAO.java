package br.com.sovis.dao;

import br.com.sovis.db.Database;
import br.com.sovis.model.Product;
import totalcross.sql.Connection;
import totalcross.sql.PreparedStatement;
import totalcross.sql.Statement;
import totalcross.sql.ResultSet;

import java.sql.SQLException;
import java.util.ArrayList;

public class ProductDAO {
    private final UserProductDAO userProductDAO = new UserProductDAO();

    public void createProduct(
            String name,
            String description,
            String price,
            ArrayList<Long> usersForAssociate) throws SQLException {
        Connection connection = Database.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO produto(nome, descricao, preco) VALUES(?, ?, ?);");

        preparedStatement.setString(1, name);
        preparedStatement.setString(2, description);
        preparedStatement.setString(3, price);

        preparedStatement.executeUpdate();

        PreparedStatement preparedStatementId = connection.prepareStatement("SELECT MAX(id) FROM produto;");
        ResultSet resultSet = preparedStatementId.executeQuery();

        Long idProduct = null;
        if (resultSet.next()) {
            idProduct = resultSet.getLong(1);
        }

        for (Long idUser : usersForAssociate) {
            userProductDAO.createUserProduct(String.valueOf(idUser), String.valueOf(idProduct));
        }

        preparedStatementId.close();
        preparedStatement.close();
        connection.close();
    }

    public void updateProduct(
            String id,
            String name,
            String description,
            String price,
            ArrayList<Long> userForAssociatedEdit
    ) throws SQLException {
        Connection connection = Database.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(
                "UPDATE produto SET nome = ?, descricao = ?, preco = ? WHERE id = ?;");

        preparedStatement.setString(1, name);
        preparedStatement.setString(2, description);
        preparedStatement.setString(3, price);
        preparedStatement.setString(4, id);

        preparedStatement.executeUpdate();

        userProductDAO.deleteUserProduct(id);

        for (Long idForAssociate : userForAssociatedEdit) {
            userProductDAO.createUserProduct(String.valueOf(idForAssociate), String.valueOf(id));
        }

        preparedStatement.close();
        connection.close();
    }

    public void deleteProduct(String id) throws SQLException {
        Connection connection = Database.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(
                "DELETE FROM produto WHERE id = ?;");

        preparedStatement.setString(1, id);

        preparedStatement.executeUpdate();

        userProductDAO.deleteUserProduct(id);

        preparedStatement.close();
        connection.close();
    }

    public ArrayList<Product> getProducts() throws SQLException {
        ArrayList<Product> products = new ArrayList<>();
        Connection connection = Database.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(
                "SELECT * FROM produto;");

        while (resultSet.next()) {
            String id = resultSet.getString("id");
            String name = resultSet.getString("nome");
            String description = resultSet.getString("descricao");
            String price = resultSet.getString("preco");
            Product product = new Product(Long.parseLong(id), name, description, Double.parseDouble(price));
            products.add(product);
        }

        resultSet.close();
        statement.close();
        connection.close();

        return products;
    }

    public ArrayList<Product> getProductsOfUser(String idUser) throws SQLException {
        ArrayList<Product> products = new ArrayList<>();
        Connection connection = Database.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT * FROM usuario_produto WHERE idUsuario = ?;");
        preparedStatement.setString(1, idUser);
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            String idProduct = resultSet.getString("idProduto");

            Product product = getProductById(idProduct);

            products.add(product);
        }

        resultSet.close();
        preparedStatement.close();
        connection.close();

        return products;
    }

    public Product getProductById(String idPassed) throws SQLException {
        Connection connection = Database.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM produto WHERE id = ?;");
        preparedStatement.setString(1, idPassed);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            String id = resultSet.getString("id");
            String name = resultSet.getString("nome");
            String description = resultSet.getString("descricao");
            String price = resultSet.getString("preco");
            return new Product(Long.parseLong(id), name, description, Double.parseDouble(price));
        }

        resultSet.close();
        preparedStatement.close();
        connection.close();

        return null;
    }
}
