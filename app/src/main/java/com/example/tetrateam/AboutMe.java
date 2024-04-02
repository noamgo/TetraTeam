package com.example.tetrateam;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

// About Me Activity (extends BaseMenuActivity to easily use the menu without using all of the menu functions again)
public class AboutMe extends BaseMenuActivity {

    // variables
    TextView tvAbout;
    Button btnBack;
    Intent intent;

    // Objects for reading the file
    InputStream is;
    InputStreamReader isr;
    BufferedReader br;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_me);

        tvAbout = findViewById(R.id.tvAbout);
        buildText();

        // Back button to go back to the menu (if signed in) or to the Game Menu (if not signed in)
        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (FirebaseManager.isSignedIn())
                    intent = new Intent(AboutMe.this, GameMenuActivity.class);
                else
                    intent = new Intent(AboutMe.this, MainFragmentHub.class);

                startActivity(intent);
            }
        });
    }

    // Build the About Me text from the text file in the raw folder
    private void buildText() {
        is = getResources().openRawResource(R.raw.about_me);
        isr = new InputStreamReader(is);
        br = new BufferedReader(isr);
        String st, all = "";

        try {
            while ((st = br.readLine()) != null)
                all += st + "\n";
            br.close();
        } catch (IOException e) {
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
        }
        tvAbout.setText(all);
    }
}