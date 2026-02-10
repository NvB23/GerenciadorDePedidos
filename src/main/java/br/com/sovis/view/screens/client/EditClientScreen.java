package br.com.sovis.view.screens.client;

import br.com.sovis.controller.ClientController;
import br.com.sovis.controller.UserController;
import br.com.sovis.exception.ButtonException;
import br.com.sovis.model.Client;
import br.com.sovis.model.User;
import br.com.sovis.view.partials.client.AssociatedClientTile;
import br.com.sovis.view.style.MessageBoxVariables;
import br.com.sovis.view.style.Variables;
import totalcross.io.IOException;
import totalcross.ui.*;
import totalcross.ui.event.ControlEvent;
import totalcross.ui.event.Event;
import totalcross.ui.gfx.Color;
import totalcross.ui.image.Image;
import totalcross.ui.image.ImageException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class EditClientScreen extends Container {
    private final Container toContainer;
    private final Client clientEdit;
    private final ClientController clientController = new ClientController();
    private Edit nameEdit, emailEdit, phoneEdit;

    private final ArrayList<AssociatedClientTile> associatedClientTileArrayList = new ArrayList<>();
    private final ArrayList<User> users;

    private final int APP_ID_SAVE_BUTTON = 5;
    private final int APP_ID_BACK_BUTTON = 999;

    public EditClientScreen(Container toContainer, Long idToEdit) throws SQLException {
        this.toContainer = toContainer;
        this.clientEdit = clientController.getClientById(idToEdit);
        UserController userController = new UserController();
        users = userController.getCommonUsers();
        setRect(0, 0, FILL, FILL);
    }

    @Override
    public void initUI() {
        Container tabBar = new Container();
        tabBar.setBackColor(Variables.PRIMARY_COLOR);
        tabBar.setRect(0,0, FILL, PARENTSIZE + 8);

        try {
            Button backButton = new Button(new Image("back-arrow.png").getScaledInstance(20, 20));
            backButton.setBackColor(Variables.PRIMARY_COLOR);
            backButton.appId = APP_ID_BACK_BUTTON;
            tabBar.add(backButton, LEFT, TOP);
        } catch (ImageException | IOException e) {
            throw new ButtonException(e);
        }

        Label titleLabel = new Label("Editar Cliente");
        titleLabel.setForeColor(Color.WHITE);
        tabBar.add(titleLabel,  AFTER + 8, CENTER);

        Button saveButton;
        try {
            saveButton = new Button(new Image("save.png").getScaledInstance(30, 30));
        } catch (ImageException | IOException e) {
            throw new ButtonException(e);
        }

        saveButton.setBackColor(Variables.PRIMARY_COLOR);
        saveButton.appId = APP_ID_SAVE_BUTTON;
        tabBar.add(saveButton, RIGHT, CENTER);

        add(tabBar);

        Label nameLabel = new Label("Nome do Cliente");
        nameLabel.setForeColor(Variables.SECOND_COLOR);
        add(nameLabel, PARENTSIZE + 50, TOP + 55, PARENTSIZE + 90, PREFERRED);
        nameEdit = new Edit();
        nameEdit.setForeColor(Variables.PRIMARY_COLOR);
        nameEdit.setText(clientEdit.getName());
        add(nameEdit, CENTER, AFTER + 5, PARENTSIZE + 90, PREFERRED - 20);

        Label emailLabel = new Label("Email do Cliente");
        emailLabel.setForeColor(Variables.SECOND_COLOR);
        add(emailLabel, PARENTSIZE + 50, AFTER + 20 , PARENTSIZE + 90, PREFERRED);
        emailEdit = new Edit();
        emailEdit.setForeColor(Variables.PRIMARY_COLOR);
        emailEdit.setText(clientEdit.getEmail());
        add(emailEdit, CENTER, AFTER + 5, PARENTSIZE + 90, PREFERRED - 20);

        Label phoneLabel = new Label("Telefone do Cliente");
        phoneLabel.setForeColor(Variables.SECOND_COLOR);
        add(phoneLabel, PARENTSIZE + 50, AFTER + 20 , PARENTSIZE + 90, PREFERRED);
        phoneEdit = new Edit("99 99999-9999");
        phoneEdit.setValidChars("0123456789");
        phoneEdit.setForeColor(Variables.PRIMARY_COLOR);
        phoneEdit.setText(clientEdit.getPhone());
        phoneEdit.setMode(Edit.NORMAL, true);
        add(phoneEdit, CENTER, AFTER + 5, PARENTSIZE + 90, PREFERRED - 20);

        Label associatedLabel = new Label("Clientes Associados");
        associatedLabel.setForeColor(Variables.SECOND_COLOR);
        add(associatedLabel, PARENTSIZE + 50, AFTER + 20 , PARENTSIZE + 90, PREFERRED);

        ListContainer listContainer = new ListContainer();
        ListContainer.Layout layout = listContainer.getLayout(0, 1);
        layout.setup();
        listContainer.setRect(CENTER, PARENTSIZE + 82, PARENTSIZE + 90, PARENTSIZE + 40);
        listContainer.highlightColor = Color.WHITE;
        add(listContainer);

        for (User user : users) {
            AssociatedClientTile associatedClientTile = new AssociatedClientTile(layout, user);
            listContainer.addContainer(associatedClientTile);
            associatedClientTileArrayList.add(associatedClientTile);
        }
    }

    @Override
    public void onEvent(Event event) {
        if (event.type == ControlEvent.PRESSED && event.target instanceof Button) {
            Control control = (Control) event.target;

            if (control.appId == APP_ID_BACK_BUTTON) {
                MainWindow.getMainWindow().swap(this.toContainer);
            }

            if (control.appId == APP_ID_SAVE_BUTTON) {
                try {
                    editClient();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    private void editClient() throws SQLException {
        String name = nameEdit.getText();
        String email = emailEdit.getText();
        String phone = phoneEdit.getText();

        if (name.trim().isEmpty() || email.trim().isEmpty() || phone.trim().isEmpty()) {
            MessageBoxVariables.fieldsEmpty();
            return;
        }

        if (!Pattern.matches("[a-zA-Z0-9._]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}", email)) {
            MessageBoxVariables.invalidEmail();
            return;
        }

        for (char c : phone.toCharArray()) {
            if (Character.isLetter(c)) {
                MessageBoxVariables.invalidPhone();
                return;
            }
        }

        ArrayList<Long> usersForAssociatedEdit = new ArrayList<>();

        for (AssociatedClientTile associatedClientTile : associatedClientTileArrayList) {
            if (associatedClientTile.getUserAssociated() != null) {
                usersForAssociatedEdit.add(associatedClientTile.getUserAssociated());
            }
        }

        Client client = new Client(name, email, phone);
        clientController.updateClient(clientEdit.getId(), client, usersForAssociatedEdit);
        MainWindow.getMainWindow().swap(new ClientScreen());
    }
}
