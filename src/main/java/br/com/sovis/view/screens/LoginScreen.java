package br.com.sovis.view.screens;

import br.com.sovis.view.screens.order.HomeScreen;
import br.com.sovis.view.style.Variables;
import totalcross.ui.*;
import totalcross.ui.Button;
import totalcross.ui.Container;
import totalcross.ui.Label;
import totalcross.ui.event.ControlEvent;
import totalcross.ui.event.Event;
import totalcross.ui.event.EventHandler;
import totalcross.ui.event.PressListener;
import totalcross.ui.font.Font;
import totalcross.ui.gfx.Color;


public class LoginScreen extends Container {

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
        Edit emailEdit = new Edit();
        emailEdit.setForeColor(Variables.PRIMARY_COLOR);
        add(emailEdit, CENTER, AFTER + 5, PARENTSIZE + 80, PREFERRED - 20);

        Label senhaLabel = new Label("Senha");
        senhaLabel.setForeColor(Color.WHITE);
        add(senhaLabel, PARENTSIZE + 20, AFTER + 50);
        Edit senhaEdit = new Edit();
        senhaEdit.setForeColor(Variables.PRIMARY_COLOR);
        add(senhaEdit, CENTER, AFTER + 5, PARENTSIZE + 80, PREFERRED - 20);
        senhaEdit.setMode(Edit.PASSWORD);

        Button entrarBotao = new Button("Entrar");
        entrarBotao.setBackColor(Variables.PRIMARY_COLOR);
        entrarBotao.setForeColor(Color.WHITE);
        add(entrarBotao, CENTER, AFTER + 50, PARENTSIZE + 80, PREFERRED);
        entrarBotao.addPressListener(new PressListener() {
            @Override
            public void controlPressed(ControlEvent controlEvent) {
                MainWindow.getMainWindow().swap(new HomeScreen());
            }
        });
    }

    @Override
    public <H extends EventHandler> void onEvent(Event<H> event) {

    }
}
