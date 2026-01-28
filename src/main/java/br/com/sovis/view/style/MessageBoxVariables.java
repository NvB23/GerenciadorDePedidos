package br.com.sovis.view.style;

import totalcross.ui.dialog.MessageBox;

public class MessageBoxVariables {
    public static void notSelectedItem () {
        new MessageBox("Seleciona um item!", "Selecione um item para realizar a operação.").popup();
    }

    public static void orderLocked () {
        new MessageBox("Pedido Fechado", "Não pode editar um pedido fechado.").popup();
    }

    public static void voidFields () {
        new MessageBox("Credenciais inválidas!", "Campos vazios.").popup();
    }

    public static void invalidCredentials () {
        new MessageBox("Credenciais inválidas!", "Erro ao acessar a conta! Verifique suas credenciais.").popup();
    }
}
