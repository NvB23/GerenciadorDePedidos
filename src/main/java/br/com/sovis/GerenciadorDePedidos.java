package br.com.sovis;
import totalcross.ui.MainWindow;
import totalcross.sys.Settings;
public class GerenciadorDePedidos extends MainWindow {
    
    public GerenciadorDePedidos() {
        setUIStyle(Settings.MATERIAL_UI);
    }

    @Override
    public void initUI() {
        setTitle("Gerenciador de Pedidos");
    }
}
