package br.com.sovis.controller;

import br.com.sovis.dao.ProductDAO;
import br.com.sovis.model.Product;

import java.sql.SQLException;
import java.util.ArrayList;

public class ProductController {
    private final ProductDAO productDAO = new ProductDAO();

    public void createProduct(Product product) throws SQLException {
        productDAO.createProduct(
                product.getName(),
                product.getDescription(),
                String.valueOf(product.getPrice())
        );
    }

    public void updateProduct(Long id, Product productModify) throws SQLException {
        productDAO.updateProduct(
                String.valueOf(id),
                productModify.getName(),
                productModify.getDescription(),
                String.valueOf(productModify.getPrice())
        );
    }

    public void deleteProduct(Long id) throws SQLException {
        productDAO.deleteProduct(String.valueOf(id));
    }

    public ArrayList<Product> getAllProducts() throws SQLException {
        return productDAO.getProducts();
    }

    public ArrayList<Product> getProductsOfUser(Long idUser) throws SQLException {
        return productDAO.getProductsOfUser(String.valueOf(idUser));
    }

    public Product getProductById(Long id) throws SQLException {
        return productDAO.getProductById(String.valueOf(id));
    }
}
