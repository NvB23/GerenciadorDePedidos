package br.com.sovis.view.screens.order;

import br.com.sovis.controller.ClientController;
import br.com.sovis.controller.OrderController;
import br.com.sovis.exception.ButtonException;
import br.com.sovis.model.Client;
import br.com.sovis.model.Order;
import br.com.sovis.model.UserLogged;
import br.com.sovis.model.enums.OrderStatus;
import br.com.sovis.model.enums.UserType;
import br.com.sovis.view.partials.order.ItemOrderTile;
import br.com.sovis.view.partials.order.OrderTile;
import br.com.sovis.view.screens.client.ClientScreen;
import br.com.sovis.view.screens.product.ProductScreen;
import br.com.sovis.view.style.MessageBoxVariables;
import br.com.sovis.view.style.Variables;
import totalcross.io.IOException;
import totalcross.ui.*;
import totalcross.ui.event.ControlEvent;
import totalcross.ui.event.Event;
import totalcross.ui.event.PressListener;
import totalcross.ui.font.Font;
import totalcross.ui.gfx.Color;
import totalcross.ui.image.Image;
import totalcross.ui.image.ImageException;

import java.sql.SQLException;
import java.util.ArrayList;

public class FilterScreen extends Container {
    private final ClientController clientController = new ClientController();
    private final ArrayList<Client> clients = clientController.getClients();
    private final OrderController orderController = new OrderController();
    private ListContainer listContainer = new ListContainer();

    private ArrayList<Order> resultsOrder;

    private Client client;

    private final Label noSearchLabel = new Label("Sem Busca Realizada");

    private boolean showNoSearchLabel = true;
    private boolean enabledButtons = false;

    private final int APP_ID_DELETE_BUTTON = 1;
    private final int APP_ID_EDIT_BUTTON = 2;
    private final int APP_ID_LOCK_BUTTON = 3;

    public FilterScreen() throws SQLException {
    }

