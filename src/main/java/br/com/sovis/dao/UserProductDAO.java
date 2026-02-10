package br.com.sovis.dao;

import br.com.sovis.db.Database;
import totalcross.sql.Connection;
import totalcross.sql.PreparedStatement;
import totalcross.sql.ResultSet;

import java.sql.SQLException;
import java.util.ArrayList;

public class UserProductDAO {
    public void createUserProduct(
            String idUser,
            String idProduct
    ) throws SQLException {

        Connection connection = Database.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO usuario_produto(idUsuario, idProduto) VALUES(?, ?);");

        preparedStatement.setString(1, idUser);
        preparedStatement.setString(2, idProduct);

        preparedStatement.executeUpdate();
        preparedStatement.close();
    }


    public void deleteUserProduct(String id) throws SQLException {
        Connection connection = Database.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(
                "DELETE FROM usuario_produto WHERE idProduto = ?;");

        preparedStatement.setString(1, id);

        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    public ArrayList<Long> getUserProductByIdProduct(String idPassed) throws SQLException {
        ArrayList<Long> idsProducts = new ArrayList<>();
        Connection connection = Database.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM usuario_produto WHERE idProduto = ?;");
        preparedStatement.setString(1, idPassed);
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            String idProduto = resultSet.getString("idUsuario");

            idsProducts.add(Long.parseLong(idProduto));
        }

        resultSet.close();
        preparedStatement.close();

        return idsProducts;
    }
}
