package com.example.RequestHandler.service;

import com.example.RequestHandler.entity.Client;
import com.example.RequestHandler.entity.Role;
import com.example.RequestHandler.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Client client = clientRepository.findByUsername(username);
        if(client == null){
            throw new UsernameNotFoundException(String.format("The username %s doesn't exist", username));
        }
        List<GrantedAuthority> authorities = new ArrayList<>();
        List<Role> roles = client.getRoles();
        for(Role role : roles){
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        UserDetails userDetails = new org.springframework.security.core.userdetails.User(client.getUsername(), client.getPassword(), authorities);
        return userDetails;
    }
}
