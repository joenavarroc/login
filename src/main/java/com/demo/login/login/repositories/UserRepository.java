package com.demo.login.login.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.login.login.user.User;

public interface UserRepository extends JpaRepository<User,Integer>{
    Optional<User> findByUsername(String username);
}
