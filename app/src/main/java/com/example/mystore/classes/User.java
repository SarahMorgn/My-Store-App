package com.example.mystore.classes;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class User extends ViewModel {
    private String userId;
    private String email;
    private String role;
    private String name;

    public User(String userId,String email,String name){
        this.userId=userId;
        this.email=email;
        this.name=name;
    }
    public String getUserId() {
        return userId;
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }

    public String getName() {
        return name;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setName(String name) {
        this.name = name;
    }
}
