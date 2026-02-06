package br.com.sovis.view.style;

import totalcross.ui.dialog.MessageBox;

public class MessageBoxVariables {
    public static void notSelectedItem () {
        new MessageBox("Seleciona um item!", "Selecione um item para realizar a operação.").popup();
    }

    public static void orderLocked () {
        new MessageBox("Pedido Fechado!", "Não pode editar um pedido fechado.").popup();
    }

    public static void voidFields () {
        new MessageBox("Credenciais inválidas!", "Campos vazios.").popup();
    }

    public static void invalidCredentials () {
        new MessageBox("Credenciais inválidas!", "Erro ao acessar a conta! Verifique suas credenciais.").popup();
    }

    public static void emptyListItemsOrder () {
        new MessageBox("Sem Itens!", "Operação não ser realizada com a lista de itens vazia.").popup();
    }

    public static void emptyClient () {
        new MessageBox("Sem Cliente!", "Sem cliente selecionado.").popup();
    }

    public static void itemWithQuantityBellowZero () {
        new MessageBox("Quantidade Abaixo de 0 ou Vazio!", "Apenas quantidades acima de zero e preenchidas são aceitas.").popup();
    }

    public static void itemWithProductEmpty () {
        new MessageBox("Item Sem Produto!", "Item sem produto selecionado.").popup();
    }

    public static void itemWithQuantityOrProductEmpty () {
        new MessageBox("Item Sem Quantidade ou Produto!", "Item sem quantidade válida ou produto selecionado.").popup();
    }

    public static void fieldsEmpty () {
        new MessageBox("Erro!", "Preencha todos os campos.").popup();
    }

    public static void invalidEmail() {
        new MessageBox("Erro!", "Email inválido.").popup();
    }

    public static void invalidPhone() {
        new MessageBox("Erro!", "Telefone inválido.").popup();
    }

    public static void alreadyExistsAOrderWithThisProduct() {
        new MessageBox("Erro ao Deletar!", "Existe pedido(s) com esse produto.").popup();
    }

    public static void alreadyExistsAOrderWithThisClient() {
        new MessageBox("Erro ao Deletar!", "Existe pedido(s) com esse cliente.").popup();
    }

    public static void productNotCanBeFree() {
        new MessageBox("Preço Inválido!", "O produto não pode ser gratuito.").popup();
    }

    public static void invalidPrice() {
        new MessageBox("Preço Inválido!", "Insira um preço válido.").popup();
    }
}
