package br.com.sovis.view.screens.order;

import br.com.sovis.controller.ClientController;
import br.com.sovis.controller.OrderController;
import br.com.sovis.exception.ButtonException;
import br.com.sovis.model.Client;
import br.com.sovis.model.Order;
import br.com.sovis.model.Product;
import br.com.sovis.view.partials.order.ContentFieldOrder;
import br.com.sovis.view.style.Variables;
import totalcross.io.IOException;
import totalcross.ui.*;
import totalcross.ui.event.ControlEvent;
import totalcross.ui.event.Event;
import totalcross.ui.gfx.Color;
import totalcross.ui.image.Image;
import totalcross.ui.image.ImageException;

import java.sql.SQLException;
import java.util.HashMap;

public class AddOrderScreen extends Container {
    private ClientController clientController = new ClientController();
    private final OrderController orderController = new OrderController();
    private final ContentFieldOrder contentFieldOrder = new ContentFieldOrder();
    private final Container toContainer;

    public AddOrderScreen(Container toContainer) throws SQLException {
        this.toContainer = toContainer;
        setRect(0, 0, FILL, FILL);
    }

    @Override
    public void initUI() {
        Container tabBar = new Container();
        tabBar.setBackColor(Variables.PRIMARY_COLOR);
        tabBar.setRect(0,0, FILL, PARENTSIZE + 8);

        try {
            Button backButton = new Button(new Image("back-arrow.png").getScaledInstance(20, 20));
            backButton.setBackColor(Variables.PRIMARY_COLOR);
            backButton.appId = 999;
            tabBar.add(backButton, LEFT, TOP);
        } catch (ImageException | IOException e) {
            throw new ButtonException(e);
        }

        Label titleLabel = new Label("Adicionar Pedido");
        titleLabel.setForeColor(Color.WHITE);
        tabBar.add(titleLabel,  AFTER + 8, CENTER);

        Button saveButton;
        try {
            saveButton = new Button(new Image("save.png").getScaledInstance(30, 30));
        } catch (ImageException | IOException e) {
            throw new ButtonException(e);
        }

        saveButton.setBackColor(Variables.PRIMARY_COLOR);
        saveButton.appId = 0;
        tabBar.add(saveButton, RIGHT, CENTER);

        add(tabBar);

        add(contentFieldOrder, 0, TOP + 80, FILL, FILL);
    }

    @Override
    public void onEvent(Event event) {
        if (event.type == ControlEvent.PRESSED && event.target instanceof Button) {

            Button control = (Button) event.target;

            if (control.appId == 0) {
                try {
                    salvarPedido(contentFieldOrder);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                return;
            }

            if (control.appId == 999) {
                MainWindow.getMainWindow().swap(toContainer);
            }
        }
    }

    private void salvarPedido(ContentFieldOrder contentFieldOrder) throws SQLException {
        Client client = contentFieldOrder.getClient();
        HashMap<Product, Integer> products = contentFieldOrder.getProducts();

        if (products == null || products.isEmpty()) throw new IllegalStateException("Pedido não poder ser salvo sem itens");

        Order order = new Order(client);

        orderController.createOrder(order, products);
        MainWindow.getMainWindow().swap(new HomeScreen());
    }
}
