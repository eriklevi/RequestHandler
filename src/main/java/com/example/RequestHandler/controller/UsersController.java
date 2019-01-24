package com.example.RequestHandler.controller;

import com.example.RequestHandler.entity.User;
import com.example.RequestHandler.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/users")
public class UsersController {

    @Autowired
    private UsersService usersService;

    @RequestMapping(value = "", method = RequestMethod.POST)
    public void createUser(@RequestBody User user, HttpServletResponse response){
        usersService.addUser(user, response);
    }
}

