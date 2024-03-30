package com.example.tetrateam;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import Tetris.TetrisGame;

public class MainActivity extends AppCompatActivity {

    Button btnSignInUp, btnAbout, btnRules;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSignInUp = findViewById(R.id.btnSignInUp);
        btnSignInUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(MainActivity.this, MainFragmentHub.class);
                startActivity(intent);
            }
        });

        btnAbout = findViewById(R.id.btnAbout);
        btnAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(MainActivity.this, AboutMe.class);
                startActivity(intent);
            }
        });

        btnRules = findViewById(R.id.btnRules);
        btnRules.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openUrl(view);
            }
        });
    }

    public void openUrl(View view) {
        // Replace the URL with your desired URL
        String url = "https://docs.google.com/document/d/1Cc8r7-TKiNB5eVwPfK9A97U3VVGC874jQe7UAKzIRqw/edit?usp=sharing";
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
    }
}