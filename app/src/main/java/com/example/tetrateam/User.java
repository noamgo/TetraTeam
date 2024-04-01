package com.example.tetrateam;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class User {

    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    public String username;
    public String email;
    public String phone;
    public int highScore;

    public User() {
    }

    public User(String username, String email, String phone) {
        this.username = username;
        this.email = email;
        this.phone = phone;
        highScore = 0;
    }

    public void writeNewUser(String userId, String name, String email, String phone) {
        User user = new User(name, email, phone);

        mDatabase.child("users").child(userId).setValue(user);
    }

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

    public void deleteUser() {
        if (mDatabase != null) {
            mDatabase.child("users").removeValue();
        }
    }
}
