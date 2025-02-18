package com.ecommerce.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ecommerce.dto.UserDto;
import com.ecommerce.dto.UserWithTokenDto;
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

    
    public String seedAdmin() {
        List<User> hasAdmin = userRepository.findByRole("ROLE_ADMIN");

        if(hasAdmin.size() == 0) {
            User user = new User();
            user.setUsername("admin");
            user.setPassword(passwordEncoder.encode("admin"));
            user.setEmail("admin@gmail.com");
            user.setRole("ROLE_ADMIN");
            userRepository.save(user);
        }
        return "Admin created";
    }

    public UserWithTokenDto createUser(User user) {

        if(hasEmail(user.getEmail())){
            throw new RuntimeException("Email already exists");
        }

        user.setRole("ROLE_USER");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User newUser =  userRepository.save(user);

        String token = jwtService.generateToken(user.getEmail());
        return convertEntityWithTokenToDto(newUser, token);
    }

    public UserWithTokenDto verify(User user) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
        if(!authentication.isAuthenticated())
            throw new RuntimeException("Invalid credentials");

        User getUser = userRepository.findByEmail(user.getEmail());

        String token = jwtService.generateToken(user.getEmail());
        
        return convertEntityWithTokenToDto(getUser, token);
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

    //utils
    public Boolean hasEmail(String email){
        return userRepository.existsByEmail(email);
    }

    //converting dto
    private UserDto convertEntityToDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setEmail(user.getEmail());
        userDto.setRole(user.getRole());
        return userDto;
    }

    private UserWithTokenDto convertEntityWithTokenToDto(User user, String token) {
        UserWithTokenDto userDto = new UserWithTokenDto();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setEmail(user.getEmail());
        userDto.setRole(user.getRole());
        userDto.setToken(token);
        return userDto;
    }

}
