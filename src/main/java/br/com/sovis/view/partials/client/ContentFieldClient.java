package br.com.sovis.view.partials.client;


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

public class ContentFieldClient extends Container {
    private Edit nameEdit;
    private Edit emailEdit;
    private Edit phoneEdit;

    private final ArrayList<AssociatedTile> associatedTileArrayList = new ArrayList<>();
    private final ArrayList<User> users;

    public ContentFieldClient() throws SQLException {
        UserController userController = new UserController();
        users = userController.getCommonUsers();
    }

    @Override
    public void initUI() {
        Label nameLabel = new Label("Nome do Cliente");
        nameLabel.setForeColor(Variables.SECOND_COLOR);
        add(nameLabel, PARENTSIZE + 50, TOP, PARENTSIZE + 90, PREFERRED);
        nameEdit = new Edit();
        nameEdit.setForeColor(Variables.PRIMARY_COLOR);
        add(nameEdit, CENTER, AFTER + 5, PARENTSIZE + 90, PREFERRED - 20);

        Label emailLabel = new Label("Email do Cliente");
        emailLabel.setForeColor(Variables.SECOND_COLOR);
        add(emailLabel, PARENTSIZE + 50, AFTER + 20 , PARENTSIZE + 90, PREFERRED);
        emailEdit = new Edit();
        emailEdit.setForeColor(Variables.PRIMARY_COLOR);
        add(emailEdit, CENTER, AFTER + 5, PARENTSIZE + 90, PREFERRED - 20);

        Label phoneLabel = new Label("Telefone do Cliente");
        phoneLabel.setForeColor(Variables.SECOND_COLOR);
        add(phoneLabel, PARENTSIZE + 50, AFTER + 20 , PARENTSIZE + 90, PREFERRED);
        phoneEdit = new Edit("99 99999-9999");
        phoneEdit.setForeColor(Variables.PRIMARY_COLOR);
        phoneEdit.setMode(Edit.NORMAL, true);
        add(phoneEdit, CENTER, AFTER + 5, PARENTSIZE + 90, PREFERRED - 20);

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

    public String getNameEdit() {
        return nameEdit.getText();
    }

    public String getEmailEdit() {
        return emailEdit.getText();
    }

    public String getPhoneEdit() {
        return phoneEdit.getText();
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
