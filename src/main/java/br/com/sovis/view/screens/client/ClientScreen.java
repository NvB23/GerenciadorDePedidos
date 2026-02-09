package br.com.sovis.view.screens.client;

import br.com.sovis.controller.ClientController;
import br.com.sovis.controller.OrderController;
import br.com.sovis.exception.ButtonException;
import br.com.sovis.model.Client;
import br.com.sovis.model.Order;
import br.com.sovis.model.UserLogged;
import br.com.sovis.model.enums.UserType;
import br.com.sovis.view.partials.client.ClientTile;
import br.com.sovis.view.partials.common.MainButton;
import br.com.sovis.view.screens.order.HomeScreen;
import br.com.sovis.view.style.MessageBoxVariables;
import br.com.sovis.view.style.Variables;
import totalcross.io.IOException;
import totalcross.ui.*;
import totalcross.ui.event.ControlEvent;
import totalcross.ui.event.Event;
import totalcross.ui.font.Font;
import totalcross.ui.gfx.Color;
import totalcross.ui.image.Image;
import totalcross.ui.image.ImageException;

import java.sql.SQLException;
import java.util.ArrayList;

public class ClientScreen extends Container {
    private final ClientController clientController = new ClientController();
    private final OrderController orderController = new OrderController();
    private ListContainer listContainer;
    private ArrayList<Client> clientList;
    private final boolean isUserAdmin = UserLogged.userLogged.getUserType().equals(UserType.ADMIN);

    private final int APP_ID_BACK_BUTTON = 999;
    private final int APP_ID_DELETE_BUTTON = 4;
    private final int APP_ID_EDIT_BUTTON = 5;

    @Override
    public void initUI() {
        Container tabBar = new Container();
        tabBar.setBackColor(Variables.PRIMARY_COLOR);
        tabBar.setRect(0,0, FILL, PARENTSIZE + 8);

        try {
            Button backButton = new Button(new Image("back-arrow.png").getScaledInstance(20, 20));
            backButton.setBackColor(Variables.PRIMARY_COLOR);
            backButton.appId = APP_ID_BACK_BUTTON;
            tabBar.add(backButton, LEFT, TOP);
        } catch (ImageException | IOException e) {
            throw new ButtonException(e);
        }

        Label titleLabel = new Label("Clientes");
        titleLabel.setForeColor(Color.WHITE);
        tabBar.add(titleLabel,  AFTER + 8, CENTER);

        add(tabBar);

        try {
            add(new MainButton("client.png", "Adicionar Cliente", new AddClientScreen(this)), CENTER, AFTER + 70, PARENTSIZE + 90, PARENTSIZE + 10);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        Label clientTitle = new Label("Lista de Clientes");
        clientTitle.setFont(Font.getFont(true, 15));
        add(clientTitle, LEFT + 10, AFTER + 30);

        Button deleteButton;
        Button editButton;
        try {
            deleteButton = new Button(new Image("trash.png").getScaledInstance(20,20));
            deleteButton.setBackColor(Variables.PRIMARY_COLOR);
            deleteButton.appId = APP_ID_DELETE_BUTTON;
            editButton = new Button(new Image("edit.png").getScaledInstance(20,20));
            editButton.setBackColor(Variables.PRIMARY_COLOR);
            editButton.appId = APP_ID_EDIT_BUTTON;
        } catch (ImageException | IOException e) {
            throw new ButtonException(e);
        }
        add(deleteButton, RIGHT - 10, SAME - 5, PREFERRED - 5, PREFERRED - 5);
        add(editButton, BEFORE - 10, SAME, PREFERRED - 5, PREFERRED - 5);

        try {
            if (isUserAdmin) {
                clientList = clientController.getClients();
            } else {
                clientList = clientController.getClientsOfUser(UserLogged.userLogged.getId());
            }

            if (clientList.isEmpty()) {
                Label semClientesLabel = new Label("Sem clientes cadastrados");
                semClientesLabel.setForeColor(Variables.PRIMARY_COLOR);
                add(semClientesLabel, CENTER, PARENTSIZE + 60);
            }
            else {
                listContainer = new ListContainer();
                add(listContainer, LEFT, AFTER + 30, FILL, FILL);

                for (Client client : clientList) {
                    ClientTile clientTile = new ClientTile(
                            client.getId(),
                            client.getName(),
                            client.getEmail(),
                            client.getDateRegister(),
                            client.getPhone());
                    listContainer.addContainer(clientTile);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void onEvent(Event event) {
        if (event.type == ControlEvent.PRESSED) {
            if (((Control) event.target).appId == APP_ID_BACK_BUTTON) {
                try {
                    MainWindow.getMainWindow().swap(new HomeScreen());
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            if (((Control) event.target).appId == APP_ID_DELETE_BUTTON) {
                int itemSelectedIndex = listContainer.getSelectedIndex();
                try {
                    if (itemSelectedIndex >= 0 && itemSelectedIndex < clientList.size()) {
                        Client client = clientList.get(itemSelectedIndex);

                       for (Order order : orderController.getOrders()) {
                           if (order.getClient().getId() == client.getId()) {
                               MessageBoxVariables.alreadyExistsAOrderWithThisClient();
                               return;
                           }
                       }
                        clientController.deleteClient(client.getId());
                        listContainer.remove(listContainer.getContainer(itemSelectedIndex));
                        MainWindow.getMainWindow().swap(new ClientScreen());
                    } else {
                        MessageBoxVariables.notSelectedItem();
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            if (((Control) event.target).appId == APP_ID_EDIT_BUTTON) {
                int itemSelectedIndex = listContainer.getSelectedIndex();
                try {
                    if (itemSelectedIndex >= 0 && itemSelectedIndex < clientList.size()) {
                        Client client = clientController.getClients().get(itemSelectedIndex);
                        MainWindow.getMainWindow().swap(new EditClientScreen(this, client.getId()));
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
