package com.example.tetrateam;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
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

public class AboutMe extends AppCompatActivity{

    Intent intent;
    InputStream is;
    InputStreamReader isr;
    BufferedReader br;
    TextView tvAbout;
    Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_me);

        tvAbout = findViewById(R.id.tvAbout);
        buildText();

        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                intent = new Intent(AboutMe.this, MainActivity.class);
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