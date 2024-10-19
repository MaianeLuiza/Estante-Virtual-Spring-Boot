package com.nam.estante_virtual.repository;


import com.nam.estante_virtual.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByLogin(String login);
}
