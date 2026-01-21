package br.com.sovis.exception;

import totalcross.ui.dialog.MessageBox;

public class ButtonException extends RuntimeException {
    public ButtonException(Exception e) {
        super(e);
        new MessageBox("Erro Botão", e.getMessage()).popup();
    }
}
