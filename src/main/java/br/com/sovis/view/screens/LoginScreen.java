package br.com.sovis.view.screens;

import br.com.sovis.controller.Authentication;
import br.com.sovis.exception.AuthenticationException;
import br.com.sovis.view.screens.order.HomeScreen;
import br.com.sovis.view.style.MessageBoxVariables;
import br.com.sovis.view.style.Variables;
import totalcross.ui.*;
import totalcross.ui.Button;
import totalcross.ui.Container;
import totalcross.ui.Label;
import totalcross.ui.event.ControlEvent;
import totalcross.ui.event.Event;
import totalcross.ui.font.Font;
import totalcross.ui.gfx.Color;

import java.sql.SQLException;


public class LoginScreen extends Container {
    Button enterButton;
    Edit emailEdit = new Edit();
    Edit passwordEdit = new Edit();
    Authentication authentication = new Authentication();

    public LoginScreen() {
        setBackColor(Variables.SECOND_COLOR);
        setRect(0, 0, FILL, FILL);
    }

    @Override
    public void initUI() {
        Label tituloLabel = new Label("Gerenciador de Pedidos");
        tituloLabel.setForeColor(Color.WHITE);
        tituloLabel.setFont(Font.getFont(true, 20));
        add(tituloLabel, CENTER, TOP + 60);

        Label emailLabel = new Label("Email");
        emailLabel.setForeColor(Color.WHITE);
        add(emailLabel, PARENTSIZE + 20, AFTER + 100);
        emailEdit.setForeColor(Variables.PRIMARY_COLOR);
        add(emailEdit, CENTER, AFTER + 5, PARENTSIZE + 80, PREFERRED - 20);

        Label passwordLabel = new Label("Senha");
        passwordLabel.setForeColor(Color.WHITE);
        add(passwordLabel, PARENTSIZE + 20, AFTER + 50);
        passwordEdit.setForeColor(Variables.PRIMARY_COLOR);
        add(passwordEdit, CENTER, AFTER + 5, PARENTSIZE + 80, PREFERRED - 20);
        passwordEdit.setMode(Edit.PASSWORD);

        enterButton = new Button("Entrar");
        enterButton.setBackColor(Variables.PRIMARY_COLOR);
        enterButton.setForeColor(Color.WHITE);
        add(enterButton, CENTER, AFTER + 50, PARENTSIZE + 80, PREFERRED);
    }

    @Override
    public void onEvent(Event event) {
        if (event.type == ControlEvent.PRESSED) {
            if (event.target == enterButton) {
                try {
                    MainWindow.getMainWindow().swap(new HomeScreen());
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                try {
                    boolean success = authentication.login(emailEdit.getValue(), passwordEdit.getValue());
                    if (emailEdit.getText().isEmpty() || passwordEdit.getText().isEmpty()) {
                        MessageBoxVariables.voidFields();
                        return;
                    }

                    if (!success) {
                        MessageBoxVariables.invalidCredentials();
                    } else {
                        MainWindow.getMainWindow().swap(new HomeScreen());
                    }
                } catch (SQLException e) {
                    throw new AuthenticationException(e);
                }
            }
        }
    }
}
