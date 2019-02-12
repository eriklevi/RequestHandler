package com.example.RequestHandler.service;

import com.example.RequestHandler.entity.Building;
import com.example.RequestHandler.entity.Room;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface BuildingService {
    List<Room> getRoomsByBuildingId(String id, HttpServletResponse response);
    List<Building> getAllBuildings(HttpServletResponse response);
    void addBuilding(Building building, HttpServletResponse response);
    void addRoomToBuildingById(String roomId, String buildingId, HttpServletResponse response);
    void addRoomToBuildingById(Room room, String buildingId, HttpServletResponse response);
}
