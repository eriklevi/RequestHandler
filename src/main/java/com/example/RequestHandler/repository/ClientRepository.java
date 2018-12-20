package com.example.RequestHandler.repository;

import com.example.RequestHandler.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    Client findByUsername(String username);
    Boolean existsByUsername(String username);
}
