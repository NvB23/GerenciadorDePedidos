package br.com.sovis.view.screens.order;

import br.com.sovis.model.enums.StatusPedido;
import br.com.sovis.view.partials.common.MainButton;
import br.com.sovis.view.partials.order.OrderTile;
import br.com.sovis.view.partials.common.TabBar;
import br.com.sovis.view.screens.client.EditClientScreen;
import br.com.sovis.view.screens.product.ProductScreen;
import totalcross.ui.*;
import totalcross.ui.event.ControlEvent;
import totalcross.ui.event.Event;
import br.com.sovis.view.screens.client.ClientScreen;

import java.util.HashMap;


public class HomeScreen extends Container {
    private OrderTile orderTile;

    public HomeScreen() {
        setRect(0, 0, FILL, FILL);
    }

    @Override
    public void initUI() {
        HashMap<String, Container> botoesHeader = new HashMap<>();
        botoesHeader.put("tab-client.png", new ClientScreen());
        botoesHeader.put("tab-product.png", new ProductScreen());

        add(new TabBar("Pedidos", botoesHeader), 0, 0, FILL, PARENTSIZE + 8);

        add(new MainButton("order-car.png", "Adicionar Pedido", new AddOrderScreen(this)), CENTER, AFTER + 10, PARENTSIZE + 90, PARENTSIZE + 10);

        Label pedidosTitle = new Label("Lista de Pedidos");
        add(pedidosTitle, CENTER, AFTER + 30);

        ListContainer listContainer = new ListContainer();
        add(listContainer, LEFT, AFTER + 30, FILL, FILL);
        for (int i = 0; i < 10; i++) {
            orderTile = new OrderTile(
                    435L * i,
                    i*123.54,
                    "12/04/2025",
                    "Cliente " + i,
                    i % 2 == 0 ? StatusPedido.FECHADO : StatusPedido.PENDENTE);
            orderTile.appId = i;
            listContainer.addContainer(orderTile);
        }
    }

    @Override
    public void onEvent(Event event) {
        if (event.type == ControlEvent.PRESSED && event.target == orderTile) {
            for (int i = 0; i < 10; i++) {
                if (((Control) event.target).appId == i) {
                    MainWindow.getMainWindow().swap(new EditOrderScreen(new HomeScreen(), 1L));
                }
            }
        }
    }
}
