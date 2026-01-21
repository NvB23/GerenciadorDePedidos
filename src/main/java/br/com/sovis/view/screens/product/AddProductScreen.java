package br.com.sovis.view.screens.product;

import br.com.sovis.view.partials.product.ContentFieldProduct;
import br.com.sovis.view.partials.common.TabBar;
import br.com.sovis.view.screens.order.HomeScreen;
import totalcross.ui.Container;

import java.util.HashMap;

public class AddProductScreen extends Container {
    private final Container containerDeVolta;

    public AddProductScreen(Container containerDeVolta) {
        this.containerDeVolta = containerDeVolta;
        setRect(0, 0, FILL, FILL);
    }

    @Override
    public void initUI() {
        HashMap<String, Container> saveBotao = new HashMap<>();
        saveBotao.put("save.png", salvarCliente());
        add(new TabBar(new HomeScreen(), "Adicionar Produto", saveBotao), 0, 0, FILL, PARENTSIZE + 8);

        add(new ContentFieldProduct(), 0, TOP + 80, FILL, FILL);
    }

    private Container salvarCliente() {
        // validação e operação
        return this.containerDeVolta;
    }
}
