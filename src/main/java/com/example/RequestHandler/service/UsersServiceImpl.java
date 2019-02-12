package com.example.RequestHandler.service;

import com.example.RequestHandler.entity.User;
import com.example.RequestHandler.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

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
            newUser.setRoles(user.getRoles());
            usersRepository.save(newUser);
            response.setStatus(200);
        }
        else{
            response.setStatus(400);
        }
    }

    @Override
    public List<User> getUsers(HttpServletResponse response) {
        response.setStatus(200);
        List<User> users = usersRepository.findAll();
        return users;
    }

    @Override
    public User getUserById(String id, HttpServletResponse response) {
        Optional<User> user = usersRepository.findById(id);
        if(user.isPresent()){
            response.setStatus(200);
            return user.get();
        } else{
            response.setStatus(400);
            return null;
        }
    }

    @Override
    public void deleteUser(String id, HttpServletResponse response) {
        usersRepository.deleteById(id);
        response.setStatus(200);
    }

    @Override
    public void updateUserById(User user, String id, HttpServletResponse response) {
        if(usersRepository.existsById(id) && user.getId().equals(id)){
            usersRepository.save(user);
            response.setStatus(200);
        }
        else{
            response.setStatus(400);
        }
    }
}
