package br.com.sovis.view.screens.order;

import br.com.sovis.controller.OrderController;
import br.com.sovis.exception.ButtonException;
import br.com.sovis.model.Order;
import br.com.sovis.model.enums.OrderStatus;
import br.com.sovis.view.partials.common.MainButton;
import br.com.sovis.view.partials.order.OrderTile;
import br.com.sovis.view.screens.product.ProductScreen;
import br.com.sovis.view.style.MessageBoxVariables;
import br.com.sovis.view.style.Variables;
import totalcross.io.IOException;
import totalcross.ui.*;
import totalcross.ui.event.ControlEvent;
import totalcross.ui.event.Event;
import br.com.sovis.view.screens.client.ClientScreen;
import totalcross.ui.font.Font;
import totalcross.ui.gfx.Color;
import totalcross.ui.image.Image;
import totalcross.ui.image.ImageException;

import java.sql.SQLException;
import java.util.ArrayList;


public class HomeScreen extends Container {
    private final OrderController orderController = new OrderController();
    private ListContainer listContainer;
    private final ArrayList<Order> orderList = orderController.getOrders();

    private final int APP_ID_CLIENT_BUTTON = 0;
    private final int APP_ID_PRODUCT_BUTTON = 1;
    private final int APP_ID_DELETE_BUTTON = 2;
    private final int APP_ID_EDIT_BUTTON = 3;
    private final int APP_ID_LOCK_BUTTON = 4;

    public HomeScreen() throws SQLException {
        setRect(0, 0, FILL, FILL);
    }

    @Override
    public void initUI() {
        Container tabBar = new Container();
        tabBar.setBackColor(Variables.PRIMARY_COLOR);
        tabBar.setRect(0,0, FILL, PARENTSIZE + 8);

        Label titleLabel = new Label("Pedidos");
        titleLabel.setForeColor(Color.WHITE);
        tabBar.add(titleLabel,  LEFT + 18, CENTER);

        try {
            Button clientButton = new Button(new Image("tab-client.png").getScaledInstance(30, 30));
            clientButton.setBackColor(Variables.PRIMARY_COLOR);
            clientButton.appId = APP_ID_CLIENT_BUTTON;
            tabBar.add(clientButton, RIGHT, CENTER);

            Button productButton = new Button(new Image("tab-product.png").getScaledInstance(30, 30));
            productButton.setBackColor(Variables.PRIMARY_COLOR);
            productButton.appId = APP_ID_PRODUCT_BUTTON;
            tabBar.add(productButton, BEFORE, CENTER);
        } catch (ImageException | IOException e) {
            throw new ButtonException(e);
        }

        add(tabBar);

        try {
            add(new MainButton("order-car.png", "Adicionar Pedido", new AddOrderScreen(this)), CENTER, AFTER + 70, PARENTSIZE + 90, PARENTSIZE + 10);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        Label orderTitle = new Label("Lista de Pedidos");
        orderTitle.setFont(Font.getFont(true, 15));
        add(orderTitle, LEFT + 10, AFTER + 30);

        Button deleteButton ,editButton, closeButton;

        try {
            deleteButton = new Button(new Image("trash.png").getScaledInstance(20,20));
            deleteButton.setBackColor(Variables.PRIMARY_COLOR);
            deleteButton.appId = APP_ID_DELETE_BUTTON;
            editButton = new Button(new Image("edit.png").getScaledInstance(20,20));
            editButton.setBackColor(Variables.PRIMARY_COLOR);
            editButton.appId = APP_ID_EDIT_BUTTON;
            closeButton = new Button(new Image("padlock.png").getScaledInstance(20,20));
            closeButton.setBackColor(Variables.PRIMARY_COLOR);
            closeButton.appId = APP_ID_LOCK_BUTTON;
        } catch (ImageException | IOException e) {
            throw new ButtonException(e);
        }
        add(deleteButton, RIGHT - 10, SAME - 5, PREFERRED - 5, PREFERRED - 5);
        add(editButton, BEFORE - 10, SAME, PREFERRED - 5, PREFERRED - 5);
        add(closeButton, BEFORE - 10, SAME, PREFERRED - 5, PREFERRED - 5);

        if (orderList.isEmpty()) {
            Label noClientsLabel = new Label("Sem pedidos cadastrados");
            noClientsLabel.setForeColor(Variables.PRIMARY_COLOR);
            add(noClientsLabel, CENTER, PARENTSIZE + 60);
        }
        else {
            listContainer = new ListContainer();
            add(listContainer, LEFT, AFTER + 30, FILL, FILL);

            for (Order order : orderList) {
                OrderTile orderTile = new OrderTile(
                        order.getId(),
                        order.getTotalValue(),
                        order.getOrderDate(),
                        order.getClient().getName(),
                        order.getStatusPedido()
                );

                listContainer.addContainer(orderTile);
            }
        }
    }

    @Override
    public void onEvent(Event event) {
        if (event.type == ControlEvent.PRESSED && event.target instanceof Button) {
            if (((Control) event.target).appId == APP_ID_CLIENT_BUTTON)
                MainWindow.getMainWindow().swap(new ClientScreen());

            if (((Control) event.target).appId == APP_ID_PRODUCT_BUTTON)
                MainWindow.getMainWindow().swap(new ProductScreen());

            if (((Control) event.target).appId == APP_ID_DELETE_BUTTON) {
                int indexSelectedItem = listContainer.getSelectedIndex();
                try {
                    if (indexSelectedItem >= 0 && indexSelectedItem < orderList.size()) {
                        Order order = orderList.get(indexSelectedItem);
                        orderController.deleteOrder(order.getId());
                        listContainer.remove(listContainer.getContainer(indexSelectedItem));
                        MainWindow.getMainWindow().swap(new HomeScreen());
                    } else {
                        MessageBoxVariables.notSelectedItem();
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }

            if (((Control) event.target).appId == APP_ID_EDIT_BUTTON) {
                int indexSelectedItem = listContainer.getSelectedIndex();
                try {
                    if (indexSelectedItem >= 0 && indexSelectedItem < orderList.size()) {
                        Order order = orderController.getOrders().get(indexSelectedItem);
                        if (order.getStatusPedido().equals(OrderStatus.FECHADO))
                            MessageBoxVariables.orderLocked();
                        else MainWindow.getMainWindow().swap(new EditOrderScreen(this, order.getId()));
                    } else {
                        MessageBoxVariables.notSelectedItem();
                    }

                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }

            if (((Control) event.target).appId == APP_ID_LOCK_BUTTON) {
                int indexSelectedItem = listContainer.getSelectedIndex();
                try {
                    if (indexSelectedItem >= 0 && indexSelectedItem < orderList.size()) {
                        Order order = orderController.getOrders().get(indexSelectedItem);
                        orderController.updateOrderStatus(order.getId(), OrderStatus.FECHADO);
                        MainWindow.getMainWindow().swap(new HomeScreen());
                    } else {
                        MessageBoxVariables.notSelectedItem();
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
