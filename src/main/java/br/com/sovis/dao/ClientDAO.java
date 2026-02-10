package br.com.sovis.dao;

import br.com.sovis.db.Database;
import br.com.sovis.model.Client;
import totalcross.sql.Connection;
import totalcross.sql.PreparedStatement;
import totalcross.sql.Statement;
import totalcross.sql.ResultSet;

import java.sql.SQLException;
import java.util.ArrayList;

public class ClientDAO {
    private final UserClientDAO userClientDAO = new UserClientDAO();

    public void createClient(
            String name,
            String email,
            String phone,
            String dateRegister,
            ArrayList<Long> usersForAssociate) throws SQLException {
        Connection connection = Database.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO cliente(nome, email, telefone, dataCadastro) VALUES(?, ?, ?, ?);");

        preparedStatement.setString(1, name);
        preparedStatement.setString(2, email);
        preparedStatement.setString(3, phone);
        preparedStatement.setString(4, dateRegister);

        preparedStatement.executeUpdate();

        PreparedStatement preparedStatementId = connection.prepareStatement("SELECT MAX(id) FROM cliente;");
        ResultSet resultSet = preparedStatementId.executeQuery();

        Long idClient = null;
        if (resultSet.next()) {
            idClient = resultSet.getLong(1);
        }

        for (Long idUser : usersForAssociate) {
            userClientDAO.createUserClient(String.valueOf(idUser), String.valueOf(idClient));
        }

        preparedStatementId.close();
        preparedStatement.close();
        connection.close();
    }

    public void updateClient(
            String id,
            String name,
            String email,
            String phone,
            String dateRegister,
            ArrayList<Long> userForAssociatedEdit
    ) throws SQLException {
        Connection connection = Database.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(
                "UPDATE cliente SET nome = ?, email = ?, telefone = ?, dataCadastro = ? WHERE id = ?;");

        preparedStatement.setString(1, name);
        preparedStatement.setString(2, email);
        preparedStatement.setString(3, phone);
        preparedStatement.setString(4, dateRegister);
        preparedStatement.setString(5, id);

        

        preparedStatement.executeUpdate();
        preparedStatement.close();
        connection.close();
    }

    public void deleteClient(String id) throws SQLException {
        Connection connection = Database.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(
                "DELETE FROM cliente WHERE id = ?;");

        preparedStatement.setString(1, id);

        preparedStatement.executeUpdate();
        preparedStatement.close();
        connection.close();
    }

    public ArrayList<Client> getClients() throws SQLException {
        ArrayList<Client> clients = new ArrayList<>();
        Connection connection = Database.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(
                "SELECT * FROM cliente;");

        while (resultSet.next()) {
            String id = resultSet.getString("id");
            String name = resultSet.getString("nome");
            String email = resultSet.getString("email");
            String phone = resultSet.getString("telefone");
            Client client = new Client(Long.parseLong(id), name, email, phone);
            clients.add(client);
        }

        resultSet.close();
        statement.close();
        connection.close();

        return clients;
    }

    public ArrayList<Client> getClientsOfUser(String idUser) throws SQLException {
        ArrayList<Client> clients = new ArrayList<>();
        Connection connection = Database.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT * FROM usuario_cliente WHERE idUsuario = ?;");
        preparedStatement.setString(1, idUser);
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            String id = resultSet.getString("idCliente");

            Client client = getClient(id);

            clients.add(client);
        }

        resultSet.close();
        preparedStatement.close();
        connection.close();

        return clients;
    }

    public Client getClient(String idPassed) throws SQLException {
        Connection connection = Database.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM cliente WHERE id = ?;");
        preparedStatement.setString(1, idPassed);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            String id = resultSet.getString("id");
            String name = resultSet.getString("nome");
            String email = resultSet.getString("email");
            String phone = resultSet.getString("telefone");
            return new Client(Long.parseLong(id), name, email, phone);
        }

        resultSet.close();
        preparedStatement.close();
        connection.close();

        return null;
    }
}
