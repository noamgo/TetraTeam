package com.example.tetrateam;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class StatsActivity extends BaseMenuActivity {

    TextView tvStats;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        tvStats = findViewById(R.id.tvStats);
        tvStats.setText("username: " + FirebaseManager.getCurrentUserDisplayName() + "\n" );
    }
}