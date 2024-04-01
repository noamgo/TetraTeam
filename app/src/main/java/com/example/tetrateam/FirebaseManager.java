package com.example.tetrateam;

import com.google.android.gms.tasks.Task;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;


/**
 * FirebaseManager
 * This class is used to directly interact with Firebase.
 *
 * @version 1.0
 */
public class FirebaseManager {
    public static void signOut() {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signOut();
    }

    public static boolean isSignedIn() {
        return FirebaseAuth.getInstance().getCurrentUser() != null;
    }

    public static Task<DataSnapshot> getUserUsername() {
        return FirebaseDatabase.getInstance().getReference("users").child(FirebaseAuth.getInstance().getUid()).child("username").get();
    }

    public static Task<DataSnapshot> getAllUserData() {
        return FirebaseDatabase.getInstance().getReference("users").child(FirebaseAuth.getInstance().getUid()).get();
    }

    // function that returns a string of the 3 users with the highest score in the database
    public static Task<DataSnapshot> getTop3Users() {
        return FirebaseDatabase.getInstance().getReference().child("users").orderByChild("highScore").limitToLast(3).get();
    }

    public static Task<AuthResult> signIn(String email, String password) {
        return FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password);
    }

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

