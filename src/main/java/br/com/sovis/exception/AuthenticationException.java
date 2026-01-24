package br.com.sovis.exception;

import totalcross.ui.dialog.MessageBox;

public class AuthenticationException extends RuntimeException {
    public AuthenticationException(Exception e) {
        super(e);
        new MessageBox("Credenciais inválidas!",
                "Erro ao acessar a conta! Verifique suas credenciais.").popup();
    }
}
