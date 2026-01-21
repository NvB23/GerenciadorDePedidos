package br.com.sovis.view.screens.product;

import br.com.sovis.view.partials.product.ContentFieldProduct;
import br.com.sovis.view.partials.common.TabBar;
import br.com.sovis.view.screens.order.HomeScreen;
import totalcross.ui.Container;

import java.util.HashMap;

public class EditProductScreen extends Container {
    private final Container containerDeVolta;
    private final Long idParaEditar;

    private String nomeAlterado;
    private String descricaoAlterado;
    private String precoAlterado;

    public EditProductScreen(Container containerDeVolta, Long idParaEditar) {
        this.containerDeVolta = containerDeVolta;
        this.idParaEditar = idParaEditar;
        setRect(0, 0, FILL, FILL);
    }

    @Override
    public void initUI() {
        HashMap<String, Container> saveBotao = new HashMap<>();
        saveBotao.put("save.png", editarCliente());
        add(new TabBar(new HomeScreen(), "Editar Produto", saveBotao), 0, 0, FILL, PARENTSIZE + 8);

        // Recupera o produto pelo id informado e depois passa para os campos

        ContentFieldProduct contentFieldProduct = new ContentFieldProduct("Coca Col", "Latinha de Coca Cola", 123.45);

        nomeAlterado = contentFieldProduct.getNomeAlterado();
        descricaoAlterado = contentFieldProduct.getDescricaoAlterado();
        precoAlterado = contentFieldProduct.getPrecoAlterado();

        add(contentFieldProduct, 0, TOP + 80, FILL, FILL);
    }

    private Container editarCliente() {
        // Recupera o produto pelo id e altera a tabela através do controller
        System.out.println(idParaEditar);

        // Dados de modificação
        System.out.println(nomeAlterado);
        System.out.println(descricaoAlterado);
        System.out.println(precoAlterado);

        // validação e operação
        return this.containerDeVolta;
    }
}
