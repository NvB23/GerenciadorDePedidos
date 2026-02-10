package br.com.sovis.view.screens.product;

import br.com.sovis.controller.ProductController;
import br.com.sovis.controller.UserClientController;
import br.com.sovis.controller.UserController;
import br.com.sovis.controller.UserProductController;
import br.com.sovis.exception.ButtonException;
import br.com.sovis.model.Product;
import br.com.sovis.model.User;
import br.com.sovis.view.partials.common.AssociatedTile;
import br.com.sovis.view.style.MessageBoxVariables;
import br.com.sovis.view.style.Variables;
import totalcross.io.IOException;
import totalcross.ui.*;
import totalcross.ui.event.ControlEvent;
import totalcross.ui.event.Event;
import totalcross.ui.gfx.Color;
import totalcross.ui.image.Image;
import totalcross.ui.image.ImageException;

import java.sql.SQLException;
import java.util.ArrayList;

public class EditProductScreen extends Container {
    private final Container toContainer;
    private final Product produtoEdit;
    private final ProductController productController = new ProductController();

    private Edit nameEdit, descriptionEdit, priceEdit;

    private final ArrayList<AssociatedTile> associatedTileArrayList = new ArrayList<>();
    private final ArrayList<User> users;
    private final ArrayList<Long> usersAssociated;

    private final int APP_ID_SAVE_BUTTON = 0;
    private final int APP_ID_BACK_BUTTON = 999;

    public EditProductScreen(Container toContainer, Long idToEdit) throws SQLException {
        this.toContainer = toContainer;
        this.produtoEdit = productController.getProductById(idToEdit);
        UserController userController = new UserController();
        users = userController.getCommonUsers();
        UserProductController userProductController = new UserProductController();
        usersAssociated = userProductController.getUserProductByIdProduct(idToEdit);
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

        Label titleLabel = new Label("Editar Produto");
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

        Label nameLabel = new Label("Nome do Produto");
        nameLabel.setForeColor(Variables.SECOND_COLOR);
        add(nameLabel, PARENTSIZE + 50, TOP + 55, PARENTSIZE + 90, PREFERRED);
        nameEdit = new Edit();
        nameEdit.setForeColor(Variables.PRIMARY_COLOR);
        nameEdit.setText(produtoEdit.getName());
        add(nameEdit, CENTER, AFTER + 5, PARENTSIZE + 90, PREFERRED - 20);

        Label descriptionLabel = new Label("Descrição do Produto");
        descriptionLabel.setForeColor(Variables.SECOND_COLOR);
        add(descriptionLabel, PARENTSIZE + 50, AFTER + 20 , PARENTSIZE + 90, PREFERRED);
        descriptionEdit = new Edit();
        descriptionEdit.setForeColor(Variables.PRIMARY_COLOR);
        descriptionEdit.setText(produtoEdit.getDescription());
        add(descriptionEdit, CENTER, AFTER + 5, PARENTSIZE + 90, PREFERRED - 20);

        Label priceLabel = new Label("Preço do Produto (R$)");
        priceLabel.setForeColor(Variables.SECOND_COLOR);
        add(priceLabel, PARENTSIZE + 50, AFTER + 20 , PARENTSIZE + 90, PREFERRED);
        priceEdit = new Edit();
        priceEdit.setValidChars("0123456789.");
        priceEdit.setForeColor(Variables.PRIMARY_COLOR);
        priceEdit.setText(String.valueOf(produtoEdit.getPrice()));
        add(priceEdit, CENTER, AFTER + 5, PARENTSIZE + 90, PREFERRED - 20);

        Label associatedLabel = new Label("Clientes Associados");
        associatedLabel.setForeColor(Variables.SECOND_COLOR);
        add(associatedLabel, PARENTSIZE + 50, AFTER + 20 , PARENTSIZE + 90, PREFERRED);

        ListContainer listContainer = new ListContainer();
        ListContainer.Layout layout = listContainer.getLayout(0, 1);
        layout.setup();
        listContainer.setRect(CENTER, PARENTSIZE + 82, PARENTSIZE + 90, PARENTSIZE + 40);
        listContainer.highlightColor = Color.WHITE;
        add(listContainer);

        for (User user : users) {
            AssociatedTile associatedTile;
            if (usersAssociated.contains(user.getId())) {
                associatedTile = new AssociatedTile(layout, user, true);
            } else {
                associatedTile = new AssociatedTile(layout, user);
            }
            listContainer.addContainer(associatedTile);
            associatedTileArrayList.add(associatedTile);
        }
    }

    @Override
    public void onEvent(Event event) {
        if (event.type == ControlEvent.PRESSED && event.target instanceof Button) {
            if (((Control) event.target).appId == APP_ID_BACK_BUTTON) {
                MainWindow.getMainWindow().swap(toContainer);
            }
            if (((Control) event.target).appId == APP_ID_SAVE_BUTTON) {
                try {
                    editClient();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    private void editClient() throws SQLException {
        String name = nameEdit.getText();
        String description = descriptionEdit.getText();
        String price = priceEdit.getText();

        if (name.isEmpty() || description.isEmpty() || price.isEmpty()) {
            MessageBoxVariables.fieldsEmpty();
            return;
        }
        Product product = new Product(
                name,
                description,
                Double.parseDouble(priceEdit.getText()));
        ArrayList<Long> usersForAssociatedEdit = new ArrayList<>();

        for (AssociatedTile associatedTile : associatedTileArrayList) {
            if (associatedTile.getUserAssociated() != null) {
                usersForAssociatedEdit.add(associatedTile.getUserAssociated());
            }
        }

        productController.updateProduct(produtoEdit.getId(), product, usersForAssociatedEdit);
        MainWindow.getMainWindow().swap(new ProductScreen());
    }
}
