package com.example.RequestHandler.service;

import com.example.RequestHandler.entity.Configuration;
import com.example.RequestHandler.entity.Sniffer;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

public interface SniffersService {
    void addSniffer(Sniffer sniffer, HttpServletResponse response);
    void deleteSnifferByName(String name, HttpServletResponse response);
    void updateSnifferByName(String name,Sniffer sniffer, HttpServletResponse response);
    Sniffer getSnifferByName(String name, HttpServletResponse response);
    List<Sniffer> getSniffersByRoom(String room, HttpServletResponse response);
    List<Sniffer> getSniffersByBuilding(String building, HttpServletResponse response);
    List<Sniffer> getSniffers(HttpServletResponse response);
    Configuration getSnifferConfigurationByName(String name, HttpServletResponse response);
}
