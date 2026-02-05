package br.com.sovis.view.screens.product;

import br.com.sovis.controller.ItemOrderController;
import br.com.sovis.controller.ProductController;
import br.com.sovis.exception.ButtonException;
import br.com.sovis.model.ItemOrder;
import br.com.sovis.model.Product;
import br.com.sovis.model.UserLogged;
import br.com.sovis.model.enums.UserType;
import br.com.sovis.view.partials.product.ProductTile;
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

public class ProductScreen extends Container {
    private final ProductController productController = new ProductController();
    private final ItemOrderController itemOrderController = new ItemOrderController();
    private ListContainer listContainer;
    private ArrayList<Product> productsList;
    private final boolean isUserAdmin = UserLogged.userLogged.getUserType().equals(UserType.ADMIN);

    private final int APP_ID_BACK_BUTTON = 999;
    private final int APP_ID_DELETE_BUTTON = 6;
    private final int APP_ID_EDIT_BUTTON =  7;

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

        Label titleLabel = new Label("Produtos");
        titleLabel.setForeColor(Color.WHITE);
        tabBar.add(titleLabel,  AFTER + 8, CENTER);

        add(tabBar);

        add(new MainButton(
                "product.png",
                "Adicionar Produto",
                new AddProductScreen(this),
                isUserAdmin
        ), CENTER, AFTER + 70, PARENTSIZE + 90, PARENTSIZE + 10);

        Label productsTitle = new Label("Lista de Produtos");
        productsTitle.setFont(Font.getFont(true, 15));
        add(productsTitle, LEFT + 10, AFTER + 30);

        Button deleteButton;
        Button editButton;
        try {
            deleteButton = new Button(new Image("trash.png").getScaledInstance(20,20));
            deleteButton.setBackColor(Variables.PRIMARY_COLOR);
            deleteButton.appId = APP_ID_DELETE_BUTTON;
            deleteButton.setEnabled(isUserAdmin);

            editButton = new Button(new Image("edit.png").getScaledInstance(20,20));
            editButton.setBackColor(Variables.PRIMARY_COLOR);
            editButton.appId = APP_ID_EDIT_BUTTON;
            editButton.setEnabled(isUserAdmin);
        } catch (ImageException | IOException e) {
            throw new ButtonException(e);
        }
        add(deleteButton, RIGHT - 10, SAME - 5, PREFERRED - 5, PREFERRED - 5);
        add(editButton, BEFORE - 10, SAME, PREFERRED - 5, PREFERRED - 5);

        try {
            if (isUserAdmin) {
                productsList = productController.getAllProducts();
            } else {
                productsList = productController.getProductsOfUser(UserLogged.userLogged.getId());
            }

            if (productsList.isEmpty()) {
                Label noProductsLabel = new Label("Sem produtos cadastrados");
                noProductsLabel.setForeColor(Variables.PRIMARY_COLOR);
                add(noProductsLabel, CENTER, PARENTSIZE + 60);
            }
            else {
                listContainer = new ListContainer();
                add(listContainer, LEFT, AFTER + 30, FILL, FILL);

                for (Product product : productsList) {
                    ProductTile productTile = new ProductTile(
                            product.getId(),
                            product.getName(),
                            product.getDescription(),
                            product.getPrice()
                    );
                    listContainer.addContainer(productTile);
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
                int indexSelectedItem = listContainer.getSelectedIndex();
                try {
                    if (indexSelectedItem >= 0 && indexSelectedItem < productsList.size()) {
                        Product product = productsList.get(indexSelectedItem);
                        for (ItemOrder itemOrder : itemOrderController.getItemOrders()) {
                            if (itemOrder.getProduct().getId() == product.getId()) {
                                MessageBoxVariables.alreadyExistsAOrderWithThisProduct();
                                return;
                            }
                        }
                        productController.deleteProduct(product.getId());
                        listContainer.remove(listContainer.getContainer(indexSelectedItem));
                        MainWindow.getMainWindow().swap(new ProductScreen());
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
                    if (indexSelectedItem >= 0 && indexSelectedItem < productsList.size()) {
                        Product product = productsList.get(indexSelectedItem);
                        MainWindow.getMainWindow().swap(new EditProductScreen(this, product.getId()));
                    }  else {
                        MessageBoxVariables.notSelectedItem();
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
