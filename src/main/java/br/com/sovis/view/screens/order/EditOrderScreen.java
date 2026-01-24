package br.com.sovis.view.screens.order;

import br.com.sovis.controller.ClientController;
import br.com.sovis.controller.OrderController;
import br.com.sovis.controller.ProductController;
import br.com.sovis.exception.ButtonException;
import br.com.sovis.model.Client;
import br.com.sovis.model.Order;
import br.com.sovis.model.Product;
import br.com.sovis.view.partials.order.ItemOrderTile;
import br.com.sovis.view.style.Variables;
import totalcross.io.IOException;
import totalcross.ui.*;
import totalcross.ui.event.ControlEvent;
import totalcross.ui.event.Event;
import totalcross.ui.event.PressListener;
import totalcross.ui.gfx.Color;
import totalcross.ui.image.Image;
import totalcross.ui.image.ImageException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class EditOrderScreen extends Container {
    private final Container toContainer;
    private final Order order;
    ComboBox clientsComboBox;

    ClientController clientController = new ClientController();
    ProductController productController = new ProductController();
    OrderController orderController = new OrderController();

    private final ArrayList<Client> clients = clientController.getClients();
    private ArrayList<Product> products;
    ListContainer listContainer;
    ArrayList<ItemOrderTile> itens = new ArrayList<>();
    ItemOrderTile itemOrderTile;

    public EditOrderScreen(Container toContainer, Long idOrderToEdit) throws SQLException {
        this.toContainer = toContainer;
        this.order = orderController.getOrdersById(idOrderToEdit);
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

        Label titleLabel = new Label("Editar Pedido");
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

        Label nameLabel = new Label("Selecione o Cliente");
        nameLabel.setForeColor(Variables.SECOND_COLOR);
        add(nameLabel, PARENTSIZE + 50, TOP+ 60, PARENTSIZE + 90, PREFERRED);

        String[] clientsName = new String[clients.size()];

        for (int i = 0; i < clients.size(); i++) {
            clientsName[i] = clients.get(i).getId()+ " " + clients.get(i).getName();
        }
        clientsComboBox = new ComboBox(clientsName);
        clientsComboBox.setForeColor(Variables.PRIMARY_COLOR);

        Client client = order.getClient();
        clientsComboBox.setSelectedIndex(findIndexByClientId(client.getId()));

        add(clientsComboBox, CENTER, AFTER + 5, PARENTSIZE + 90, PREFERRED);

        Label emailLabel = new Label("Items do Pedido");
        emailLabel.setForeColor(Variables.SECOND_COLOR);
        add(emailLabel, PARENTSIZE + 50, AFTER + 40 , PARENTSIZE + 90, PREFERRED);
        listContainer = new ListContainer();

        try {
            Button addButton = new Button(new Image("add.png").getScaledInstance(30,30));
            addButton.setBackColor(Variables.PRIMARY_COLOR);
            addButton.appId = 1212;

            addButton.addPressListener(new PressListener() {
                @Override
                public void controlPressed(ControlEvent controlEvent) {
                    try {
                        products = productController.listarProdutos();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    itemOrderTile = new ItemOrderTile(products);
                    itens.add(itemOrderTile);
                    listContainer.addContainer(itemOrderTile);
                }
            });

            add(addButton, RIGHT - 16, AFTER - 35, PREFERRED - 10, PREFERRED - 10);
        } catch (ImageException | IOException e) {
            throw new ButtonException(e);
        }
        add(listContainer, LEFT, AFTER + 20, FILL, FILL);
    }

    private int findIndexByClientId(Long id) {
        for (int i = 0; i < clients.size(); i++) {
            if (clients.get(i).getId().equals(id)) return i;
        }
        return 0;
    }

    @Override
    public void onEvent(Event event) {
        if (event.type == ControlEvent.PRESSED && event.target instanceof Button) {

            Button control = (Button) event.target;

            if (control.appId == 0) {
                try {
                    editOrder();
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

    private void editOrder() throws SQLException {
        Client cliente = clients.get(clientsComboBox.getSelectedIndex());
        HashMap<Product, Integer> mapProductQuantity = new HashMap<>();

        for (ItemOrderTile item : itens) {
            if (item.getQuantidade() > 0) {
                mapProductQuantity.put(item.getProduto(), item.getQuantidade());
            }
        }

        if (products == null || products.isEmpty()) throw new IllegalStateException("Pedido não poder ser editado sem itens");

        Order orderEdit = new Order(cliente);

        orderController.updateOrder(orderEdit.getId(), orderEdit, mapProductQuantity);
        MainWindow.getMainWindow().swap(new HomeScreen());
    }
}