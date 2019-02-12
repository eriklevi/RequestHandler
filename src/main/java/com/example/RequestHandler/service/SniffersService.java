package com.example.RequestHandler.service;

import com.example.RequestHandler.entity.Configuration;
import com.example.RequestHandler.entity.Sniffer;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

public interface SniffersService {
    void addSniffer(Sniffer sniffer, HttpServletResponse response);
    void deleteSnifferById(String id, HttpServletResponse response);
    void updateSnifferById(String id,Sniffer sniffer, HttpServletResponse response);
    Sniffer getSnifferById(String id, HttpServletResponse response);
    List<Sniffer> getSniffersByRoom(String room, HttpServletResponse response);
    List<Sniffer> getSniffersByRoom(String room);
    List<Sniffer> getSniffersByBuilding(String building, HttpServletResponse response);
    List<Sniffer> getSniffers(HttpServletResponse response);
    Configuration getSnifferConfigurationById(String id, HttpServletResponse response);
}
