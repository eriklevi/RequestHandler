package com.example.RequestHandler.service;

import com.example.RequestHandler.entity.User;
import com.example.RequestHandler.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

@Service
public class UsersServiceImpl implements UsersService {

    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void addUser(User user, HttpServletResponse response) {
        //Check if the user already exists
        if(!usersRepository.existsByMail(user.getMail())){
            User newUser = new User();
            newUser.setMail(user.getMail());
            newUser.setUsername(user.getUsername());
            newUser.setPassword(passwordEncoder.encode(user.getPassword()));
            ArrayList<String> roles = new ArrayList<String>();
            roles.add("USER");
            newUser.setRoles(roles);
            usersRepository.save(newUser);
            response.setStatus(200);
        }
        else{
            response.setStatus(400);
        }
    }
}
