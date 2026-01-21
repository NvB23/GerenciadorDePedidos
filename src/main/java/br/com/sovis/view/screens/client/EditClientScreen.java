package br.com.sovis.view.screens.client;

import br.com.sovis.view.partials.client.ContentFieldClient;
import br.com.sovis.view.partials.common.TabBar;
import br.com.sovis.view.screens.order.HomeScreen;
import totalcross.ui.Container;

import java.util.HashMap;

public class EditClientScreen extends Container {
    private final Container containerDeVolta;
    private final Long idParaEditar;

    private String nomeAlterado;
    private String emailAlterado;
    private String telefoneAlterado;

    public EditClientScreen(Container containerDeVolta, Long idParaEditar) {
        this.containerDeVolta = containerDeVolta;
        this.idParaEditar = idParaEditar;
        setRect(0, 0, FILL, FILL);
    }

    @Override
    public void initUI() {
        HashMap<String, Container> saveBotao = new HashMap<>();
        saveBotao.put("save.png", editarCliente());
        add(new TabBar(new HomeScreen(), "Editar Cliente", saveBotao), 0, 0, FILL, PARENTSIZE + 8);

        // Recupera o cliente pelo id informado e depois passa para os campos

        ContentFieldClient contentFieldClient = new ContentFieldClient("Nau", "naum@emai.c", "3675647678");

        nomeAlterado = contentFieldClient.getNomeAlterado();
        emailAlterado = contentFieldClient.getEmailAlterado();
        telefoneAlterado = contentFieldClient.getTelefoneAlterado();

        add(contentFieldClient, 0, TOP + 80, FILL, FILL);
    }

    private Container editarCliente() {
        // Recupera o cliente pelo id e altera a tabela através do controller
        System.out.println(idParaEditar);

        // Dados de modificação
        System.out.println(nomeAlterado);
        System.out.println(emailAlterado);
        System.out.println(telefoneAlterado);

        // validação e operação
        return this.containerDeVolta;
    }
}
