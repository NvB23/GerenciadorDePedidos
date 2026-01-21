package br.com.sovis.view.partials.client;

import br.com.sovis.view.style.Variables;
import totalcross.ui.Label;
import totalcross.ui.ScrollContainer;

public class ClientTile extends ScrollContainer {
    private final Long idCliente;
    private final String nome;
    private final String email;
    private final String dataCadastro;
    private final String telefone;

    public ClientTile(Long idCliente, String nome,String email, String dataCadastro, String telefone) {
        super(false);

        this.idCliente = idCliente;
        this.nome = nome;
        this.email = email;
        this.dataCadastro = dataCadastro;
        this.telefone = telefone;
    }
    @Override
    public void initUI() {
        Label idClienteLabel = new Label("ID CLIENTE - " + idCliente);
        idClienteLabel.setForeColor(Variables.PRIMARY_COLOR);
        add(idClienteLabel, LEFT + 8, TOP + 8, PREFERRED, PREFERRED);

        Label dataLabel = new Label(dataCadastro);
        add(dataLabel, RIGHT - 8, TOP + 8, PREFERRED, PREFERRED);

        Label nomeLabel = new Label(nome);
        add(nomeLabel, LEFT + 8, AFTER + 16, PREFERRED, PREFERRED);

        Label emailLabel = new Label(email);
        add(emailLabel, LEFT + 8, AFTER + 16, PREFERRED, PREFERRED);

        Label statusLabel = new Label(telefone);
        add(statusLabel, RIGHT -8, SAME, PREFERRED, PREFERRED);
    }
}
