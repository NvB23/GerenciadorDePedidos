package br.com.sovis.view.screens.client;

import br.com.sovis.view.partials.client.ClientTile;
import br.com.sovis.view.partials.common.TabBar;
import br.com.sovis.view.partials.common.MainButton;
import br.com.sovis.view.screens.order.HomeScreen;
import totalcross.ui.*;
import totalcross.ui.event.ControlEvent;
import totalcross.ui.event.Event;

public class ClientScreen extends Container {
    private ClientTile clientTile;

    @Override
    public void initUI() {
        add(new TabBar(new HomeScreen(), "Clientes"), 0, 0, FILL, PARENTSIZE + 8);

        add(new MainButton("client.png", "Adicionar Cliente", new AddClientScreen(this)), CENTER, AFTER + 10, PARENTSIZE + 90, PARENTSIZE + 10);

        Label pedidosTitle = new Label("Lista de Clientes");
        add(pedidosTitle, CENTER, AFTER + 30);

        ListContainer listContainer = new ListContainer();
        add(listContainer, LEFT, AFTER + 30, FILL, FILL);
        for (int i = 0; i < 10; i++) {
            clientTile = new ClientTile(
                    435L * i,
                    "Cliente " + i,
                    "naum@email.com",
                    "23/04/2026",
                    "90 7884-7574");
            clientTile.appId = i;
            listContainer.addContainer(clientTile);
        }
    }
    @Override
    public void onEvent(Event event) {
        if (event.type == ControlEvent.PRESSED && event.target == clientTile) {
            for (int i = 0; i < 10; i++) {
                if (((Control) event.target).appId == i) {
                    MainWindow.getMainWindow().swap(new EditClientScreen(new ClientScreen(), 1L));
                }
            }
        }
    }
}
