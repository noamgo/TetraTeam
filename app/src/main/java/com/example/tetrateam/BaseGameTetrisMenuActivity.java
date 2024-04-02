package com.example.tetrateam;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;

// Base class for menu in the Tetris game (to use toggle music feature easily only in the tetris game activity)
public class BaseGameTetrisMenuActivity extends AppCompatActivity {

    // variables
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("RestrictedApi")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.tetris_game_menu, menu);

        if (menu instanceof MenuBuilder) {
            MenuBuilder mb = (MenuBuilder) menu;
            mb.setOptionalIconsVisible(true);
        }
        MenuItem menuItem = menu.findItem(R.id.rules);
        return true;
    }

    // define all the menu items in the menu
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.rules) {
            String url = "https://docs.google.com/document/d/1Cc8r7-TKiNB5eVwPfK9A97U3VVGC874jQe7UAKzIRqw/edit?usp=sharing";
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(intent);
            return true;
        }

        if (id == R.id.about) {
            intent = new Intent(this, AboutMe.class);
            startActivity(intent);
            return true;
        }

        if (id == R.id.Menu) {
            intent = new Intent(this, GameMenuActivity.class);
            startActivity(intent);
            return true;
        }

        // Sign out menu item asks the user if they want to sign out before signing out
        if (id == R.id.signOut) {
            new AlertDialog.Builder(this).setTitle("Sign out").
                    setMessage("Are you sure you want to sign out?").
                    setNeutralButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    }).setNegativeButton("Yes", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            intent = new Intent(BaseGameTetrisMenuActivity.this, MainActivity.class);
                            FirebaseManager.signOut();
                            startActivity(intent);
                        }
                    }).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}