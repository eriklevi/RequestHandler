package com.example.RequestHandler.controller;

import com.example.RequestHandler.entity.Configuration;
import com.example.RequestHandler.entity.Sniffer;
import com.example.RequestHandler.service.SniffersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@RestController
@Validated
@RequestMapping("/sniffers")
public class SniffersController {

    @Autowired
    private SniffersService sniffersService;


    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(value = "", method = RequestMethod.POST)
    public void createSniffer(@RequestBody @Valid Sniffer sniffer, HttpServletResponse response){
        sniffersService.addSniffer(sniffer, response);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public @ResponseBody Sniffer
    getSnifferByName(@PathVariable @NotEmpty String id, HttpServletResponse response){
        return sniffersService.getSnifferById(id, response);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @RequestMapping(value = "", method = RequestMethod.GET)
    public @ResponseBody List<Sniffer>
    getSniffers(HttpServletResponse response){
        return sniffersService.getSniffers(response);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(value = "/{name}", method = RequestMethod.DELETE)
    public void removeSnifferByName(@PathVariable @NotEmpty String id, HttpServletResponse response){
        sniffersService.deleteSnifferById(id, response);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(value = "/{name}", method = RequestMethod.PUT)
    public void updateSnifferByName(@RequestBody @Valid Sniffer sniffer, @PathVariable @NotEmpty String name, HttpServletResponse response){
        sniffersService.updateSnifferById(name, sniffer, response);
    }

    @PreAuthorize("hasAuthority('SNIFFER')")
    @RequestMapping(value = "/{name}/config")
    public @ResponseBody Configuration
    getSnifferConfiguration(@PathVariable String name, HttpServletResponse response){
        return sniffersService.getSnifferConfigurationById(name, response);
    }


    /**
     * Nel caso una path variable non rispettasse i criteri di validazione verrebbe lanciata una ConstraintViolationException
     * che non ha un gestore predefinito che setta il repsonse status a 400 e quindi viene ritornata un 500.
     * In questo modo il server ritorna uno status sensato.
     * @param e
     * @return
     */
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponseEntity<String> handleConstraintViolationException(ConstraintViolationException e) {
        return new ResponseEntity<>("not valid due to validation error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
    }

}
