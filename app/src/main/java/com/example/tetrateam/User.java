package com.example.tetrateam;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

// User class that contains the information of the user
public class User {

    // variables
    public String username;
    public String email;
    public String phone;
    public int highScore;

    // Firebase Database instance
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

    // constructor with parameters
    public User(String username, String email, String phone) {
        this.username = username;
        this.email = email;
        this.phone = phone;
        highScore = 0;
    }

    // write the user to the database
    public void writeNewUser(String userId, String name, String email, String phone) {
        User user = new User(name, email, phone);

        mDatabase.child("users").child(userId).setValue(user);
    }

    // getters and setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getHighScore() {
        return highScore;
    }

    public void setHighScore(int highScore) {
        this.highScore = highScore;
    }
}
