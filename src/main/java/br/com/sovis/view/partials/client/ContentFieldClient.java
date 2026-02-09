package br.com.sovis.view.partials.client;


import br.com.sovis.controller.UserController;
import br.com.sovis.model.User;
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

    private final ArrayList<Long> usersForAssociate = new ArrayList<>();

    private ListContainer listContainer;

    private final ArrayList<AssociatedClientTile> associatedClientTileArrayList = new ArrayList<>();

    public ContentFieldClient() throws SQLException {
        UserController userController = new UserController();
        ArrayList<User> users = userController.getCommonUsers();

        ListContainer.Layout layout = listContainer.getLayout(0, 1);
        layout.setup();

        for (User user : users) {
            AssociatedClientTile associatedClientTile = new AssociatedClientTile(layout, user);
            Long idUser = associatedClientTile.getUserAssociated();
            if (idUser != null) {
                usersForAssociate.add(idUser);
            }
            associatedClientTileArrayList.add(associatedClientTile);
        }
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

        Label associatedLabel = new Label("Clientes Associados");
        associatedLabel.setForeColor(Variables.SECOND_COLOR);
        add(associatedLabel, PARENTSIZE + 50, AFTER + 20 , PARENTSIZE + 90, PREFERRED);

        listContainer = new ListContainer();

        listContainer.setRect(CENTER, PARENTSIZE + 82, PARENTSIZE + 90, PARENTSIZE + 60);
        listContainer.highlightColor = Color.WHITE;
        add(listContainer);

        for (AssociatedClientTile associatedClientTile : associatedClientTileArrayList) {
            listContainer.addContainer(associatedClientTile);
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
        return usersForAssociate;
    }
}
