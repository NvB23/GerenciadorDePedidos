package br.com.sovis.view.partials.order;


import br.com.sovis.controller.ClientController;
import br.com.sovis.controller.ProductController;
import br.com.sovis.exception.ButtonException;
import br.com.sovis.model.Client;
import br.com.sovis.model.ItemOrder;
import br.com.sovis.model.Product;
import br.com.sovis.view.style.MessageBoxVariables;
import br.com.sovis.view.style.Variables;
import totalcross.io.IOException;
import totalcross.ui.*;
import totalcross.ui.dialog.MessageBox;
import totalcross.ui.event.ControlEvent;
import totalcross.ui.event.PressListener;
import totalcross.ui.gfx.Color;
import totalcross.ui.image.Image;
import totalcross.ui.image.ImageException;

import java.sql.SQLException;
import java.util.ArrayList;

public class ContentFieldOrder extends Container {
    private final ClientController clientController = new ClientController();
    private final ProductController productController = new ProductController();

    ComboBox clientsComboBox;

    ItemOrderTile itemOrderTile;

    private final ArrayList<Client> clients = clientController.getClients();

    private ArrayList<Product> products;

    ListContainer listContainer;
    ArrayList<ItemOrderTile> items = new ArrayList<>();

    public ContentFieldOrder() throws SQLException {}

    @Override
    public void initUI() {
        super.initUI();

        Label nameLabel = new Label("Selecione o Cliente");
        nameLabel.setForeColor(Variables.SECOND_COLOR);
        add(nameLabel, PARENTSIZE + 50, TOP, PARENTSIZE + 90, PREFERRED);

        String[] clientsName = new String[clients.size()];

        for (int i = 0; i < clients.size(); i++) {
            clientsName[i] = clients.get(i).getId()+ " " + clients.get(i).getName();
        }
        clientsComboBox = new ComboBox(clientsName);
        clientsComboBox.setForeColor(Variables.PRIMARY_COLOR);

        add(clientsComboBox, CENTER, AFTER + 5, PARENTSIZE + 90, PREFERRED);

        Label emailLabel = new Label("Items do Pedido");
        emailLabel.setForeColor(Variables.SECOND_COLOR);
        add(emailLabel, PARENTSIZE + 50, AFTER + 40 , PARENTSIZE + 90, PREFERRED);

        listContainer = new ListContainer();
        ListContainer.Layout layout = listContainer.getLayout(0,1);
        layout.setup();
        listContainer.setRect(LEFT, PARENTSIZE + 60, FILL, PARENTSIZE + 60);
        add(listContainer);

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
                    try {
                        products = productController.listarProdutos();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }

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
                        listContainer.setRect(LEFT, PARENTSIZE + 60, FILL, PARENTSIZE + 60);

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

    public Client getClient() {
        int idSelectedClients = clientsComboBox.getSelectedIndex();
        return clients.get(idSelectedClients);
    }

    public ArrayList<ItemOrder> getItemOrders() {
        ArrayList<ItemOrder> itemOrders = new ArrayList<>();

        for (ItemOrderTile item : items) {
            ItemOrder itemOrder = new ItemOrder(
                    item.getProduct(),
                    item.getQuantity(),
                    item.getProduct().getPrice() * item.getQuantity()
            );

            itemOrders.add(itemOrder);
        }
        return itemOrders;
    }


}