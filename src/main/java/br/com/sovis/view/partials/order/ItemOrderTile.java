package br.com.sovis.view.partials.order;

import br.com.sovis.model.Product;
import br.com.sovis.view.style.Variables;
import totalcross.ui.ComboBox;
import totalcross.ui.Container;
import totalcross.ui.Edit;

import java.util.List;

public class ItemOrderTile extends Container {
    private final List<Product> productList;
    Edit quantityEdit;
    ComboBox comboBox;

    public ItemOrderTile(List<Product> productList) {
        this.productList = productList;
        setRect(0, 0, FILL, PREFERRED);
    }

    @Override
    public void initUI() {
        String[] productsName = new String[productList.size()];

        for (int i = 0; i < productList.size(); i++) {
            productsName[i] = productList.get(i).getId() + " " + productList.get(i).getName();
        }
        comboBox = new ComboBox(productsName);
        comboBox.setForeColor(Variables.PRIMARY_COLOR);

        quantityEdit = new Edit();
        quantityEdit.setValidChars("0123456789.,");

        add(quantityEdit, LEFT + 8, CENTER, PARENTSIZE + 20, PREFERRED - 14);
        add(comboBox, AFTER + 8, CENTER, PARENTSIZE + 70, PREFERRED);
    }

    public Integer getQuantidade() {
        return quantityEdit.getText().isEmpty() ? 0 : Integer.parseInt(quantityEdit.getText());
    }

    public Product getProduto() {
        return productList.get(comboBox.getSelectedIndex());
    }
}