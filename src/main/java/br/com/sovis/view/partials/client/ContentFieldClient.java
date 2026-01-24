package br.com.sovis.view.partials.client;


import br.com.sovis.view.style.Variables;
import totalcross.ui.Container;
import totalcross.ui.Edit;
import totalcross.ui.Label;

public class ContentFieldClient extends Container {
    private Edit nameEdit;
    private Edit emailEdit;
    private Edit phoneEdit;

    public ContentFieldClient() {}

    @Override
    public void initUI() {
        Label nameLabel = new Label("Nome do Cliente");
        nameLabel.setForeColor(Variables.SECOND_COLOR);
        add(nameLabel, PARENTSIZE + 50, TOP, PARENTSIZE + 90, PREFERRED);
        nameEdit = new Edit();
        nameEdit.setForeColor(Variables.PRIMARY_COLOR);
        add(nameEdit, CENTER, AFTER + 5, PARENTSIZE + 90, PREFERRED - 20);

        Label emailLabel = new Label("Email do Cliente");
        emailLabel.setForeColor(Variables.SECOND_COLOR);
        add(emailLabel, PARENTSIZE + 50, AFTER + 30 , PARENTSIZE + 90, PREFERRED);
        emailEdit = new Edit();
        emailEdit.setForeColor(Variables.PRIMARY_COLOR);
        add(emailEdit, CENTER, AFTER + 5, PARENTSIZE + 90, PREFERRED - 20);

        Label phoneLabel = new Label("Telefone do Cliente");
        phoneLabel.setForeColor(Variables.SECOND_COLOR);
        add(phoneLabel, PARENTSIZE + 50, AFTER + 30 , PARENTSIZE + 90, PREFERRED);
        phoneEdit = new Edit("99 99999-9999");
        phoneEdit.setValidChars("0123456789");
        phoneEdit.setForeColor(Variables.PRIMARY_COLOR);
        phoneEdit.setMode(Edit.NORMAL, true);
        add(phoneEdit, CENTER, AFTER + 5, PARENTSIZE + 90, PREFERRED - 20);
    }

    public String getNameEdit() {
        return nameEdit.getText();
    }

    public String getEmailEdit() {
        return emailEdit.getText();
    }

    public String getPhoneEdit() {
        return phoneEdit.getText();
    }
}
