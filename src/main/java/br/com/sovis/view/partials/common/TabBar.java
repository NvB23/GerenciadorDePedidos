package br.com.sovis.view.partials.common;

import br.com.sovis.exception.ButtonException;
import br.com.sovis.view.style.Variables;
import totalcross.io.IOException;
import totalcross.ui.*;
import totalcross.ui.event.ControlEvent;
import totalcross.ui.event.Event;
import totalcross.ui.gfx.Color;
import totalcross.ui.image.Image;
import totalcross.ui.image.ImageException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TabBar extends Container {
    private Container containerDeVolta = null;
    private final String titulo;

    private final int qntButton;
    private final List<String> listaDePaths;
    private final Map<Integer, Container> mapEvents;

    /* Construtor com apenas titulo e um Map com a relação
    do path da imagem do botão com o container para o qual ele vai */
    public TabBar(String titulo, Map<String, Container> pathContainers) {
        this.listaDePaths = new ArrayList<>();
        this.qntButton = pathContainers.size();
        this.titulo = titulo;
        this.mapEvents = new HashMap<>();

        Integer v = 0;

        for (Map.Entry<String, Container> parValor : pathContainers.entrySet()) {
            mapEvents.put(v, parValor.getValue());
            listaDePaths.add(parValor.getKey());
            v++;
        }
        setBackColor(Variables.PRIMARY_COLOR);
    }

    /* Construtor com botão de volta (necessário passar qual o Container destino da volta),
    um título e o Map com a relação de paths e telas */
    public TabBar(Container containerDeVolta, String titulo, Map<String, Container> pathContainers) {
        this.containerDeVolta = containerDeVolta;
        this.listaDePaths = new ArrayList<>();
        this.qntButton = pathContainers.size();
        this.titulo = titulo;
        this.mapEvents = new HashMap<>();

        Integer v = 0;

        for (Map.Entry<String, Container> parValor : pathContainers.entrySet()) {
            mapEvents.put(v, parValor.getValue());
            listaDePaths.add(parValor.getKey());
            v++;
        }
        setBackColor(Variables.PRIMARY_COLOR);
    }

    // Construtor com container de para o qual voltará e o título
    public TabBar(Container containerDeVolta, String titulo) {
        this.containerDeVolta = containerDeVolta;
        listaDePaths = null;
        this.qntButton = 0;
        this.titulo = titulo;
        this.mapEvents = null;
        setBackColor(Variables.PRIMARY_COLOR);
    }

    @Override
    public void initUI() {
        try {
            Button voltarBotao = new Button(new Image("back-arrow.png").getScaledInstance(20, 20));
            voltarBotao.setBackColor(Variables.PRIMARY_COLOR);
            voltarBotao.appId = 999;
            if (containerDeVolta != null) {
                add(voltarBotao, LEFT, TOP);
            }
        } catch (ImageException | IOException e) {
            throw new ButtonException(e);
        }

        Label tituloLabel = new Label(this.titulo);
        tituloLabel.setForeColor(Color.WHITE);
        add(tituloLabel, containerDeVolta != null ? AFTER + 4 : LEFT + 18, CENTER);

        if (this.listaDePaths != null && this.qntButton != 0) {
            try {
                Button baseBotao = new Button(new Image(listaDePaths.get(0)).getScaledInstance(30, 30));
                baseBotao.setBackColor(Variables.PRIMARY_COLOR);
                baseBotao.appId = 0;
                add(baseBotao, RIGHT, CENTER);

                if (this.qntButton != 1) {
                    for (int i = 1; i < qntButton; i++) {
                        Button outrosBotao = new Button(new Image(listaDePaths.get(i)).getScaledInstance(30, 30));
                        outrosBotao.setBackColor(Variables.PRIMARY_COLOR);
                        outrosBotao.appId = i;
                        add(outrosBotao, BEFORE, CENTER);
                    }
                }
            } catch (ImageException | IOException e) {
                throw new ButtonException(e);
            }
        }
    }

    @Override
    public void onEvent(Event event) {
        switch (event.type) {
            case ControlEvent.PRESSED:
                if (this.mapEvents != null) {
                    for (Map.Entry<Integer, Container> parValor : mapEvents.entrySet()) {
                        if (((Control) event.target).appId == parValor.getKey()) {
                            MainWindow.getMainWindow().swap(parValor.getValue());
                        }
                    }
                }

                if (((Control) event.target).appId == 999) MainWindow.getMainWindow().swap(this.containerDeVolta);
        }
    }
}
