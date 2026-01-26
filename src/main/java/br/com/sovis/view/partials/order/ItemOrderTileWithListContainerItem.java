package br.com.sovis.view.partials.order;

import br.com.sovis.model.Product;
import br.com.sovis.view.style.Variables;
import totalcross.ui.ComboBox;
import totalcross.ui.Edit;
import totalcross.ui.ListContainer;
import totalcross.ui.layout.VBox;

import java.sql.SQLException;
import java.util.List;

public class ItemOrderTileWithListContainerItem extends ListContainer.Item {
    private final List<Product> productList;
    private Edit quantityEdit;
    private ComboBox comboBox;

    private final String quantity;
    private final String product;


    public ItemOrderTileWithListContainerItem(List<Product> productList) {

        this.productList = productList;
        this.quantity = "";
        this.product = "";
        setRect(0, 0, FILL, PARENTSIZE + 12);
    }

    public ItemOrderTileWithListContainerItem(List<Product> productList, String quantity, String product) throws SQLException {
        super(null);
        this.productList = productList;
        this.quantity = quantity;
        this.product = product;
        setRect(0, 0, FILL, PARENTSIZE + 12);
    }

    @Override
    public void initUI() {
        String[] productsName = new String[productList.size()];

        for (int i = 0; i < productList.size(); i++) {
            productsName[i] = productList.get(i).getId() + " " + productList.get(i).getName();
        }
        comboBox = new ComboBox(productsName);
        comboBox.setForeColor(Variables.PRIMARY_COLOR);
        comboBox.setValue(product);

        quantityEdit = new Edit();
        quantityEdit.setValidChars("0123456789.,");
        quantityEdit.setText(quantity);

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