package com.example.RequestHandler.service;

import com.example.RequestHandler.entity.User;

import javax.servlet.http.HttpServletResponse;

public interface UsersService {
    void addUser(User user, HttpServletResponse response);

}
