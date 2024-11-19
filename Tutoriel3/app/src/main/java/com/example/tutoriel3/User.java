package com.example.tutoriel3;

public class User {
    public String username;
    public String email;
    public String password;
    String id;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String username,String email, String password,String id) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
