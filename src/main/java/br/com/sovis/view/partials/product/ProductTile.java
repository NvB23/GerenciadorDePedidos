package br.com.sovis.view.partials.product;

import br.com.sovis.view.style.Variables;
import totalcross.ui.*;

public class ProductTile extends ScrollContainer {
    private final Long idProduct;
    private final String name;
    private final String description;
    private final Double price;

    public ProductTile(Long idPedido, String name, String description, Double price) {
        super(false);
        this.idProduct = idPedido;
        this.name = name;
        this.description = description;
        this.price = price;
    }
    @Override
    public void initUI() {
        Label idProdutoLabel = new Label("ID Produto - " + idProduct);
        idProdutoLabel.setForeColor(Variables.PRIMARY_COLOR);
        add(idProdutoLabel, LEFT + 8, TOP + 8, FILL, PREFERRED);

        Label nomeLabel = new Label(name);
        nomeLabel.setForeColor((Variables.SECOND_COLOR));
        add(nomeLabel, LEFT + 8, AFTER + 16, FILL, PREFERRED);

        Label descricaoLabel = new Label(description);
        add(descricaoLabel, LEFT + 8, AFTER + 16, FILL, PREFERRED);

        Label precoLabel = new Label("R$" + String.format("%.2f", price));
        add(precoLabel, LEFT + 8, AFTER + 16, FILL, PREFERRED);
    }
}
