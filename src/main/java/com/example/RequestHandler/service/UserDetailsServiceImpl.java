package com.example.RequestHandler.service;

import com.example.RequestHandler.entity.User;
import com.example.RequestHandler.repository.UsersRepository;
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
    private UsersRepository usersRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = usersRepository.findByUsername(username);
        System.out.println("nome "+ user.getUsername()+ "password "+ user.getPassword()+" id "+ user.getId());
        if(user == null){
            throw new UsernameNotFoundException(String.format("The username %s doesn't exist", username));
        }
        List<GrantedAuthority> authorities = new ArrayList<>();
        List<String> roles = user.getRoles();
        for(String role : roles){
            authorities.add(new SimpleGrantedAuthority(role));
        }
        UserDetails userDetails = new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
        return userDetails;
    }
}
