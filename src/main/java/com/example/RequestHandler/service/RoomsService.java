package com.example.RequestHandler.service;

import com.example.RequestHandler.entity.Room;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface RoomsService {

    List<Room> getAllRooms(HttpServletResponse response);
    Room getRoomById(String id, HttpServletResponse response);
    void addRoom(Room room);
}
