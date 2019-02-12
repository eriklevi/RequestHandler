package com.example.RequestHandler.service;

import com.example.RequestHandler.entity.Configuration;
import com.example.RequestHandler.entity.Sniffer;
import com.example.RequestHandler.repository.SniffersRepository;
import com.example.RequestHandler.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Service
public class SniffersServiceImpl implements SniffersService {

    @Autowired
    private SniffersRepository sniffersRepository;

    @Override
    public void addSniffer(Sniffer sniffer, HttpServletResponse response) {
        if(!sniffersRepository.existsByName(sniffer.getName())){
            Sniffer newSniffer = new Sniffer();
            newSniffer.setMac(sniffer.getMac());
            newSniffer.setName(sniffer.getName());
            newSniffer.setBuilding(sniffer.getBuilding());
            newSniffer.setRoom(sniffer.getRoom());
            newSniffer.setLocation(sniffer.getLocation());
            sniffersRepository.insert(newSniffer);
            response.setStatus(200);
        }
        else{
            response.setStatus(400);
        }
    }

    @Override
    public void deleteSnifferByName(String name, HttpServletResponse response) {
        if(sniffersRepository.existsByName(name)){
            sniffersRepository.deleteByName(name);
            System.out.println("Cancellato sniffer: "+name);
            response.setStatus(200);
        }
        else{
            response.setStatus(400);
        }
    }

    @Override
    public void updateSnifferByName(String name, Sniffer sniffer, HttpServletResponse response) {
        Sniffer sniffer1 = sniffersRepository.findByName(name);
        if(sniffer1 != null && name.equals(sniffer.getName())){
            sniffersRepository.save(sniffer);
            response.setStatus(200);
        }
        else{
            response.setStatus(400);
        }
    }

    @Override
    public Sniffer getSnifferByName(String name, HttpServletResponse response) {
            Sniffer sniffer = sniffersRepository.findByName(name);
            if(sniffer != null){
                response.setStatus(200);
                return sniffer;
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
    public Configuration getSnifferConfigurationByName(String name, HttpServletResponse response) {
        Sniffer sniffer = sniffersRepository.findByName(name);
        if(sniffer != null){
            response.setStatus(200);
            return sniffer.getConfiguration();
        }
        else{
            response.setStatus(400);
            return null;
        }
    }
}
