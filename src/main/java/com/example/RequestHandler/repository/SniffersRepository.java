package com.example.RequestHandler.repository;

import com.example.RequestHandler.entity.Sniffer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SniffersRepository extends MongoRepository<Sniffer,String> {
    Boolean existsByName(String name);
    Sniffer findByName(String name);
    List<Sniffer> findByBuilding(String building);
    List<Sniffer> findByRoom(String room);

}
