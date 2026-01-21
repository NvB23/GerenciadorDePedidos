package br.com.sovis.view.partials.order;

import br.com.sovis.model.enums.StatusPedido;
import br.com.sovis.view.style.Variables;
import totalcross.ui.Label;
import totalcross.ui.ScrollContainer;

public class OrderTile extends ScrollContainer {
    private final Long idPedido;
    private final double valorTotal;
    private final String dataPedido;
    private final String nomeCliente;
    private final StatusPedido statusPedido;

    public OrderTile(Long idPedido, double valorTotal, String dataPedido, String cliente, StatusPedido statusPedido) {
        super(false);
        this.idPedido = idPedido;
        this.valorTotal = valorTotal;
        this.dataPedido = dataPedido;
        this.nomeCliente = cliente;
        this.statusPedido = statusPedido;
    }
    @Override
    public void initUI() {
        Label idPedidoLabel = new Label("ID PEDIDO - " + idPedido);
        idPedidoLabel.setForeColor(Variables.PRIMARY_COLOR);
        add(idPedidoLabel, LEFT + 8, TOP + 8, PREFERRED, PREFERRED);

        Label dataLabel = new Label(dataPedido);
        add(dataLabel, RIGHT - 8, TOP + 8, PREFERRED, PREFERRED);

        Label clienteLabel = new Label("Cliente: " + nomeCliente);
        add(clienteLabel, LEFT + 8, AFTER + 16, PREFERRED, PREFERRED);

        Label valorTotalLabel = new Label("Valor: R$" + valorTotal);
        add(valorTotalLabel, LEFT + 8, AFTER + 16, PREFERRED, PREFERRED);

        Label statusLabel = new Label("Status: " + statusPedido);
        statusLabel.setForeColor(statusPedido.equals(StatusPedido.FECHADO) ? Variables.RED_STATUS : Variables.YELLOW_STATUS);
        add(statusLabel, RIGHT -8, SAME, PREFERRED, PREFERRED);
    }
}
