package br.com.sovis.view.partials.product;


import br.com.sovis.controller.UserController;
import br.com.sovis.model.User;
import br.com.sovis.view.partials.common.AssociatedTile;
import br.com.sovis.view.style.Variables;
import totalcross.ui.Container;
import totalcross.ui.Edit;
import totalcross.ui.Label;
import totalcross.ui.ListContainer;
import totalcross.ui.gfx.Color;

import java.sql.SQLException;
import java.util.ArrayList;

public class ContentFieldProduct extends Container {
    private Edit nameEdit;
    private Edit descriptionEdit;
    private Edit priceEdit;

    private final ArrayList<AssociatedTile> associatedTileArrayList = new ArrayList<>();
    private final ArrayList<User> users;

    public ContentFieldProduct() throws SQLException {
        UserController userController = new UserController();
        users = userController.getCommonUsers();
    }

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
        add(descriptionLabel, PARENTSIZE + 50, AFTER + 20 , PARENTSIZE + 90, PREFERRED);
        descriptionEdit = new Edit();
        descriptionEdit.setForeColor(Variables.PRIMARY_COLOR);
        add(descriptionEdit, CENTER, AFTER + 5, PARENTSIZE + 90, PREFERRED - 20);

        Label priceLabel = new Label("Preço do Produto (R$)");
        priceLabel.setForeColor(Variables.SECOND_COLOR);
        add(priceLabel, PARENTSIZE + 50, AFTER + 20 , PARENTSIZE + 90, PREFERRED);
        priceEdit = new Edit();
        priceEdit.setValidChars("0123456789.");
        priceEdit.setForeColor(Variables.PRIMARY_COLOR);
        add(priceEdit, CENTER, AFTER + 5, PARENTSIZE + 90, PREFERRED - 20);

        Label associatedLabel = new Label("Usuários Associados");
        associatedLabel.setForeColor(Variables.SECOND_COLOR);
        add(associatedLabel, PARENTSIZE + 50, AFTER + 20 , PARENTSIZE + 90, PREFERRED);

        ListContainer listContainer = new ListContainer();
        ListContainer.Layout layout = listContainer.getLayout(0, 1);
        layout.setup();
        listContainer.setRect(CENTER, PARENTSIZE + 72, PARENTSIZE + 90, PARENTSIZE + 40);
        listContainer.highlightColor = Color.WHITE;
        add(listContainer);

        for (User user : users) {
            AssociatedTile associatedTile = new AssociatedTile(layout, user);
            listContainer.addContainer(associatedTile);
            associatedTileArrayList.add(associatedTile);
        }
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

    public ArrayList<Long> getUsersForAssociate() {
        final ArrayList<Long> usersForAssociate = new ArrayList<>();

        for (AssociatedTile associatedTile : associatedTileArrayList) {
            if (associatedTile.getUserAssociated() != null) {
                usersForAssociate.add(associatedTile.getUserAssociated());
            }
        }

        return usersForAssociate;
    }
}
