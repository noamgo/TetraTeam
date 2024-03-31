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

    public void setUsernameValue(String userId, String username) {
        mDatabase.child("users").child(userId).child("username").setValue(username);
    }

    public void setEmailValue(String userId, String email) {
        mDatabase.child("users").child(userId).child("email").setValue(email);
    }

    public void setPhoneValue(String userId, String phone) {
        mDatabase.child("users").child(userId).child("phone").setValue(phone);
    }

    public void setHighScoreValue(String userId, int highScore) {
        mDatabase.child("users").child(userId).child("highScore").setValue(highScore);
    }

    public String getUsernameValue(String userId) {
        return mDatabase.child("users").child(userId).child("username").toString();
    }

    public void deleteUser() {
        if (mDatabase != null) {
            mDatabase.child("users").removeValue();
        }
    }
}
