package com.ecommerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ecommerce.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{

    User findByUsername(String username);

    @Query(
        "SELECT u FROM User u WHERE " +
        "LOWER(u.username) ILIKE LOWER(CONCAT('%', :search, '%'))"
    )
    List<User> searchUsers(String search);

    List<User> findByRole(String role);

}
