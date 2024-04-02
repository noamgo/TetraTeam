package com.example.tetrateam;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import Tetris.TetrisGame;

public class GameMenuActivity extends BaseMenuActivity {

    TextView tvWelcome;
    Button btn1Player, btn2Players, btnStats;
    Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_menu);

        tvWelcome = findViewById(R.id.tvWelcome);
        FirebaseManager.getUserUsername()
                        .addOnCompleteListener(task -> {
                           if(task.isSuccessful()) {
                               String username = task.getResult().getValue(String.class);
                               tvWelcome.setText("Welcome " + username + "!");
                           }
                        });

        btn1Player = findViewById(R.id.startGame);
        btn1Player.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(GameMenuActivity.this, TetrisGame.class);
                startActivity(intent);
            }
        });

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