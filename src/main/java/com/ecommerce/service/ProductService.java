package com.ecommerce.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.model.Product;
import com.ecommerce.repository.ProductRepository;

@Service
public class ProductService {

    @Autowired
    private ProductRepository  productRepository;

    public Product createProduct(Product product) {
        Product newProduct = productRepository.save(product);
        return newProduct;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(int id) {
        return productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
    }

    public Product updateProduct(Product product) {
        this.productRepository.findById(product.getId()).orElseThrow(() -> new RuntimeException("Product not found"));
        return productRepository.save(product);
    }

    public Product deleteProduct(int id) {
        Product product = this.getProductById(id);
        productRepository.delete(product);
        return product;
    }

}
