package com.example.tetrateam;

import com.google.android.gms.tasks.Task;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;

// Class used to interact with Firebase
public class FirebaseManager {

    // sign out the user
    public static void signOut() {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signOut();
    }

    // return true if the user is signed in else false
    public static boolean isSignedIn() {
        return FirebaseAuth.getInstance().getCurrentUser() != null;
    }

    // get the username of the current user
    public static Task<DataSnapshot> getUserUsername() {
        return FirebaseDatabase.getInstance().getReference("users").child(FirebaseAuth.getInstance().getUid()).child("username").get();
    }

    // gets all the data of the current user
    public static Task<DataSnapshot> getAllUserData() {
        return FirebaseDatabase.getInstance().getReference("users").child(FirebaseAuth.getInstance().getUid()).get();
    }

    // function that returns a Task that gets the top 3 users
    public static Task<DataSnapshot> getTop3Users() {
        return FirebaseDatabase.getInstance().getReference().child("users").orderByChild("highScore").limitToLast(3).get();
    }

    // sign in the user
    public static Task<AuthResult> signIn(String email, String password) {
        return FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password);
    }

    // send a password reset to the user's email
    public static Task<Void> sendPasswordReset(String email) {
        return FirebaseAuth.getInstance().sendPasswordResetEmail(email);
    }

    //update the high score in the database if newScore is higher than the current high score
    public static void updateHighScore(int newScore) {
        // get the current high score from the database
        Task<DataSnapshot> currentHighScoreTask = FirebaseDatabase.getInstance().getReference("users").child(FirebaseAuth.getInstance().getUid()).child("highScore").get();
        currentHighScoreTask.addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                int currentHighScore = task.getResult().getValue(Integer.class);
                if (newScore > currentHighScore) {
                    // update the high score in the database
                    FirebaseDatabase.getInstance().getReference("users").child(FirebaseAuth.getInstance().getUid()).child("highScore").setValue(newScore);
                }
            }
        });
    }
}

