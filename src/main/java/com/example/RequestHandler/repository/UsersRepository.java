package com.example.RequestHandler.repository;

import com.example.RequestHandler.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends MongoRepository<User, String> {
    User findByUsername(String username);
    Boolean existsByUsername(String username);
    Boolean existsByMail(String email);
}
