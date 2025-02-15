package com.ecommerce.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ecommerce.dto.UserDto;
import com.ecommerce.model.User;
import com.ecommerce.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);

    public UserDto createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User newUser =  userRepository.save(user);

        return convertEntityToDto(newUser);
    }

    public List<UserDto> findAll(String search, String role) {
        if(search != null) {
            List<User> user =  userRepository.searchUsers(search);
            return user.stream().map(this::convertEntityToDto).toList();
        }

        if(role != null) {
            List<User> user = userRepository.findByRole(role);
            return user.stream().map(this::convertEntityToDto).toList();
        }

        List<User> user = userRepository.findAll();
        return user.stream().map(this::convertEntityToDto).toList();
    }

    private UserDto convertEntityToDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setRole(user.getRole());
        return userDto;
    }

}
