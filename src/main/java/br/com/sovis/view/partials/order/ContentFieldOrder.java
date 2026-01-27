package br.com.sovis.view.partials.order;


import br.com.sovis.controller.ClientController;
import br.com.sovis.controller.ProductController;
import br.com.sovis.exception.ButtonException;
import br.com.sovis.model.Client;
import br.com.sovis.model.ItemOrder;
import br.com.sovis.model.Product;
import br.com.sovis.view.style.Variables;
import totalcross.io.IOException;
import totalcross.ui.*;
import totalcross.ui.event.ControlEvent;
import totalcross.ui.event.PressListener;
import totalcross.ui.image.Image;
import totalcross.ui.image.ImageException;

import java.sql.SQLException;
import java.util.ArrayList;

public class ContentFieldOrder extends Container {
    private final ClientController clientController = new ClientController();
    private final ProductController productController = new ProductController();

    ComboBox clientsComboBox;

    ItemOrderTileWithListContainerItem itemOrderTile;

    private final ArrayList<Client> clients = clientController.getClients();

    private ArrayList<Product> products;

    ListContainer listContainer;
    ArrayList<ItemOrderTileWithListContainerItem> items = new ArrayList<>();

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
        ListContainer.Layout layout = listContainer.getLayout(0,2);
        layout.setup();
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
                    itemOrderTile = new ItemOrderTileWithListContainerItem(products, layout);
                    //itemOrderTile = new ItemOrderTile(products);
                    items.add(itemOrderTile);
                    listContainer.addContainer(itemOrderTile);
                    listContainer.reposition();
                }
            });

            deleteButton.addPressListener(new PressListener() {
                @Override
                public void controlPressed(ControlEvent controlEvent) {
                    int selectedItem = listContainer.getSelectedIndex();
                    items.remove(selectedItem);

                    listContainer.removeAll();

                    ItemOrderTileWithListContainerItem[] newItems = new ItemOrderTileWithListContainerItem[items.size()];

                    for (int i = 0; i < newItems.length; i++) {
                        //Corrigir Posição de Inserção dos itens
                        //if (items.get(i).getPos().x)
                        newItems[i] = items.get(i);
                    }

                    listContainer.addContainers(newItems);

                    listContainer.resize();
                    listContainer.reposition();
                    listContainer.repaintNow();
                }
            });

            add(addButton, RIGHT - 16, AFTER - 35, PREFERRED - 10, PREFERRED - 10);

            // Bug de delete aqui!
            add(deleteButton, BEFORE - 4, SAME, PREFERRED - 10, PREFERRED - 10);
        } catch (ImageException | IOException e) {
            throw new ButtonException(e);
        }
        add(listContainer, LEFT, AFTER + 20, FILL, FILL);
    }

    public Client getClient() {
        int idSelectedClients = clientsComboBox.getSelectedIndex();
        return clients.get(idSelectedClients);
    }

    public ArrayList<ItemOrder> getItemOrders() {
        ArrayList<ItemOrder> itemOrders = new ArrayList<>();

        for (ItemOrderTileWithListContainerItem item : items) {
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