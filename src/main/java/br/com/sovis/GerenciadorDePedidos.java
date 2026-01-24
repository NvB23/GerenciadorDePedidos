package br.com.sovis;
import br.com.sovis.db.Database;
import br.com.sovis.view.screens.LoginScreen;
import totalcross.ui.MainWindow;
import totalcross.sys.Settings;

import java.sql.SQLException;

public class GerenciadorDePedidos extends MainWindow {
    
    public GerenciadorDePedidos() {
        setUIStyle(Settings.MATERIAL_UI);
    }

    @Override
    public void initUI() {
        setTitle("Gerenciador de Pedidos");
        try {
            Database.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        swap(new LoginScreen());
    }
}