    public FilterScreen(Client client) throws SQLException {
        this.client = client;

        showNoSearchLabel = false;
        enabledButtons = true;

        listContainer = new ListContainer();
        listContainer.setRect( LEFT, BOTTOM, FILL, PARENTSIZE + 62);
        add(listContainer);

        if (UserLogged.userLogged.getUserType().equals(UserType.ADMIN)) {
            resultsOrder = orderController.getOrdersByIdClient(client.getId());
        } else {
            resultsOrder = orderController.getOrdersOfUserByIdClient(client.getId(), UserLogged.userLogged.getId());
        }

        for (Order order : resultsOrder) {
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
            backButton.addPressListener(new PressListener() {
                @Override
                public void controlPressed(ControlEvent controlEvent) {
                    try {
                        MainWindow.getMainWindow().swap(new HomeScreen());
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
        } catch (ImageException | IOException e) {
            throw new ButtonException(e);
        }

        Label titleLabel = new Label("Filtrar Pedidos Por Cliente");
        titleLabel.setForeColor(Color.WHITE);
        tabBar.add(titleLabel,  RIGHT - 8, CENTER);

        add(tabBar);

        Label nameLabel = new Label("Selecione o Cliente");
        nameLabel.setForeColor(Variables.SECOND_COLOR);
        add(nameLabel, PARENTSIZE + 50, TOP+ 60, PARENTSIZE + 90, PREFERRED);

        String[] clientsName = new String[clients.size()];

        for (int i = 0; i < clients.size(); i++) {
            clientsName[i] = clients.get(i).getId()+ " " + clients.get(i).getName();
        }
        ComboBox clientsComboBox = new ComboBox(clientsName);
        clientsComboBox.setForeColor(Variables.PRIMARY_COLOR);

        add(clientsComboBox, LEFT + 12, AFTER + 5, PARENTSIZE + 80, PREFERRED);

        if (client != null) clientsComboBox.setValue(client.getId() + " " + client.getName());

        Label orderTitle = new Label("Lista de Pedidos");
        orderTitle.setFont(Font.getFont(true, 15));
        add(orderTitle, LEFT + 10, AFTER + 30);

        Button deleteButton ,editButton, closeButton;

        try {
            deleteButton = new Button(new Image("trash.png").getScaledInstance(20,20));
            deleteButton.setBackColor(Variables.PRIMARY_COLOR);
            deleteButton.setEnabled(enabledButtons);
            deleteButton.appId = APP_ID_DELETE_BUTTON;

            editButton = new Button(new Image("edit.png").getScaledInstance(20,20));
            editButton.setBackColor(Variables.PRIMARY_COLOR);
            editButton.appId = APP_ID_EDIT_BUTTON;
            editButton.setEnabled(enabledButtons);

            closeButton = new Button(new Image("padlock.png").getScaledInstance(20,20));
            closeButton.setBackColor(Variables.PRIMARY_COLOR);
            closeButton.appId = APP_ID_LOCK_BUTTON;
            closeButton.setEnabled(enabledButtons);
        } catch (ImageException | IOException e) {
            throw new ButtonException(e);
        }
        add(deleteButton, RIGHT - 10, SAME - 5, PREFERRED - 5, PREFERRED - 5);
        add(editButton, BEFORE - 10, SAME, PREFERRED - 5, PREFERRED - 5);
        add(closeButton, BEFORE - 10, SAME, PREFERRED - 5, PREFERRED - 5);

        if (showNoSearchLabel) {
            noSearchLabel.setForeColor(Variables.SECOND_COLOR);
            add(noSearchLabel, CENTER, CENTER, PREFERRED, PREFERRED);
        }

        Label noResultsLabel = new Label("Sem Resultados Encontrados");
        noResultsLabel.setForeColor(Variables.SECOND_COLOR);

        try {
            Button searchButton = new Button(new Image("search.png").getScaledInstance(23,23));
            searchButton.setBackColor(Variables.PRIMARY_COLOR);
            searchButton.appId = 0;
            searchButton.addPressListener(new PressListener() {
                @Override
                public void controlPressed(ControlEvent controlEvent) {
                    remove(noSearchLabel);
                    remove(noResultsLabel);
                    int clientSelected = clientsComboBox.getSelectedIndex();
                    if (clientSelected >= 0 && clientSelected < clients.size()) {
                        remove(listContainer);
                        listContainer = new ListContainer();
                        add(listContainer, LEFT, BOTTOM, FILL, PARENTSIZE + 62);

                        Client client = clients.get(clientSelected);

                        try {
                            if (UserLogged.userLogged.getUserType().equals(UserType.ADMIN)) {
                                resultsOrder = orderController.getOrdersByIdClient(client.getId());
                            } else {
                                resultsOrder = orderController.getOrdersOfUserByIdClient(client.getId(), UserLogged.userLogged.getId());
                            }

                            if (resultsOrder.isEmpty()) {
                                add(noResultsLabel, CENTER, CENTER, PREFERRED, PREFERRED);
                            }
                            else {
                                for (Order order : resultsOrder) {
                                    OrderTile orderTile = new OrderTile(
                                            order.getId(),
                                            order.getTotalValue(),
                                            order.getOrderDate(),
                                            order.getClient().getName(),
                                            order.getStatusPedido()
                                    );

                                    listContainer.addContainer(orderTile);
                                }
                                deleteButton.setEnabled(true);
                                editButton.setEnabled(true);
                                closeButton.setEnabled(true);
                            }
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                    } else {
                        MessageBoxVariables.emptyClient();
                    }
                }
            });
            add(searchButton, clientsComboBox.getX() + PARENTSIZE + 80, clientsComboBox.getY(), PREFERRED - 5, PREFERRED - 5);
        } catch (ImageException | IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void onEvent(Event event) {
        if (event.type == ControlEvent.PRESSED && event.target instanceof Button) {
            if (((Control) event.target).appId == APP_ID_DELETE_BUTTON) {
                int indexSelectedItem = listContainer.getSelectedIndex();
                try {
                    if (indexSelectedItem >= 0 && indexSelectedItem < resultsOrder.size()) {
                        Order order = resultsOrder.get(indexSelectedItem);
                        orderController.deleteOrder(order.getId());
                        listContainer.remove(listContainer.getContainer(indexSelectedItem));
                        MainWindow.getMainWindow().swap(new FilterScreen(order.getClient()));
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
                    if (indexSelectedItem >= 0 && indexSelectedItem < resultsOrder.size()) {
                        Order order = resultsOrder.get(indexSelectedItem);
                        if (order.getStatusPedido().equals(OrderStatus.FECHADO))
                            MessageBoxVariables.orderLocked();
                        else MainWindow.getMainWindow().swap(new EditOrderScreen(new FilterScreen(order.getClient()), order.getId()));
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
                    if (indexSelectedItem >= 0 && indexSelectedItem < resultsOrder.size()) {
                        Order order = resultsOrder.get(indexSelectedItem);
                        orderController.updateOrderStatus(order.getId(), OrderStatus.FECHADO);
                        MainWindow.getMainWindow().swap(new FilterScreen(order.getClient()));
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
