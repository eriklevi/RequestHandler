package com.example.RequestHandler.controller;

import com.example.RequestHandler.entity.Room;
import com.example.RequestHandler.service.RoomsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@RestController
@Validated
@RequestMapping("/rooms")
public class RoomsController {

    @Autowired
    private RoomsService roomsService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(value = "", method = RequestMethod.GET)
    public @ResponseBody List<Room>
    getRooms(HttpServletResponse response){
        return roomsService.getAllRooms(response);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public @ResponseBody Room
    getRoomById(@PathVariable @NotEmpty String id, HttpServletResponse response){
        return roomsService.getRoomById(id, response);
    }
}
