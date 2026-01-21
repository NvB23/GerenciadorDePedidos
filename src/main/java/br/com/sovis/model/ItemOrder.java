package br.com.sovis.model;

import java.time.LocalDateTime;

public class ItemOrder {
    private Long id;
    private Long idPedido;
    private Long idProduto;
    private double valorItem;
    private final String dataDoPedido = LocalDateTime.now().toLocalDate().toString();

    public ItemOrder(Long id, Long idPedido, Long idProduto, double valorItem) {
        this.id = id;
        this.idPedido = idPedido;
        this.idProduto = idProduto;
        this.valorItem = valorItem;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(Long idPedido) {
        this.idPedido = idPedido;
    }

    public Long getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(Long idProduto) {
        this.idProduto = idProduto;
    }

    public double getValorItem() {
        return valorItem;
    }

    public void setValorItem(double valorItem) {
        this.valorItem = valorItem;
    }

    public String getDataDoPedido() {
        return dataDoPedido;
    }
}
