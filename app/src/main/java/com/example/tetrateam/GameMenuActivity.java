package com.example.tetrateam;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

// Menu for the Game and stats after user is signed in (extends BaseMenuActivity to easily use the menu without using all of the menu functions again)
public class GameMenuActivity extends BaseMenuActivity {

    // variables
    TextView tvWelcome;
    Button btnStart, btn2Players, btnStats;
    Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_menu);

        // print welcome message with username of the user
        tvWelcome = findViewById(R.id.tvWelcome);
        FirebaseManager.getUserUsername()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        String username = task.getResult().getValue(String.class);
                        tvWelcome.setText("Welcome " + username + "!");
                    }
                });

        // button to go to the tetris game
        btnStart = findViewById(R.id.startGame);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(GameMenuActivity.this, TetrisGame.class);
                startActivity(intent);
            }
        });

        // button to go to the stats activity
        btnStats = findViewById(R.id.btnStats);
        btnStats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(GameMenuActivity.this, StatsActivity.class);
                startActivity(intent);
            }
        });
    }
}