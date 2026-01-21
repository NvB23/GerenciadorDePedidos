package br.com.sovis.view.partials.client;


import br.com.sovis.exception.AttributesNullException;
import br.com.sovis.view.style.Variables;
import totalcross.ui.Container;
import totalcross.ui.Edit;
import totalcross.ui.Label;

public class ContentFieldClient extends Container {
    private String nome;
    private String email;
    private String telefone;

    private String nomeAlterado;
    private String emailAlterado;
    private String telefoneAlterado;

    public ContentFieldClient(String nome, String email, String telefone) {
        if (nome == null || email == null || telefone == null) {
            throw new AttributesNullException("Atributos passados não podem ser nulos");
        }

        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
    }

    public ContentFieldClient() {
        this.nome = "";
        this.email = "";
        this.telefone = "";
    }

    @Override
    public void initUI() {
        Label nomeLabel = new Label("Nome do Cliente");
        nomeLabel.setForeColor(Variables.SECOND_COLOR);
        add(nomeLabel, PARENTSIZE + 50, TOP, PARENTSIZE + 90, PREFERRED);
        Edit nomeEdit = new Edit();
        nomeEdit.setForeColor(Variables.PRIMARY_COLOR);
        add(nomeEdit, CENTER, AFTER + 5, PARENTSIZE + 90, PREFERRED - 20);

        nomeAlterado = nomeEdit.getText();

        Label emailLabel = new Label("Email do Cliente");
        emailLabel.setForeColor(Variables.SECOND_COLOR);
        add(emailLabel, PARENTSIZE + 50, AFTER + 30 , PARENTSIZE + 90, PREFERRED);
        Edit emailEdit = new Edit();
        emailEdit.setForeColor(Variables.PRIMARY_COLOR);
        add(emailEdit, CENTER, AFTER + 5, PARENTSIZE + 90, PREFERRED - 20);

        emailAlterado = emailEdit.getText();

        Label telefoneLabel = new Label("Telefone do Cliente");
        telefoneLabel.setForeColor(Variables.SECOND_COLOR);
        add(telefoneLabel, PARENTSIZE + 50, AFTER + 30 , PARENTSIZE + 90, PREFERRED);
        Edit telefoneEdit = new Edit("99 99999-9999");
        telefoneEdit.setValidChars("0123456789");
        telefoneEdit.setForeColor(Variables.PRIMARY_COLOR);
        telefoneEdit.setMode(Edit.NORMAL, true);
        add(telefoneEdit, CENTER, AFTER + 5, PARENTSIZE + 90, PREFERRED - 20);

        telefoneAlterado = telefoneEdit.getText();

        nomeEdit.setText(nome);
        emailEdit.setText(email);
        telefoneEdit.setText(telefone);
    }

    public String getNomeAlterado() {
        return nomeAlterado;
    }

    public String getEmailAlterado() {
        return emailAlterado;
    }

    public String getTelefoneAlterado() {
        return telefoneAlterado;
    }
}
