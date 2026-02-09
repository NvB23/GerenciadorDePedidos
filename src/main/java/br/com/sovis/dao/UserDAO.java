package br.com.sovis.dao;

import br.com.sovis.db.Database;
import br.com.sovis.model.User;
import br.com.sovis.model.enums.UserType;
import totalcross.sql.Connection;
import totalcross.sql.PreparedStatement;
import totalcross.sql.Statement;
import totalcross.sql.ResultSet;

import java.sql.SQLException;
import java.util.ArrayList;

public class UserDAO {
    public boolean createUser(String email, String password) throws SQLException {
        Connection connection = Database.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO usuario(email, senha) VALUES(?, ?);");

        preparedStatement.setString(1, email);
        preparedStatement.setString(2, password);

        int i = preparedStatement.executeUpdate();
        preparedStatement.close();
        connection.close();

        if (i > 0) return true;
        else if (i < 0) return false;
        return false;
    }

    public boolean updateUser(String id, String email, String password) throws SQLException {
        Connection connection = Database.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(
                "UPDATE usuario SET email = ?, senha = ? WHERE id = ?;");

        preparedStatement.setString(1, email);
        preparedStatement.setString(2, password);
        preparedStatement.setString(3, id);

        int i = preparedStatement.executeUpdate();
        preparedStatement.close();
        connection.close();

        if (i > 0) return true;
        else if (i < 0) return false;
        return false;
    }

    public boolean deleteUser(String id) throws SQLException {
        Connection connection = Database.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(
                "DELETE FROM usuario WHERE id = ?;");

        preparedStatement.setString(1, id);

        int i = preparedStatement.executeUpdate();
        preparedStatement.close();
        connection.close();

        if (i > 0) return true;
        else if (i < 0) return false;
        return false;
    }

    public ArrayList<User> getUsers() throws SQLException {
        ArrayList<User> usuarios = new ArrayList<>();
        Connection connection = Database.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(
                "SELECT * FROM usuario;");

        while (resultSet.next()) {
            String id = resultSet.getString("id");
            String email = resultSet.getString("email");
            String password = resultSet.getString("senha");
            String userType = resultSet.getString("tipo");
            User user = new User(Long.parseLong(id), email, password, UserType.valueOf(userType));
            usuarios.add(user);
        }

        resultSet.close();
        statement.close();
        connection.close();

        return usuarios;
    }

    public ArrayList<User> getCommonUsers() throws SQLException {
        ArrayList<User> usuarios = new ArrayList<>();
        Connection connection = Database.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(
                "SELECT * FROM usuario WHERE tipo = 'COMUM';");

        while (resultSet.next()) {
            String id = resultSet.getString("id");
            String email = resultSet.getString("email");
            String password = resultSet.getString("senha");
            String userType = resultSet.getString("tipo");
            User user = new User(Long.parseLong(id), email, password, UserType.valueOf(userType));
            usuarios.add(user);
        }

        resultSet.close();
        statement.close();
        connection.close();

        return usuarios;
    }

    public User getUserById(String idPassed) throws SQLException {
        Connection connection = Database.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM usuario WHERE id = ?;");
        preparedStatement.setString(1, idPassed);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            String id = resultSet.getString("id");
            String email = resultSet.getString("email");
            String password = resultSet.getString("senha");
            String userType = resultSet.getString("tipo");
            return new User(Long.parseLong(id), email, password, UserType.valueOf(userType));
        }

        resultSet.close();
        preparedStatement.close();
        connection.close();

        return null;
    }

    public User getUserByEmailSenha(String emailPassed, String passwordPassed) throws SQLException {
        Connection connection = Database.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT * FROM usuario WHERE email = ? AND senha = ?;"
        );
        preparedStatement.setString(1, emailPassed);
        preparedStatement.setString(2, passwordPassed);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            String id = resultSet.getString("id");
            String email = resultSet.getString("email");
            String password = resultSet.getString("senha");
            String userType = resultSet.getString("tipo");
            return new User(Long.parseLong(id), email, password, UserType.valueOf(userType));
        }

        resultSet.close();
        preparedStatement.close();
        connection.close();

        return null;
    }
}
