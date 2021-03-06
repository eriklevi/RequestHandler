package com.example.RequestHandler.service;

import com.example.RequestHandler.entity.Room;
import com.example.RequestHandler.repository.RoomsRepository;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

@Service
public class RoomsServiceImpl implements RoomsService {

    @Autowired
    private RoomsRepository roomsRepository;

    @Override
    public List<Room> getAllRooms(HttpServletResponse response) {
        response.setStatus(200);
        return roomsRepository.findAll();
    }

    @Override
    public Room getRoomById(String id, HttpServletResponse response) {
        Optional<Room> optionalRoom = roomsRepository.findById(id);
        if(optionalRoom.isPresent()){
            response.setStatus(200);
            return optionalRoom.get();
        }
        else{
            response.setStatus(400);
            return null;
        }
    }

    @Override
    public void addRoom(Room room) {

    }
}
