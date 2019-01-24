package com.example.RequestHandler.entity;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "users")
public class User {

    @Id private String id;
    @NotNull @Email private String mail;
    @NotNull private String username;
    @NotNull private String password;
    @NotNull private ArrayList<String> roles;

    public User(){}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMail() { return mail; }

    public void setMail(String mail) { this.mail = mail; }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(ArrayList<String> roles){
        this.roles = roles;
    }
}
