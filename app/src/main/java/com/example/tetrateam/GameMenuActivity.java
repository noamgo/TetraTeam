package com.example.tetrateam;

import androidx.appcompat.app.AppCompatActivity;

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
        tvWelcome.setText("Welcome " + FirebaseManager.getUserUsername() + "!");

        btn1Player = findViewById(R.id.btn1player);
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