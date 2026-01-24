package br.com.sovis.view.screens.client;

import br.com.sovis.controller.ClientController;
import br.com.sovis.exception.ButtonException;
import br.com.sovis.model.Client;
import br.com.sovis.view.style.Variables;
import totalcross.io.IOException;
import totalcross.ui.*;
import totalcross.ui.event.ControlEvent;
import totalcross.ui.event.Event;
import totalcross.ui.gfx.Color;
import totalcross.ui.image.Image;
import totalcross.ui.image.ImageException;

import java.sql.SQLException;

public class EditClientScreen extends Container {
    private final Container toContainer;
    private final Client clientEdit;
    private final ClientController clientController = new ClientController();
    private Edit nameEdit, emailEdit, phoneEdit;

    public EditClientScreen(Container toContainer, Long idToEdit) throws SQLException {
        this.toContainer = toContainer;
        this.clientEdit = clientController.getClientById(idToEdit);
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
            backButton.appId = 999;
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
        saveButton.appId = 5;
        tabBar.add(saveButton, RIGHT, CENTER);

        add(tabBar);

        Label nameLabel = new Label("Nome do Cliente");
        nameLabel.setForeColor(Variables.SECOND_COLOR);
        add(nameLabel, PARENTSIZE + 50, TOP + 60, PARENTSIZE + 90, PREFERRED);
        nameEdit = new Edit();
        nameEdit.setForeColor(Variables.PRIMARY_COLOR);
        nameEdit.setText(clientEdit.getName());
        add(nameEdit, CENTER, AFTER + 5, PARENTSIZE + 90, PREFERRED - 20);

        Label emailLabel = new Label("Email do Cliente");
        emailLabel.setForeColor(Variables.SECOND_COLOR);
        add(emailLabel, PARENTSIZE + 50, AFTER + 30 , PARENTSIZE + 90, PREFERRED);
        emailEdit = new Edit();
        emailEdit.setForeColor(Variables.PRIMARY_COLOR);
        emailEdit.setText(clientEdit.getEmail());
        add(emailEdit, CENTER, AFTER + 5, PARENTSIZE + 90, PREFERRED - 20);

        Label phoneLabel = new Label("Telefone do Cliente");
        phoneLabel.setForeColor(Variables.SECOND_COLOR);
        add(phoneLabel, PARENTSIZE + 50, AFTER + 30 , PARENTSIZE + 90, PREFERRED);
        phoneEdit = new Edit("99 99999-9999");
        phoneEdit.setValidChars("0123456789");
        phoneEdit.setForeColor(Variables.PRIMARY_COLOR);
        phoneEdit.setText(clientEdit.getPhone());
        phoneEdit.setMode(Edit.NORMAL, true);
        add(phoneEdit, CENTER, AFTER + 5, PARENTSIZE + 90, PREFERRED - 20);
    }

    @Override
    public void onEvent(Event event) {
        if (event.type == ControlEvent.PRESSED && event.target instanceof Button) {
            Control control = (Control) event.target;

            if (control.appId == 999) {
                MainWindow.getMainWindow().swap(this.toContainer);
            }

            if (control.appId == 5) {
                try {
                    editClient();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    private void editClient() throws SQLException {
        Client client = new Client(nameEdit.getText(), emailEdit.getText(), phoneEdit.getText());
        clientController.updateClient(clientEdit.getId(), client);
        MainWindow.getMainWindow().swap(new ClientScreen());
    }
}
