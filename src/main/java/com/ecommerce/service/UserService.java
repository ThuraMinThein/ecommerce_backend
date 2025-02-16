package com.ecommerce.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ecommerce.dto.UserDto;
import com.ecommerce.helper.JWTService;
import com.ecommerce.model.User;
import com.ecommerce.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JWTService jwtService;
    
    @Autowired
    private AuthenticationManager authenticationManager;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);

    public UserDto createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User newUser =  userRepository.save(user);

        String token = jwtService.generateToken(user.getUsername());
        return convertEntityToDto(newUser, token);
    }

    public UserDto verify(User user) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        if(!authentication.isAuthenticated())
            throw new RuntimeException("Invalid credentials");

        User getUser = userRepository.findByUsername(user.getUsername());

        String token = jwtService.generateToken(user.getUsername());
        
        return convertEntityToDto(getUser, token);
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

    private UserDto convertEntityToDto(User user, String token) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setRole(user.getRole());
        userDto.setToken(token);
        return userDto;
    }

}
