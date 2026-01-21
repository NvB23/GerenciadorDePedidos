package br.com.sovis.view.partials.order;


import br.com.sovis.exception.AttributesNullException;
import br.com.sovis.exception.ButtonException;
import br.com.sovis.model.Client;
import br.com.sovis.model.Product;
import br.com.sovis.view.partials.product.ProductTile;
import br.com.sovis.view.style.Variables;
import totalcross.io.IOException;
import totalcross.ui.*;
import totalcross.ui.dialog.ControlBox;
import totalcross.ui.event.ControlEvent;
import totalcross.ui.event.PressListener;
import totalcross.ui.image.Image;
import totalcross.ui.image.ImageException;

import java.util.ArrayList;

public class ContentFieldOrder extends Container {
    private ArrayList<Client> clientes = new ArrayList<>();
    private ArrayList<Product> produtos = new ArrayList<>();

    public ContentFieldOrder(ArrayList<Client> cliente, ArrayList<Product> produtos) {
        if (cliente == null || produtos == null) {
            throw new AttributesNullException("Atributos passados não podem ser nulos");
        }

        this.clientes = cliente;
        this.produtos = produtos;
    }

    public ContentFieldOrder() {}

    @Override
    public void initUI() {
        super.initUI();

        Label nomeLabel = new Label("Selecione o Cliente");
        nomeLabel.setForeColor(Variables.SECOND_COLOR);
        add(nomeLabel, PARENTSIZE + 50, TOP, PARENTSIZE + 90, PREFERRED);

        Client cliente1 = new Client(1L, "João", "", "");
        Client cliente2 = new Client(2L, "Maria", "", "");
        Client cliente3 = new Client(3L, "Naum", "", "");

        clientes.add(cliente1);
        clientes.add(cliente2);
        clientes.add(cliente3);

        String[] nomesClientes = new String[clientes.size()];

        for (int i = 0; i < clientes.size(); i++) {
            nomesClientes[i] = clientes.get(i).getNome();
        }
        ComboBox clientesComboBox = new ComboBox(nomesClientes);
        clientesComboBox.setForeColor(Variables.PRIMARY_COLOR);
        add(clientesComboBox, CENTER, AFTER + 5, PARENTSIZE + 90, PREFERRED);

        Label emailLabel = new Label("Items do Pedido");
        emailLabel.setForeColor(Variables.SECOND_COLOR);
        add(emailLabel, PARENTSIZE + 50, AFTER + 40 , PARENTSIZE + 90, PREFERRED);
        ListContainer listContainer = new ListContainer();
        try {
            Button botaoAdd = new Button(new Image("add.png").getScaledInstance(30,30));
            botaoAdd.setBackColor(Variables.PRIMARY_COLOR);
            botaoAdd.addPressListener(new PressListener() {
                @Override
                public void controlPressed(ControlEvent controlEvent) {
                    Product produto1 = new Product(1L, "Playstation", "", 0.1);
                    Product produto2 = new Product(2L, "Café", "", 0.2);
                    Product produto3 = new Product(3L, "Diamante", "", 0.3);

                    produtos.add(produto1);
                    produtos.add(produto2);
                    produtos.add(produto3);

                    String[] nomesProdutos = new String[produtos.size()];

                    for (int i = 0; i < produtos.size(); i++) {
                        nomesProdutos[i] = produtos.get(i).getId() + " - " + produtos.get(i).getNome();
                    }
                    ComboBox produtoComboBox = new ComboBox(nomesProdutos);
                    produtoComboBox.setForeColor(Variables.PRIMARY_COLOR);

                   /* String idProdutoSelecionado = produtoComboBox.getValue().toString().split(" - ")[0];*/

                    /* Product produtoSelecionado = (Product) produtos.stream()
                    .filter(product -> product.getId() == Long.parseLong(idProdutoSelecionado)); */

                    Container containerItem = new Container();
                    containerItem.setRect(CENTER, CENTER, PREFERRED, PREFERRED);
                    containerItem.add(produtoComboBox, LEFT + 8, CENTER, PARENTSIZE + 80, PREFERRED);
                    try {
                        Button lixeiraBotao = new Button(new Image("trash.png").getScaledInstance(30, 30));
                        lixeiraBotao.setBackColor(Variables.PRIMARY_COLOR);
                        containerItem.add(
                                lixeiraBotao,
                                RIGHT - 8, CENTER, PREFERRED - 12, PREFERRED - 12);
                        lixeiraBotao.addPressListener(new PressListener() {
                            @Override
                            public void controlPressed(ControlEvent controlEvent) {

                            }
                        });
                    } catch (ImageException | IOException e) {
                        throw new ButtonException(e);
                    }

                    listContainer.addContainer(containerItem);
                }
            });
            add(botaoAdd, RIGHT - 16, AFTER - 35, PREFERRED - 10, PREFERRED - 10);
        } catch (ImageException | IOException e) {
            throw new ButtonException(e);
        }
        add(listContainer, LEFT, AFTER + 20, FILL, FILL);
    }
}
