package br.com.sovis.db;

import totalcross.db.sqlite.SQLiteUtil;
import totalcross.sql.PreparedStatement;
import totalcross.sys.Settings;
import totalcross.sql.Connection;
import totalcross.sql.Statement;
import java.sql.SQLException;

public class Database {
    public static SQLiteUtil sqLiteUtil;
    static {
        try {
            sqLiteUtil = new SQLiteUtil(Settings.appPath, "gerenciador_de_pedidos.db");
            Statement statement = sqLiteUtil.con().createStatement();
            statement.execute("PRAGMA foreign_keys = ON;");

            statement.execute("CREATE TABLE IF NOT EXISTS usuario (" +
                    "id INTEGER NOT NULL," +
                    "email TEXT NOT NULL UNIQUE," +
                    "senha TEXT NOT NULL," +

                    "PRIMARY KEY(id AUTOINCREMENT)" +
                    ");"
            );

            PreparedStatement preparedStatement = sqLiteUtil.con().prepareStatement(
                    "INSERT OR IGNORE INTO usuario(email, senha) VALUES(?, ?);");
            preparedStatement.setString(1, "usuario@sovis.com.br");
            preparedStatement.setString(2, "123");

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

            preparedStatement.executeUpdate();
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
