package com.ecommerce.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ecommerce.dto.ProductDto;
import com.ecommerce.model.Product;
import com.ecommerce.repository.ProductRepository;
import com.ecommerce.third_party_services.CloudinaryService;

@Service
public class ProductService {

    @Autowired
    private CloudinaryService cloudinaryService;

    @Autowired
    private ProductRepository  productRepository;

    public Product createProduct(ProductDto productDto, MultipartFile image) {
        String imageUrl = cloudinaryService.uploadImage(image, "Product_image");
        Product product = convertProductDtoTOProduct(productDto, imageUrl);
        Product newProduct = productRepository.save(product);
        return newProduct;
    }

    public List<Product> getAllProducts(String search) {
        if(search != null && !search.isEmpty())
            return productRepository.searchProducts(search);
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(int id) {
        return productRepository.findById(id);
        // return productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
    }

    public Product updateProduct(Product product) {
        this.productRepository.findById(product.getId()).orElseThrow(() -> new RuntimeException("Product not found"));
        return productRepository.save(product);
    }

    public Product deleteProduct(int id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
        if(product.getImageUrl() != null) {
            cloudinaryService.deleteImage(product.getImageUrl());
        }
        productRepository.delete(product);
        return product;
    }

    //utils
    private Product convertProductDtoTOProduct(ProductDto productDto, String imageUrl) {
        Product product = new Product();
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setBrand(productDto.getBrand());
        product.setPrice(productDto.getPrice());
        product.setCategory(productDto.getCategory());
        product.setReleaseDate(productDto.getReleaseDate());
        product.setAvailable(productDto.isAvailable());
        product.setQuantity(productDto.getQuantity());
        product.setImageUrl(imageUrl);
        return product;
    }
}
