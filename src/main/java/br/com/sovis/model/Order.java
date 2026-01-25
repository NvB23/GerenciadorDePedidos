package br.com.sovis.model;

import br.com.sovis.model.enums.OrderStatus;

import java.time.LocalDateTime;

public class Order {
    private Long id;
    private Client client;
    private Double totalValue;
    private OrderStatus orderStatus = OrderStatus.PENDENTE;
    private String orderDate = LocalDateTime.now().toLocalDate().toString();

    public Order(Client client) {
        this.client = client;
    }

    public Order(Long id, Client client, Double totalValue, String orderDate, OrderStatus orderStatus) {
        this.id = id;
        this.client = client;
        this.totalValue = totalValue;
        this.orderStatus = orderStatus;
        this.orderDate = orderDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Double getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(Double totalValue) {
        this.totalValue = totalValue;
    }

    public OrderStatus getStatusPedido() {
        return orderStatus;
    }

    public void setStatusPedido(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getOrderDate() {
        return orderDate;
    }
}
