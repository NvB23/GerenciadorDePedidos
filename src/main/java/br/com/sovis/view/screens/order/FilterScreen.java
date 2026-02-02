package br.com.sovis.view.screens.order;

import br.com.sovis.controller.ClientController;
import br.com.sovis.controller.OrderController;
import br.com.sovis.exception.ButtonException;
import br.com.sovis.model.Client;
import br.com.sovis.model.Order;
import br.com.sovis.view.partials.order.ItemOrderTile;
import br.com.sovis.view.partials.order.OrderTile;
import br.com.sovis.view.style.MessageBoxVariables;
import br.com.sovis.view.style.Variables;
import totalcross.io.IOException;
import totalcross.ui.*;
import totalcross.ui.event.ControlEvent;
import totalcross.ui.event.PressListener;
import totalcross.ui.gfx.Color;
import totalcross.ui.image.Image;
import totalcross.ui.image.ImageException;

import java.sql.SQLException;
import java.util.ArrayList;

public class FilterScreen extends Container {
    private final ClientController clientController = new ClientController();
    private final ArrayList<Client> clients = clientController.getClients();
    private final OrderController orderController = new OrderController();
    private final ListContainer listContainer = new ListContainer();
    private boolean isVoidResults = true;

    public FilterScreen() throws SQLException {
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

        try {
            Button searchButton = new Button(new Image("search.png").getScaledInstance(23,23));
            searchButton.setBackColor(Variables.PRIMARY_COLOR);
            searchButton.appId = 0;
            searchButton.addPressListener(new PressListener() {
                @Override
                public void controlPressed(ControlEvent controlEvent) {
                    int clientSelected = clientsComboBox.getSelectedIndex();
                    if (clientSelected >= 0 && clientSelected < clients.size()) {
                        isVoidResults = false;
                        add(listContainer, LEFT, BOTTOM, FILL, PARENTSIZE + 65);

                        Client client = clients.get(clientSelected);

                        try {
                            ArrayList<Order> resultsOrder = orderController.getOrdersByIdClient(client.getId());

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
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                    } else {
                        MessageBoxVariables.emptyClient();
                    }
                }
            });
            add(searchButton, AFTER + 5, SAME, PREFERRED - 5, PREFERRED - 5);
        } catch (ImageException | IOException e) {
            throw new RuntimeException(e);
        }

        Label noResultsLabel = new Label(!isVoidResults ? "" : "Sem Pedidos");
        noResultsLabel.setForeColor(Variables.SECOND_COLOR);
        add(noResultsLabel, CENTER, CENTER, PREFERRED, PREFERRED);
    }
}
