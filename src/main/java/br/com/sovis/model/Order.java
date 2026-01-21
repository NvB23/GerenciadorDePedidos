package br.com.sovis.model;

import br.com.sovis.model.enums.StatusPedido;

import java.time.LocalDateTime;

public class Order {
    private final Long id;
    private final Long idCliente;
    private Double valorTotal;
    private StatusPedido statusPedido;

    public Order(Long id, Long idCliente, Double valorTotal, StatusPedido statusPedido) {
        this.id = id;
        this.idCliente = idCliente;
        this.valorTotal = valorTotal;
        this.statusPedido = statusPedido;
    }

    public Long getId() {
        return id;
    }

    public Long getIdCliente() {
        return idCliente;
    }

    public Double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public StatusPedido getStatusPedido() {
        return statusPedido;
    }

    public void setStatusPedido(StatusPedido statusPedido) {
        this.statusPedido = statusPedido;
    }
}
