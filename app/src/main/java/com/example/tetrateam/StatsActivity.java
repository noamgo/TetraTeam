package com.example.tetrateam;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

// Stats Activity that shows the stats of the user and the high score of top players (extends BaseMenuActivity to easily use the menu without using all of the menu functions again)
public class StatsActivity extends BaseMenuActivity {

    // variables
    Button btnBack;
    Intent intent;
    TextView tvStats;
    User currentUser;

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

        // get the stats of the user
        FirebaseManager.getAllUserData()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        currentUser = task.getResult().getValue(User.class);

                        // print the stats of the current user
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

                        // get the top 3 users from the database
                        FirebaseManager.getTop3Users().addOnCompleteListener(task2 -> {
                            if (task2.isSuccessful()) {
                                ArrayList<User> top3Users = new ArrayList<>();
                                DataSnapshot dataSnapshot = task2.getResult();
                                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                                    User user = userSnapshot.getValue(User.class);
                                    top3Users.add(user);
                                }

                                // Sort the list of top 3 users by high score in ascending order
                                Collections.sort(top3Users, new Comparator<User>() {
                                    @Override
                                    public int compare(User user1, User user2) {
                                        // Compare by high score
                                        return Long.compare(user1.getHighScore(), user2.getHighScore());
                                    }
                                });

                                // Display the top 3 users in ascending order
                                int place = 1;
                                for (int i = top3Users.size() - 1; i >= 0; i--) {
                                    User user = top3Users.get(i);
                                    String username = user.getUsername();
                                    long highScore = user.getHighScore();
                                    tvStats.append("Place: " + place + "\n" +
                                            "Username: " + username + "\n" +
                                            "High Score: " + highScore + "\n\n");
                                    place++;
                                }
                            }
                        });
                    }
                });
    }
}