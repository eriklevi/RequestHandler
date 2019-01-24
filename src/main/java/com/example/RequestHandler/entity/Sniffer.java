package com.example.RequestHandler.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Document(collection = "sniffers")
public class Sniffer {

    @Id private String id;
    @NotNull
    @Pattern(regexp = "^([0-9A-Fa-f]{2}[:]){5}([0-9A-Fa-f]{2})$")
    private String mac;
    @NotNull private String name;
    @NotNull private String building;
    @NotNull private String room;
    @NotNull private GeoJsonPoint location;

    public Sniffer(){}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public GeoJsonPoint getLocation() {
        return location;
    }

    public void setLocation(GeoJsonPoint location) {
        this.location = location;
    }
}
