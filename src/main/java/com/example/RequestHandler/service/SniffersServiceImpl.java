package com.example.RequestHandler.service;

import com.example.RequestHandler.entity.Configuration;
import com.example.RequestHandler.entity.Room;
import com.example.RequestHandler.entity.Sniffer;
import com.example.RequestHandler.entity.User;
import com.example.RequestHandler.repository.RoomsRepository;
import com.example.RequestHandler.repository.SniffersRepository;
import com.example.RequestHandler.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.security.MessageDigest;

@Service
public class SniffersServiceImpl implements SniffersService {

    private final SniffersRepository sniffersRepository;
    private final RoomsRepository roomsRepository;
    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public SniffersServiceImpl(SniffersRepository sniffersRepository, RoomsRepository roomsRepository, UsersRepository usersRepository, PasswordEncoder passwordEncoder) {
        this.sniffersRepository = sniffersRepository;
        this.roomsRepository = roomsRepository;
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
    }

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
                try {
                    addProcedure(sniffer, response, optionalRoom);
                }
                catch(Exception e){
                    System.out.println(e.getMessage());
                    response.setStatus(400);
                }
            } else {
                if (list.stream()
                        .map(Sniffer::getName)
                        .anyMatch(str -> str.equals(sniffer.getName()))) {
                    //we have already a sniffer with the given name inside the room
                    response.setStatus(400);
                } else {
                    Optional<Room> optionalRoom = roomsRepository.findById(sniffer.getRoom());
                    try {
                        addProcedure(sniffer, response, optionalRoom);
                    }
                    catch(Exception e){
                        System.out.println(e.getMessage());
                        response.setStatus(400);
                    }
                }
            }
        }
        else{
            response.setStatus(400);
        }
    }

    private void addProcedure(Sniffer sniffer, HttpServletResponse response, Optional<Room> optionalRoom) throws NoSuchAlgorithmException {
        if (optionalRoom.isPresent()) {
            Room room = optionalRoom.get();
            Sniffer newSniffer = new Sniffer();
            newSniffer.setMac(sniffer.getMac());
            newSniffer.setName(sniffer.getName());
            newSniffer.setBuilding(sniffer.getBuilding());
            newSniffer.setRoom(sniffer.getRoom());
            newSniffer.setMacID(sniffer.getMac().replace(":", ""));
            newSniffer.setLocation(sniffer.getLocation());
            newSniffer.setConfiguration(sniffer.getConfiguration());
            Sniffer sniffer1 = sniffersRepository.insert(newSniffer);
            room.addsniffer(sniffer1.getId());//we use sniffer1 to be shure to get the sniffer id
            roomsRepository.save(room);
            User u = new User();
            u.setUsername(sniffer.getMac().replace(":", ""));
            String password = sniffer1.getMac().replace(":", "") + "secret12345";
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hashInBytes = md.digest(password.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : hashInBytes){
                sb.append(String.format("%02x", b));
            }
            password = sb.toString().substring(0, 12);
            System.out.println("Password for " + u.getUsername() + ": " + password);
            u.setPassword(passwordEncoder.encode(password));
            List<String> roles = new ArrayList<>();
            roles.add("SNIFFER");
            u.setRoles(roles);
            usersRepository.save(u);
            response.setStatus(200);
        }
        else{
            response.setStatus(400);
        }
    }

    @Override
    //TODO cancellare a cascata anche l'utente correlato
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
    //TODO controllare quali sono i campi che possono essere effettvamente modificati tipo mac address no o id
    public void updateSnifferById(String id, Sniffer sniffer, HttpServletResponse response) {
        Optional<Sniffer> optionalSniffer = sniffersRepository.findById(id);
        if(optionalSniffer.isPresent()
                && sniffer.getId().equals(id) //we have to check correspondence

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
    public Configuration getSnifferConfigurationByMacId(String id, HttpServletResponse response) {
        Optional<Sniffer> optionalSniffer = sniffersRepository.findByMacID(id);
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
