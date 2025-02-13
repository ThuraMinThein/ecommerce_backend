package com.ecommerce.third_party_services;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

@Service
public class CloudinaryService {

    @Autowired
    private Cloudinary cloudinary;

    public String uploadImage(MultipartFile image, String folderName) {
        try {
            File uploadedFile = convertMultiPartToFile(image);
            var uploadImage = cloudinary.uploader().upload(uploadedFile, ObjectUtils.asMap("folder", folderName));

            return uploadImage.get("url").toString();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "Failed to upload image");
        }
    }

    public void deleteImage(String imageUrl) {
        try {
            String publicId = extractPublicIdFromUrl(imageUrl);
            cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "Failed to delete image");
        }
    }


    //utils
    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convertedFile = new File(file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convertedFile);
        fos.write(file.getBytes());
        fos.close();
    return convertedFile;
    }

    private static final Pattern CLOUDINARY_URL_PATTERN = Pattern.compile(
        "/v\\d+/(.+?)\\.(?:jpg|jpeg|jpe|gif|png|bmp|svg|webp|mp4|webm|mov|ogv|flv|m3u8|ts)"
    );

    public String extractPublicIdFromUrl(String url) {
        Matcher matcher = CLOUDINARY_URL_PATTERN.matcher(url);
        
        if (matcher.find()) {
            return matcher.group(1);
        } else {
            throw new IllegalArgumentException("Invalid Cloudinary URL");
        }
    }

}
