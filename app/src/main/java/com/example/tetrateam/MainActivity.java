package com.example.tetrateam;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

// Main menu (extends AppCompatActivity to easily use the menu without using all of the menu functions again)
public class MainActivity extends AppCompatActivity {

    // variables
    Button btnPlay, btnAbout, btnRules;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // button that moves the user to the SignIn/Up Fragments (if not signed in) or the Game Menu (if signed in)
        btnPlay = findViewById(R.id.btnPlay);
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(MainActivity.this, MainFragmentHub.class);
                startActivity(intent);
            }
        });

        // button that moves the user to the About Me Activity
        btnAbout = findViewById(R.id.btnAbout);
        btnAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(MainActivity.this, AboutMe.class);
                startActivity(intent);
            }
        });

        // button that opens the rules google docs
        btnRules = findViewById(R.id.btnRules);
        btnRules.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openUrl(view);
            }
        });
    }

    // function that opens the rules google docs
    public void openUrl(View view) {
        // Replace the URL with your desired URL
        String url = "https://docs.google.com/document/d/1Cc8r7-TKiNB5eVwPfK9A97U3VVGC874jQe7UAKzIRqw/edit?usp=sharing";
        intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
    }
}