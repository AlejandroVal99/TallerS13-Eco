package com.example.tallers13_eco;

import androidx.annotation.NonNull;

public class User {
    public String id;
    public String name, telephone, email, password;

    public User() {

    }

    public User(String id, String name, String telephone, String email, String password) {
        this.id = id;
        this.name = name;
        this.telephone = telephone;
        this.email = email;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}