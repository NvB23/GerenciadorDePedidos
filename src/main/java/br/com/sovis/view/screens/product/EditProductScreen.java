package br.com.sovis.view.screens.product;

import br.com.sovis.controller.ProductController;
import br.com.sovis.exception.ButtonException;
import br.com.sovis.model.Product;
import br.com.sovis.view.style.Variables;
import totalcross.io.IOException;
import totalcross.ui.*;
import totalcross.ui.event.ControlEvent;
import totalcross.ui.event.Event;
import totalcross.ui.gfx.Color;
import totalcross.ui.image.Image;
import totalcross.ui.image.ImageException;

import java.sql.SQLException;

public class EditProductScreen extends Container {
    private final Container toContainer;
    private final Product produtoEdit;
    private final ProductController productController = new ProductController();

    private Edit nameEdit, descriptionEdit, priceEdit;

    private final int APP_ID_SAVE_BUTTON = 0;
    private final int APP_ID_BACK_BUTTON = 999;

    public EditProductScreen(Container toContainer, Long idParaEditar) throws SQLException {
        this.toContainer = toContainer;
        this.produtoEdit = productController.getProductById(idParaEditar);
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
        add(nameLabel, PARENTSIZE + 50, TOP + 60, PARENTSIZE + 90, PREFERRED);
        nameEdit = new Edit();
        nameEdit.setForeColor(Variables.PRIMARY_COLOR);
        nameEdit.setText(produtoEdit.getName());
        add(nameEdit, CENTER, AFTER + 5, PARENTSIZE + 90, PREFERRED - 20);

        Label descriptionLabel = new Label("Descrição do Produto");
        descriptionLabel.setForeColor(Variables.SECOND_COLOR);
        add(descriptionLabel, PARENTSIZE + 50, AFTER + 30 , PARENTSIZE + 90, PREFERRED);
        descriptionEdit = new Edit();
        descriptionEdit.setForeColor(Variables.PRIMARY_COLOR);
        descriptionEdit.setText(produtoEdit.getDescription());
        add(descriptionEdit, CENTER, AFTER + 5, PARENTSIZE + 90, PREFERRED - 20);

        Label priceLabel = new Label("Preço do Produto (R$)");
        priceLabel.setForeColor(Variables.SECOND_COLOR);
        add(priceLabel, PARENTSIZE + 50, AFTER + 30 , PARENTSIZE + 90, PREFERRED);
        priceEdit = new Edit();
        priceEdit.setValidChars("0123456789.");
        priceEdit.setForeColor(Variables.PRIMARY_COLOR);
        priceEdit.setText(String.valueOf(produtoEdit.getPrice()));
        add(priceEdit, CENTER, AFTER + 5, PARENTSIZE + 90, PREFERRED - 20);
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
        Product product = new Product(
                nameEdit.getText(),
                descriptionEdit.getText(),
                Double.parseDouble(priceEdit.getText()));
        productController.updateProduct(produtoEdit.getId(), product);
        MainWindow.getMainWindow().swap(new ProductScreen());
    }
}
