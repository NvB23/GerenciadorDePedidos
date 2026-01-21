package br.com.sovis.view.screens.order;

import br.com.sovis.view.partials.order.ContentFieldOrder;
import br.com.sovis.view.partials.common.TabBar;
import totalcross.ui.Container;

import java.util.HashMap;

public class AddOrderScreen extends Container {
    private final Container containerDeVolta;

    public AddOrderScreen(Container containerEntraESai) {
        this.containerDeVolta = containerEntraESai;
        setRect(0, 0, FILL, FILL);
    }

    @Override
    public void initUI() {
        HashMap<String, Container> saveBotao = new HashMap<>();
        saveBotao.put("save.png", salvarPedido());
        add(new TabBar(this.containerDeVolta, "Adicionar Pedido", saveBotao), 0, 0, FILL, PARENTSIZE + 8);

        add(new ContentFieldOrder(), 0, TOP + 80, FILL, FILL);
    }

    private Container salvarPedido() {
        // validação e operação
        return this.containerDeVolta;
    }
}
