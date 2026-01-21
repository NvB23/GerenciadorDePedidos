package br.com.sovis.view.partials.common;

import br.com.sovis.exception.ButtonException;
import br.com.sovis.view.style.Variables;
import totalcross.io.IOException;
import totalcross.ui.Button;
import totalcross.ui.Container;
import totalcross.ui.Control;
import totalcross.ui.MainWindow;
import totalcross.ui.event.ControlEvent;
import totalcross.ui.event.Event;
import totalcross.ui.gfx.Color;
import totalcross.ui.image.Image;
import totalcross.ui.image.ImageException;

public class MainButton extends Container {
    private final String pathImg;
    private final String texto;
    private final Container containerAlvo;

    public MainButton(String path, String texto, Container containerAlvo) {
        this.pathImg = path;
        this.texto = texto;
        this.containerAlvo = containerAlvo;
    }

    @Override
    public void initUI() {
        try {
            Button adicionarBotao = new Button(
                    this.texto,
                    new Image(this.pathImg).getScaledInstance(30, 30),
                    RIGHT,
                    28);
            adicionarBotao.setBackColor(Variables.PRIMARY_COLOR);
            adicionarBotao.setForeColor(Color.WHITE);
            adicionarBotao.appId = 1;
            add(adicionarBotao, 0, 0, FILL, FILL);
        } catch (ImageException | IOException e) {
            throw new ButtonException(e);
        }
    }

    @Override
    public void onEvent(Event event) {
        switch (event.type) {
            case ControlEvent.PRESSED:
                if (((Control) event.target).appId == 1) {
                    MainWindow.getMainWindow().swap(this.containerAlvo);
                }
        }
    }
}
