package com.example.RequestHandler.repository;

import com.example.RequestHandler.entity.Room;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomsRepository extends MongoRepository<Room, String> {
}
