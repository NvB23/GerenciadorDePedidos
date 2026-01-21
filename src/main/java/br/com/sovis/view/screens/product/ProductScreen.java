package br.com.sovis.view.screens.product;

import br.com.sovis.view.partials.product.ProductTile;
import br.com.sovis.view.partials.common.TabBar;
import br.com.sovis.view.partials.common.MainButton;
import br.com.sovis.view.screens.order.HomeScreen;
import totalcross.ui.*;
import totalcross.ui.event.ControlEvent;
import totalcross.ui.event.Event;

public class ProductScreen extends Container {
    private ProductTile productTile;

    @Override
    public void initUI() {
        add(new TabBar(new HomeScreen(), "Produtos"), 0, 0, FILL, PARENTSIZE + 8);

        add(new MainButton("product.png", "Adicionar Produto", new AddProductScreen(this)), CENTER, AFTER + 10, PARENTSIZE + 90, PARENTSIZE + 10);

        Label pedidosTitle = new Label("Lista de Produtos");
        add(pedidosTitle, CENTER, AFTER + 30);

        ListContainer listContainer = new ListContainer();
        add(listContainer, LEFT, AFTER + 30, FILL, FILL);
        for (int i = 0; i < 10; i++) {
            productTile = new ProductTile(
                    165*374L,
                    "Produto " + i,
                    "Descrição do produto " + i,
                    234.90 * i
            );
            productTile.appId = i;
            listContainer.addContainer(productTile);
        }
    }

    @Override
    public void onEvent(Event event) {
        if (event.type == ControlEvent.PRESSED && event.target == productTile) {
            for (int i = 0; i < 10; i++) {
                if (((Control) event.target).appId == i) {
                    MainWindow.getMainWindow().swap(new EditProductScreen(new ProductScreen(), 1L));
                }
            }
        }
    }
}
