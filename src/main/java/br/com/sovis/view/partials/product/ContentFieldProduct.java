package br.com.sovis.view.partials.product;


import br.com.sovis.view.style.Variables;
import totalcross.ui.Container;
import totalcross.ui.Edit;
import totalcross.ui.Label;

public class ContentFieldProduct extends Container {
    private Edit nameEdit;
    private Edit descriptionEdit;
    private Edit priceEdit;

    public ContentFieldProduct() {}

    @Override
    public void initUI() {
        Label nameLabel = new Label("Nome do Produto");
        nameLabel.setForeColor(Variables.SECOND_COLOR);
        add(nameLabel, PARENTSIZE + 50, TOP, PARENTSIZE + 90, PREFERRED);
        nameEdit = new Edit();
        nameEdit.setForeColor(Variables.PRIMARY_COLOR);
        add(nameEdit, CENTER, AFTER + 5, PARENTSIZE + 90, PREFERRED - 20);

        Label descriptionLabel = new Label("Descrição do Produto");
        descriptionLabel.setForeColor(Variables.SECOND_COLOR);
        add(descriptionLabel, PARENTSIZE + 50, AFTER + 30 , PARENTSIZE + 90, PREFERRED);
        descriptionEdit = new Edit();
        descriptionEdit.setForeColor(Variables.PRIMARY_COLOR);
        add(descriptionEdit, CENTER, AFTER + 5, PARENTSIZE + 90, PREFERRED - 20);

        Label priceLabel = new Label("Preço do Produto (R$)");
        priceLabel.setForeColor(Variables.SECOND_COLOR);
        add(priceLabel, PARENTSIZE + 50, AFTER + 30 , PARENTSIZE + 90, PREFERRED);
        priceEdit = new Edit();
        priceEdit.setValidChars("0123456789.");
        priceEdit.setForeColor(Variables.PRIMARY_COLOR);
        add(priceEdit, CENTER, AFTER + 5, PARENTSIZE + 90, PREFERRED - 20);
    }

    public String getPriceEdit() {
        return priceEdit.getText().replace(",", ".");
    }

    public String getDescriptionEdit() {
        return descriptionEdit.getText();
    }

    public String getNameEdit() {
        return nameEdit.getText();
    }
}
