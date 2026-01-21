package br.com.sovis.view.screens.order;

import br.com.sovis.view.partials.order.ContentFieldOrder;
import br.com.sovis.view.partials.common.TabBar;
import totalcross.ui.Container;

import java.util.HashMap;

public class EditOrderScreen extends Container {
    private final Container containerDeVolta;
    private final Long idParaEditar;

    private String nomeAlterado;
    private String Alterado;
    private String telefoneAlterado;

    public EditOrderScreen(Container containerEntraESai, Long idParaEditar) {
        this.containerDeVolta = containerEntraESai;
        this.idParaEditar = idParaEditar;
        setRect(0, 0, FILL, FILL);
    }

    @Override
    public void initUI() {
        HashMap<String, Container> saveBotao = new HashMap<>();
        saveBotao.put("save.png", editarPedido());
        add(new TabBar(this.containerDeVolta, "Editar Pedido", saveBotao), 0, 0, FILL, PARENTSIZE + 8);

        // Recupera o pedido pelo id informado e depois passa para os campos

        // PARA FAZER AINDA!!!!!!

        ContentFieldOrder contentFieldOrder = new ContentFieldOrder();

        add(contentFieldOrder, 0, TOP + 80, FILL, FILL);
    }

    private Container editarPedido() {
        // Recupera o pedido pelo id e altera a tabela através do controller
        System.out.println(idParaEditar);
        // validação e operação
        return this.containerDeVolta;
    }
}
