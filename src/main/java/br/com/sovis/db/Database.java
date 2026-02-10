package br.com.sovis.db;

import br.com.sovis.model.User;
import br.com.sovis.model.enums.UserType;
import totalcross.db.sqlite.SQLiteUtil;
import totalcross.sql.PreparedStatement;
import totalcross.sys.Settings;
import totalcross.sql.Connection;
import totalcross.sql.Statement;
import java.sql.SQLException;
import java.util.ArrayList;

public class Database {
    public static SQLiteUtil sqLiteUtil;
    static {
        try {
            sqLiteUtil = new SQLiteUtil(Settings.appPath, "gerenciador_de_pedidos.db");
            Statement statement = sqLiteUtil.con().createStatement();
            statement.execute("PRAGMA foreign_keys = ON;");

            statement.execute("CREATE TABLE IF NOT EXISTS usuario (" +
                    "id INTEGER NOT NULL," +
                    "nome TEXT NOT NULL," +
                    "email TEXT NOT NULL UNIQUE," +
                    "senha TEXT NOT NULL," +
                    "tipo TEXT NOT NULL," +

                    "PRIMARY KEY(id AUTOINCREMENT)" +
                    ");"
            );

            ArrayList<User> users = new ArrayList<>();
            users.add(new User("Administrador", "admin@sovis.com.br", "123", UserType.ADMIN));
            users.add(new User("Naum", "naum@sovis.com.br", "123", UserType.COMUM));
            users.add(new User("Kaue", "kaue@sovis.com.br", "123", UserType.COMUM));
            users.add(new User("Francisco", "francisco@sovis.com.br", "123", UserType.COMUM));
            users.add(new User("João Carlos de Almeida", "joaoCA@sovis.com.br", "123", UserType.COMUM));
            users.add(new User("Felipe Siqueira", "felipe@sovis.com.br", "123", UserType.COMUM));

            PreparedStatement preparedStatement = sqLiteUtil.con().prepareStatement(
                    "INSERT OR IGNORE INTO usuario(nome, email, senha, tipo) VALUES(?, ?, ?, ?);");

            for (User user : users) {
                preparedStatement.setString(1, user.getName());
                preparedStatement.setString(2, user.getEmail());
                preparedStatement.setString(3, user.getPassword());
                preparedStatement.setString(4, String.valueOf(user.getUserType()));
                preparedStatement.executeUpdate();
            }

            statement.execute("CREATE TABLE IF NOT EXISTS cliente (" +
                    "id INTEGER NOT NULL," +
                    "nome TEXT NOT NULL," +
                    "email TEXT NOT NULL," +
                    "telefone TEXT NOT NULL," +
                    "dataCadastro TEXT NOT NULL," +

                    "PRIMARY KEY(id AUTOINCREMENT)" +
                    ");"
            );

            statement.execute("CREATE TABLE IF NOT EXISTS produto (" +
                    "id INTEGER NOT NULL," +
                    "nome TEXT NOT NULL," +
                    "descricao TEXT NOT NULL," +
                    "preco REAL NOT NULL," +

                    "PRIMARY KEY(id AUTOINCREMENT)" +
                    ");"
            );

            statement.execute("CREATE TABLE IF NOT EXISTS pedido (" +
                    "id INTEGER NOT NULL," +
                    "idCliente INTEGER NOT NULL," +
                    "idUsuario INTEGER NOT NULL," +
                    "valorTotal REAL NOT NULL," +
                    "dataPedido TEXT NOT NULL," +
                    "statusPedido TEXT NOT NULL," +

                    "PRIMARY KEY(id AUTOINCREMENT)," +
                    "FOREIGN KEY(idCliente) REFERENCES cliente(id)" +
                    ");"
            );
            statement.execute("CREATE TABLE IF NOT EXISTS item_pedido (" +
                    "id INTEGER NOT NULL," +
                    "idPedido INTEGER NOT NULL," +
                    "idProduto INTEGER NOT NULL," +
                    "quantidade INTEGER NOT NULL," +
                    "valorItem REAL NOT NULL," +

                    "PRIMARY KEY(id AUTOINCREMENT)," +
                    "FOREIGN KEY(idPedido) REFERENCES pedido(id)," +
                    "FOREIGN KEY(idProduto) REFERENCES produto(id)" +
                    ");"
            );
            statement.execute("CREATE TABLE IF NOT EXISTS usuario_produto (" +
                    "id INTEGER NOT NULL," +
                    "idUsuario INTEGER NOT NULL," +
                    "idProduto INTEGER NOT NULL," +

                    "PRIMARY KEY(id AUTOINCREMENT)," +
                    "FOREIGN KEY(idUsuario) REFERENCES usuario(id)," +
                    "FOREIGN KEY(idProduto) REFERENCES produto(id)" +
                    ");"
            );
            statement.execute("CREATE TABLE IF NOT EXISTS usuario_cliente (" +
                    "id INTEGER NOT NULL," +
                    "idUsuario INTEGER NOT NULL," +
                    "idCliente INTEGER NOT NULL," +

                    "PRIMARY KEY(id AUTOINCREMENT)," +
                    "FOREIGN KEY(idUsuario) REFERENCES usuario(id)," +
                    "FOREIGN KEY(idCliente) REFERENCES cliente(id)" +
                    ");"
            );

            preparedStatement.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return sqLiteUtil.con();
    }
}
