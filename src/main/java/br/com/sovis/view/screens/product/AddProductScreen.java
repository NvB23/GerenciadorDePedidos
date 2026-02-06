package br.com.sovis.view.screens.product;

import br.com.sovis.controller.ProductController;
import br.com.sovis.exception.ButtonException;
import br.com.sovis.model.Product;
import br.com.sovis.view.partials.product.ContentFieldProduct;
import br.com.sovis.view.style.MessageBoxVariables;
import br.com.sovis.view.style.Variables;
import totalcross.io.IOException;
import totalcross.ui.*;
import totalcross.ui.dialog.MessageBox;
import totalcross.ui.event.ControlEvent;
import totalcross.ui.event.Event;
import totalcross.ui.gfx.Color;
import totalcross.ui.image.Image;
import totalcross.ui.image.ImageException;

import java.sql.SQLException;

public class AddProductScreen extends Container {
    private final Container toContainer;
    private final ContentFieldProduct contentFieldProduct =  new ContentFieldProduct();
    private final ProductController productController = new ProductController();

    private final int APP_ID_BACK_BUTTON = 999;
    private final int APP_ID_SAVE_BUTTON = 0;

    public AddProductScreen(Container toContainer) {
        this.toContainer = toContainer;
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

        Label titleLabel = new Label("Adicionar Produto");
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

        add(contentFieldProduct, 0, TOP + 80, FILL, FILL);
    }

    @Override
    public  void onEvent(Event event) {
        if (event.type == ControlEvent.PRESSED && event.target instanceof Button) {

            Control control = (Control) event.target;

            if (control.appId == APP_ID_SAVE_BUTTON) {
                try {
                    MainWindow.getMainWindow().setFocus(null);
                    saveClient(contentFieldProduct);
                    return;
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }

            if (control.appId == APP_ID_BACK_BUTTON) {
                MainWindow.getMainWindow().swap(toContainer);
            }
        }
    }

    private void saveClient(ContentFieldProduct contentFieldProduto) throws SQLException {
        String name = contentFieldProduto.getNameEdit();
        String description = contentFieldProduto.getDescriptionEdit();
        String price = contentFieldProduto.getPriceEdit();

        if (name.isEmpty() || description.isEmpty() || price.isEmpty()) {
            MessageBoxVariables.fieldsEmpty();
            return;
        }

        if (Double.parseDouble(price) == 0) {
            MessageBoxVariables.productNotCanBeFree();
            return;
        }

        if (Double.parseDouble(price) < 0) {
            MessageBoxVariables.invalidPrice();
            return;
        }

        Product product = new Product(
                name,
                description,
                Double.parseDouble(price)
        );
        productController.createProduct(product);
        MainWindow.getMainWindow().swap(new ProductScreen());
    }
}
