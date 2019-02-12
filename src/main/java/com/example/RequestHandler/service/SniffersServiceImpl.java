package com.example.RequestHandler.service;

import com.example.RequestHandler.entity.Configuration;
import com.example.RequestHandler.entity.Room;
import com.example.RequestHandler.entity.Sniffer;
import com.example.RequestHandler.repository.RoomsRepository;
import com.example.RequestHandler.repository.SniffersRepository;
import com.example.RequestHandler.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

@Service
public class SniffersServiceImpl implements SniffersService {

    @Autowired
    private SniffersRepository sniffersRepository;
    @Autowired
    private RoomsRepository roomsRepository;

    @Override
    public void addSniffer(Sniffer sniffer, HttpServletResponse response) {
        if(!sniffersRepository.existsByMac(sniffer.getMac())) {

            List<Sniffer> list = getSniffersByRoom(sniffer.getRoom());
            /*
            we need o check if there is already a sniffer with the same name inside the room
             */
            if (list == null) {
                //we dont have any sniffer in the given room so we proceed to add it
                Optional<Room> optionalRoom = roomsRepository.findById(sniffer.getRoom());
                addProcedure(sniffer, response, optionalRoom);
            } else {
                if (list.stream()
                        .map(Sniffer::getName)
                        .anyMatch(str -> str.equals(sniffer.getName()))) {
                    //we have already a sniffer with the given name inside the room
                    response.setStatus(400);
                } else {
                    Optional<Room> optionalRoom = roomsRepository.findById(sniffer.getRoom());
                    addProcedure(sniffer, response, optionalRoom);
                }
            }
        }
        else{
            response.setStatus(400);
        }
    }

    public void addProcedure(Sniffer sniffer, HttpServletResponse response, Optional<Room> optionalRoom) {
        if (optionalRoom.isPresent()) {
            Room room = optionalRoom.get();
            Sniffer newSniffer = new Sniffer();
            newSniffer.setMac(sniffer.getMac());
            newSniffer.setName(sniffer.getName());
            newSniffer.setBuilding(sniffer.getBuilding());
            newSniffer.setRoom(sniffer.getRoom());
            newSniffer.setLocation(sniffer.getLocation());
            Sniffer sniffer1 = sniffersRepository.insert(newSniffer);
            room.addsniffer(sniffer1.getId());//we use sniffer1 to be shure to get the sniffer id
            roomsRepository.save(room);
            response.setStatus(200);
        }
        else{
            response.setStatus(400);
        }
    }

    @Override
    public void deleteSnifferById(String id, HttpServletResponse response) {
        if(sniffersRepository.existsById(id)){
            sniffersRepository.deleteById(id);
            System.out.println("Cancellato sniffer: "+id);
            response.setStatus(200);
        }
        else{
            response.setStatus(400);
        }
    }

    @Override
    public void updateSnifferById(String id, Sniffer sniffer, HttpServletResponse response) {
        Optional<Sniffer> optionalSniffer = sniffersRepository.findById(id);
        if(optionalSniffer.isPresent()
                && sniffer.getId().equals(id) //we have to check correspondence
                && !sniffersRepository.existsByMac(sniffer.getMac()) //mac should be unique
        ){
            sniffersRepository.save(sniffer);
            response.setStatus(200);
        }
        else{
            response.setStatus(400);
        }
    }

    @Override
    public Sniffer getSnifferById(String id, HttpServletResponse response) {
            Optional<Sniffer> optionalSniffer = sniffersRepository.findById(id);
            if(optionalSniffer.isPresent()){
                response.setStatus(200);
                return optionalSniffer.get();
            }
            else{
                response.setStatus(400);
                return null;
            }
        }

    @Override
    public List<Sniffer> getSniffersByRoom(String room, HttpServletResponse response) {
        List<Sniffer> sniffers = sniffersRepository.findByRoom(room);
        if(sniffers != null)
            response.setStatus(200);
        else
            response.setStatus(400);
        return sniffers;
    }

    @Override
    public List<Sniffer> getSniffersByBuilding(String building, HttpServletResponse response) {
        List<Sniffer> sniffers = sniffersRepository.findByRoom(building);
        if(sniffers != null)
            response.setStatus(200);
        else
            response.setStatus(400);
        return sniffers;
    }

    @Override
    public List<Sniffer> getSniffers(HttpServletResponse response) {
        response.setStatus(200);
        return sniffersRepository.findAll();
    }

    @Override
    public Configuration getSnifferConfigurationById(String id, HttpServletResponse response) {
        Optional<Sniffer> optionalSniffer = sniffersRepository.findById(id);
        if(optionalSniffer.isPresent()){
            response.setStatus(200);
            return optionalSniffer.get().getConfiguration();
        }
        else{
            response.setStatus(400);
            return null;
        }
    }

    @Override
    public List<Sniffer> getSniffersByRoom(String room) {
        return sniffersRepository.findByRoom(room);
    }
}
