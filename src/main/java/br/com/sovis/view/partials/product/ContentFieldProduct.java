package br.com.sovis.view.partials.product;


import br.com.sovis.exception.AttributesNullException;
import br.com.sovis.view.style.Variables;
import totalcross.ui.Container;
import totalcross.ui.Edit;
import totalcross.ui.Label;

public class ContentFieldProduct extends Container {
    private String nome;
    private String descricao;
    private String preco;

    private String nomeAlterado;
    private String descricaoAlterado;
    private String precoAlterado;

    public ContentFieldProduct(String nome, String descricao, Double preco) {
        if (nome == null || descricao == null || preco == null) {
            throw new AttributesNullException("Atributos passados não podem ser nulos");
        }

        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco.toString();
    }

    public ContentFieldProduct() {
        this.nome = "";
        this.descricao = "";
        this.preco = "";
    }

    @Override
    public void initUI() {
        Label nomeLabel = new Label("Nome do Produto");
        nomeLabel.setForeColor(Variables.SECOND_COLOR);
        add(nomeLabel, PARENTSIZE + 50, TOP, PARENTSIZE + 90, PREFERRED);
        Edit nomeEdit = new Edit();
        nomeEdit.setForeColor(Variables.PRIMARY_COLOR);
        add(nomeEdit, CENTER, AFTER + 5, PARENTSIZE + 90, PREFERRED - 20);

        nomeAlterado = nomeEdit.getText();

        Label descricaoLabel = new Label("Descrição do Produto");
        descricaoLabel.setForeColor(Variables.SECOND_COLOR);
        add(descricaoLabel, PARENTSIZE + 50, AFTER + 30 , PARENTSIZE + 90, PREFERRED);
        Edit descricaoEdit = new Edit();
        descricaoEdit.setForeColor(Variables.PRIMARY_COLOR);
        add(descricaoEdit, CENTER, AFTER + 5, PARENTSIZE + 90, PREFERRED - 20);

        descricaoAlterado = descricaoEdit.getText();

        Label precoLabel = new Label("Preço do Produto (R$)");
        precoLabel.setForeColor(Variables.SECOND_COLOR);
        add(precoLabel, PARENTSIZE + 50, AFTER + 30 , PARENTSIZE + 90, PREFERRED);
        Edit precoEdit = new Edit();
        precoEdit.setValidChars("0123456789.,");
        precoEdit.setForeColor(Variables.PRIMARY_COLOR);
        add(precoEdit, CENTER, AFTER + 5, PARENTSIZE + 90, PREFERRED - 20);

        precoAlterado = precoEdit.getText();

        nomeEdit.setText(nome);
        descricaoEdit.setText(descricao);
        precoEdit.setText(preco);
    }

    public String getNomeAlterado() {
        return nomeAlterado;
    }

    public String getDescricaoAlterado() {
        return descricaoAlterado;
    }

    public String getPrecoAlterado() {
        return precoAlterado;
    }
}
