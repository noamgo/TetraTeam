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
    TextView tvStats, tvRank1, tvPlayer1, tvScore1, tvRank2, tvPlayer2, tvScore2, tvRank3, tvPlayer3, tvScore3;
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

        ArrayList<User> top3Users = new ArrayList<>();

        tvStats = findViewById(R.id.tvStats);

        tvRank1 = findViewById(R.id.tvRank1);
        tvPlayer1 = findViewById(R.id.tvPlayer1);
        tvScore1 = findViewById(R.id.tvScore1);
        tvRank2 = findViewById(R.id.tvRank2);
        tvPlayer2 = findViewById(R.id.tvPlayer2);
        tvScore2 = findViewById(R.id.tvScore2);
        tvRank3 = findViewById(R.id.tvRank3);
        tvPlayer3 = findViewById(R.id.tvPlayer3);
        tvScore3 = findViewById(R.id.tvScore3);

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
                                DataSnapshot dataSnapshot = task2.getResult();
                                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                                    User user = userSnapshot.getValue(User.class);
                                    top3Users.add(user);
                                }

                                // Sort the list of top 3 users by high score in descending order
                                Collections.sort(top3Users, new Comparator<User>() {
                                    @Override
                                    public int compare(User user1, User user2) {
                                        // Compare by high score
                                        return Long.compare(user2.getHighScore(), user1.getHighScore());
                                    }
                                });
                            }

                            // Display the top 3 users in ascending order
                            User user1 = top3Users.get(0);
                            User user2 = top3Users.get(1);
                            User user3 = top3Users.get(2);

                            tvRank1.setText("1");
                            tvPlayer1.setText(user1.getUsername());
                            tvScore1.setText(Long.toString(user1.getHighScore()));

                            tvRank2.setText("2");
                            tvPlayer2.setText(user2.getUsername());
                            tvScore2.setText(Long.toString(user2.getHighScore()));

                            tvRank3.setText("3");
                            tvPlayer3.setText(user3.getUsername());
                            tvScore3.setText(Long.toString(user3.getHighScore()));

                        });
                    }
                });
    }
}