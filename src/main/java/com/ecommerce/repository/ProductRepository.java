package com.ecommerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ecommerce.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    @Query(
        "SELECT p FROM Product p WHERE " +
        "LOWER(p.name) ILIKE LOWER(CONCAT('%', :query, '%')) OR " +
        "LOWER(p.description) ILIKE LOWER(CONCAT('%', :query, '%')) OR " +
        "LOWER(p.category) ILIKE LOWER(CONCAT('%', :query, '%')) OR " +
        "LOWER(p.brand) ILIKE LOWER(CONCAT('%', :query, '%'))"
        )
    List<Product> searchProducts(String query);

}
