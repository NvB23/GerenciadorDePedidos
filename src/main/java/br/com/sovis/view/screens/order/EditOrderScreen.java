package br.com.sovis.view.screens.order;

import br.com.sovis.controller.ClientController;
import br.com.sovis.controller.ItemOrderController;
import br.com.sovis.controller.OrderController;
import br.com.sovis.controller.ProductController;
import br.com.sovis.exception.ButtonException;
import br.com.sovis.model.Client;
import br.com.sovis.model.ItemOrder;
import br.com.sovis.model.Order;
import br.com.sovis.model.Product;
import br.com.sovis.view.partials.order.ItemOrderTile;
import br.com.sovis.view.style.MessageBoxVariables;
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

public class EditOrderScreen extends Container {
    private final Container toContainer;
    private final Order order;
    private ComboBox clientsComboBox;

    private final ClientController clientController = new ClientController();
    private final ProductController productController = new ProductController();
    private final OrderController orderController = new OrderController();
    private final ItemOrderController itemOrderController = new ItemOrderController();

    private final ArrayList<Client> clients = clientController.getClients();
    private ArrayList<Product> products;
    private ListContainer listContainer;
    private final ArrayList<ItemOrderTile> items = new ArrayList<>();
    private ItemOrderTile itemOrderTile;

    private final int APP_ID_SAVE_BUTTON = 0;
    private final int APP_ID_BACK_BUTTON = 999;

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
            backButton.appId = APP_ID_BACK_BUTTON;
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
        saveButton.appId = APP_ID_SAVE_BUTTON;
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
        ListContainer.Layout layout = listContainer.getLayout(0,1);
        layout.setup();
        listContainer.setRect(LEFT, PARENTSIZE + 70, FILL, PARENTSIZE + 60);
        add(listContainer);

        ArrayList<ItemOrder> itemOrders;

        try {
            products = productController.listarProdutos();
            itemOrders = itemOrderController.getItemOrdersById(order.getId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        for (ItemOrder itemOrder : itemOrders) {
            Product productEdit = itemOrder.getProduct();
            ItemOrderTile itemOrderTileEdit;
            try {
                itemOrderTileEdit = new ItemOrderTile(
                        products,
                        String.valueOf(itemOrder.getQuantity()),
                        productEdit.getId() + " " + productEdit.getName(),
                        layout
                );
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            items.add(itemOrderTileEdit);
            listContainer.addContainer(itemOrderTileEdit);
        }

        try {
            Button addButton = new Button(new Image("add.png").getScaledInstance(30,30));
            addButton.setBackColor(Variables.PRIMARY_COLOR);
            addButton.appId = 1212;

            Button deleteButton = new Button(new Image("trash.png").getScaledInstance(30,30));
            deleteButton.setBackColor(Variables.PRIMARY_COLOR);
            deleteButton.appId = 1313;

            addButton.addPressListener(new PressListener() {
                @Override
                public void controlPressed(ControlEvent controlEvent) {

                    itemOrderTile = new ItemOrderTile(products, layout);
                    items.add(itemOrderTile);
                    listContainer.addContainer(itemOrderTile);
                }
            });

            deleteButton.addPressListener(new PressListener() {
                @Override
                public void controlPressed(ControlEvent controlEvent) {
                    int selectedItem = listContainer.getSelectedIndex();

                    if (selectedItem >= 0 && selectedItem < items.size()) {
                        items.remove(selectedItem);

                        remove(listContainer);

                        listContainer = new ListContainer();
                        listContainer.getLayout(0,1);
                        layout.setup();
                        listContainer.setRect(LEFT, PARENTSIZE + 70, FILL, PARENTSIZE + 60);

                        for (ItemOrderTile item : items) {
                            listContainer.addContainer(item);
                        }

                        add(listContainer);
                        repaint();
                    }
                    else {
                        MessageBoxVariables.notSelectedItem();
                    }
                }
            });

            add(addButton, RIGHT - 16, AFTER - 35, PREFERRED - 10, PREFERRED - 10);

            add(deleteButton, BEFORE - 4, SAME, PREFERRED - 10, PREFERRED - 10);
        } catch (ImageException | IOException e) {
            throw new ButtonException(e);
        }
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

            if (control.appId == APP_ID_SAVE_BUTTON) {
                try {
                    editOrder();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                return;
            }

            if (control.appId == APP_ID_BACK_BUTTON) {
                MainWindow.getMainWindow().swap(toContainer);
            }
        }
    }

    private void editOrder() throws SQLException {
        Client client = clients.get(clientsComboBox.getSelectedIndex());
        ArrayList<ItemOrder> newItems = new ArrayList<>();

        for (ItemOrderTile itemOrderTile : items) {
            Product product = itemOrderTile.getProduct();

            ItemOrder itemOrder = new ItemOrder(
                    order,
                    product,
                    itemOrderTile.getQuantity(),
                    product.getPrice() * itemOrderTile.getQuantity()
            );

            newItems.add(itemOrder);
        }


        if (products == null || products.isEmpty()) throw new IllegalStateException("Pedido não poder ser editado sem itens");

        order.setClient(client);
        orderController.updateOrder(order.getId(), order, newItems);
        MainWindow.getMainWindow().swap(new HomeScreen());
    }
}