package br.com.sovis.view.partials.product;

import br.com.sovis.view.style.Variables;
import totalcross.ui.Label;
import totalcross.ui.ScrollContainer;
import totalcross.ui.font.Font;

public class ProductTile extends ScrollContainer {
    private final Long idProduto;
    private final String nome;
    private final String descricao;
    private final Double preco;

    public ProductTile(Long idPedido, String nome, String descricao, Double preco) {
        super(false);

        this.idProduto = idPedido;
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
    }
    @Override
    public void initUI() {
        Label idProdutoLabel = new Label("ID Produto - " + idProduto);
        idProdutoLabel.setForeColor(Variables.PRIMARY_COLOR);
        add(idProdutoLabel, LEFT + 8, TOP + 8, FILL, PREFERRED);

        Label nomeLabel = new Label(nome);
        nomeLabel.setForeColor((Variables.SECOND_COLOR));
        add(nomeLabel, LEFT + 8, AFTER + 16, FILL, PREFERRED);

        Label descricaoLabel = new Label(descricao);
        add(descricaoLabel, LEFT + 8, AFTER + 16, FILL, PREFERRED);

        Label precoLabel = new Label("R$" + preco);
        precoLabel.setFont(Font.getFont(true, 16));
        add(precoLabel, LEFT + 8, AFTER + 16, FILL, PREFERRED);
    }
}
