package com.ecommerce.third_party_services;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import io.github.cdimascio.dotenv.Dotenv;

@Configuration
public class CloudinaryConfig {

    Dotenv dotenv = Dotenv.load();

    private String cloudName = dotenv.get("CLOUDINARY_NAME");

    private String apiKey = dotenv.get("CLOUDINARY_API_KEY");

    private String apiSecret = dotenv.get("CLOUDINARY_API_SECRET");

    @Bean
    public Cloudinary cloudinary() {
        return new Cloudinary(ObjectUtils.asMap(
                "cloud_name", cloudName,
                "api_key", apiKey,
                "api_secret", apiSecret));
    }

}
