package br.com.sovis.view.screens.client;

import br.com.sovis.controller.ClientController;
import br.com.sovis.exception.ButtonException;
import br.com.sovis.model.Client;
import br.com.sovis.view.partials.client.ContentFieldClient;
import br.com.sovis.view.style.Variables;
import totalcross.io.IOException;
import totalcross.ui.*;
import totalcross.ui.dialog.MessageBox;
import totalcross.ui.event.ControlEvent;
import totalcross.ui.event.Event;
import totalcross.ui.gfx.Color;
import totalcross.ui.image.Image;
import totalcross.ui.image.ImageException;

import java.sql.SQLException;

public class AddClientScreen extends Container {
    private final ClientController clienteController = new ClientController();
    private final Container toContainer;
    ContentFieldClient contentFieldCliente = new ContentFieldClient();


    public AddClientScreen(Container toContainer) {
        this.toContainer = toContainer;
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

        Label titleLabel = new Label("Adicionar Cliente");
        titleLabel.setForeColor(Color.WHITE);
        tabBar.add(titleLabel,  AFTER + 8, CENTER);

        try {
            Button saveButton = new Button(new Image("save.png").getScaledInstance(30, 30));
            saveButton.setBackColor(Variables.PRIMARY_COLOR);
            saveButton.appId = 0;
            tabBar.add(saveButton, RIGHT, CENTER);
        } catch (ImageException | IOException e) {
            throw new ButtonException(e);
        }

        add(tabBar);

        add(contentFieldCliente, 0, TOP + 80, FILL, FILL);
    }

    @Override
    public void onEvent(Event event) {
        if (event.type == ControlEvent.PRESSED && event.target instanceof Button) {

            Control control = (Control) event.target;

            if (control.appId == 0) {
                try {
                    MainWindow.getMainWindow().setFocus(null);
                    saveClient(contentFieldCliente);
                    return;
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }

            if (control.appId == 999) {
                MainWindow.getMainWindow().swap(toContainer);
            }
        }
    }

    private void saveClient(ContentFieldClient contentFieldCliente) throws SQLException {
        String name = contentFieldCliente.getNameEdit();
        String email = contentFieldCliente.getEmailEdit();
        String phone = contentFieldCliente.getPhoneEdit();

        if (name.isEmpty() || email.isEmpty() || phone.isEmpty()) {
            new MessageBox("Erro!", "Preencha todos os campos.").popup();
            return;
        }
        Client client = new Client(
                name,
                email,
                phone
        );
        clienteController.createClient(client);
        MainWindow.getMainWindow().swap(new ClientScreen());
    }
}
