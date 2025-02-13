package com.ecommerce.dto;

import java.math.BigDecimal;
import java.sql.Date;

import lombok.Data;

@Data
public class ProductDto {

    private int id;
    private String name;
    private String imageUrl;
    private String description;
    private String brand;
    private BigDecimal price;
    private String category;
    private Date releaseDate;
    private boolean isAvailable;
    private int quantity;

}
