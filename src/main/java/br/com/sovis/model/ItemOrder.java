package br.com.sovis.model;

public class ItemOrder {
    private Long id;
    private Order order;
    private Product product;
    private final Integer quantity;
    private final double itemValue;

    public ItemOrder(Long id, Order order, Product product, Integer quantity, double itemValue) {
        this.id = id;
        this.order = order;
        this.product = product;
        this.quantity = quantity;
        this.itemValue = itemValue;
    }

    public ItemOrder(Order order, Product product, Integer quantity, double itemValue) {
        this.order = order;
        this.product = product;
        this.quantity = quantity;
        this.itemValue = itemValue;
    }

    public ItemOrder(Product product, Integer quantity, double itemValue) {
        this.product = product;
        this.quantity = quantity;
        this.itemValue = itemValue;
    }

    public Long getId() {
        return id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getItemValue() {
        return itemValue;
    }
}
