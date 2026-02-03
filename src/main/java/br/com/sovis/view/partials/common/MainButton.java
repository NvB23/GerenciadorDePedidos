package br.com.sovis.view.partials.common;

import br.com.sovis.exception.ButtonException;
import br.com.sovis.view.style.Variables;
import totalcross.io.IOException;
import totalcross.ui.Button;
import totalcross.ui.Container;
import totalcross.ui.MainWindow;
import totalcross.ui.event.ControlEvent;
import totalcross.ui.event.Event;
import totalcross.ui.gfx.Color;
import totalcross.ui.image.Image;
import totalcross.ui.image.ImageException;

public class MainButton extends Container {
    private final String pathImg;
    private final String text;
    private final Container toContainer;
    private final boolean isEnabled;

    private Button addButton;

    public MainButton(String path, String text, Container toContainer) {
        this.pathImg = path;
        this.text = text;
        this.toContainer = toContainer;
        this.isEnabled = true;
    }

    public MainButton(String path, String text, Container toContainer, boolean isEnabled) {
        this.pathImg = path;
        this.text = text;
        this.toContainer = toContainer;
        this.isEnabled = isEnabled;
    }

    @Override
    public void initUI() {
        try {
            addButton = new Button(
                    this.text,
                    new Image(this.pathImg).getScaledInstance(30, 30),
                    RIGHT,
                    28);
            addButton.setBackColor(Variables.PRIMARY_COLOR);
            addButton.setForeColor(Color.WHITE);
            addButton.appId = -123;
            addButton.setEnabled(isEnabled);
            add(addButton, 0, 0, FILL, FILL);
        } catch (ImageException | IOException e) {
            throw new ButtonException(e);
        }
    }

    @Override
    public void onEvent(Event event) {
        if (event.type == ControlEvent.PRESSED && event.target instanceof Button) {
            if (event.target == addButton) {
                MainWindow.getMainWindow().swap(this.toContainer);
            }
        }
    }
}
