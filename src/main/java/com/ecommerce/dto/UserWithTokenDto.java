package com.ecommerce.dto;

import lombok.Data;

@Data
public class UserWithTokenDto {

    private int id;
    private String username;
    private String email;
    private String role;
    private String token;

}
