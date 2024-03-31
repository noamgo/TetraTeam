package com.example.tetrateam;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class AboutMe extends BaseMenuActivity {

    Intent intent;
    InputStream is;
    InputStreamReader isr;
    BufferedReader br;
    TextView tvAbout;
    Button btnBack;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_me);

        mAuth = FirebaseAuth.getInstance();

        tvAbout = findViewById(R.id.tvAbout);
        buildText();

        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(FirebaseManager.isSignedIn())
                    intent = new Intent(AboutMe.this, GameMenuActivity.class);
                else
                    intent = new Intent(AboutMe.this, MainFragmentHub.class);

                startActivity(intent);
            }
        });
    }

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