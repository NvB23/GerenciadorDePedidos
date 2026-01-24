package br.com.sovis.view.partials.client;

import br.com.sovis.view.style.Variables;
import totalcross.ui.Label;
import totalcross.ui.ScrollContainer;

public class ClientTile extends ScrollContainer {
    private final Long idClient;
    private final String name;
    private final String email;
    private final String dateRegister;
    private final String phone;

    public ClientTile(Long idClient, String name, String email, String dateRegister, String phone) {
        super(false);

        this.idClient = idClient;
        this.name = name;
        this.email = email;
        this.dateRegister = dateRegister;
        this.phone = phone;
    }
    @Override
    public void initUI() {
        Label idClientLabel = new Label("ID CLIENTE - " + idClient);
        idClientLabel.setForeColor(Variables.PRIMARY_COLOR);
        add(idClientLabel, LEFT + 8, TOP + 8, PREFERRED, PREFERRED);

        Label dateLabel = new Label(dateRegister);
        add(dateLabel, RIGHT - 8, TOP + 8, PREFERRED, PREFERRED);

        Label nameLabel = new Label(name);
        add(nameLabel, LEFT + 8, AFTER + 16, PREFERRED, PREFERRED);

        Label emailLabel = new Label(email);
        add(emailLabel, LEFT + 8, AFTER + 16, PREFERRED, PREFERRED);

        Label statusLabel = new Label(phone);
        add(statusLabel, RIGHT -8, SAME, PREFERRED, PREFERRED);
    }
}
