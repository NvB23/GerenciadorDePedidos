package br.com.sovis.view.partials.order;

import br.com.sovis.controller.ItemOrderController;
import br.com.sovis.model.ItemOrder;
import br.com.sovis.model.enums.OrderStatus;
import br.com.sovis.view.style.Variables;
import totalcross.ui.Label;
import totalcross.ui.ScrollContainer;

import java.sql.SQLException;
import java.util.ArrayList;

public class OrderTile extends ScrollContainer {
    private final Long idOrder;
    private final double totalValue;
    private final String orderDate;
    private final String nameClient;
    private final OrderStatus orderStatus;
    private final ItemOrderController itemOrderController = new ItemOrderController();

    public OrderTile(Long idOrder, double totalValue, String orderDate, String cliente, OrderStatus orderStatus) {
        super(false);
        this.idOrder = idOrder;
        this.totalValue = totalValue;
        this.orderDate = orderDate;
        this.nameClient = cliente;
        this.orderStatus = orderStatus;
    }
    @Override
    public void initUI() {
        Label idOrderLabel = new Label("ID PEDIDO - " + idOrder);
        idOrderLabel.setForeColor(Variables.PRIMARY_COLOR);
        add(idOrderLabel, LEFT + 8, TOP + 8, PREFERRED, PREFERRED);

        Label dateLabel = new Label(orderDate);
        add(dateLabel, RIGHT - 8, TOP + 8, PREFERRED, PREFERRED);

        Label clientLabel = new Label("Cliente: " + nameClient);
        add(clientLabel, LEFT + 8, AFTER + 16, PREFERRED, PREFERRED);

        Label totalValueLabel = new Label("Total: R$" + String.format("%.2f", totalValue));
        add(totalValueLabel, LEFT + 8, AFTER + 16, PREFERRED, PREFERRED);

        Label statusLabel = new Label("Status: " + orderStatus);
        statusLabel.setForeColor(orderStatus.equals(OrderStatus.FECHADO) ? Variables.RED_STATUS : Variables.YELLOW_STATUS);
        add(statusLabel, RIGHT -8, SAME, PREFERRED, PREFERRED);

        Label itemsLabel = new Label("Itens");
        itemsLabel.setForeColor(Variables.PRIMARY_COLOR);
        add(itemsLabel, LEFT + 8, AFTER + 20, PREFERRED, PREFERRED);

        try {
            ArrayList<ItemOrder> items =  itemOrderController.getItemOrdersById(idOrder);
            for (ItemOrder item : items) {
                Label quantLabel = new Label(item.getQuantity() + " un");
                add(quantLabel, LEFT + 8, AFTER + 10, PREFERRED, PREFERRED);
                Label itemNameLabel = new Label(item.getProduct().getName());
                add(itemNameLabel, AFTER + 20, SAME, PREFERRED, PREFERRED);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
