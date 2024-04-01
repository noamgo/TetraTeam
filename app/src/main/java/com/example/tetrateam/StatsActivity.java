package com.example.tetrateam;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.GenericTypeIndicator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class StatsActivity extends BaseMenuActivity {

    Button btnBack;
    Intent intent;
    TextView tvStats;
    User currentUser;
    int highScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(StatsActivity.this, GameMenuActivity.class);
                startActivity(intent);
            }
        });

        tvStats = findViewById(R.id.tvStats);

        FirebaseManager.getAllUserData()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        currentUser = task.getResult().getValue(User.class);

                        tvStats.setText("User information: \n" +
                                "username: " + "\n" + currentUser.getUsername() + "\n" +
                                "\n" +
                                "email: " + "\n" + currentUser.getEmail() + "\n" +
                                "\n" +
                                "phone: " + "\n" + currentUser.getPhone() + "\n" +
                                "\n" + "\n" +
                                "Your high score is: " + "\n" + currentUser.getHighScore() +
                                "\n" + "\n" +
                                "Top 3 players: " + "\n");

                        FirebaseManager.getTop3Users().addOnCompleteListener(task2 -> {
                            if (task2.isSuccessful()) {
                                HashMap<String, HashMap<String, Object>> top3UsersMap = (HashMap<String, HashMap<String, Object>>) task2.getResult().getValue();
                                if (top3UsersMap != null) {
                                    for (String userId : top3UsersMap.keySet()) {
                                        HashMap<String, Object> userData = top3UsersMap.get(userId);
                                        // Assuming each user node contains "username" and "highScore" fields
                                        String username = (String) userData.get("username");
                                        long highScore = (long) userData.get("highScore");
                                        // Now you have the user data, you can use it as needed
                                        tvStats.append(username + " high score: " + highScore + "\n");
                                    }
                                }
                            } else {
                                tvStats.append("Error: " + task2.getException().getMessage());
                            }
                        });
                    }
                });
    }
}