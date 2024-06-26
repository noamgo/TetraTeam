package com.example.tetrateam;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

// Base class for menu in most activities (to be able to write only once the menu function without repeating it)
public class BaseMenuActivity extends AppCompatActivity {

    // variables
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("RestrictedApi")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        if (menu instanceof MenuBuilder) {
            MenuBuilder mb = (MenuBuilder) menu;
            mb.setOptionalIconsVisible(true);
        }
        MenuItem menuItem = menu.findItem(R.id.rules);
        return true;
    }

    // make sure that the menu wont show the activity the user is already in
    public boolean onPrepareOptionsMenu(Menu menu) {
        // Hide the "About Me" option if the user is in the "About Me" activity
        MenuItem aboutMenuItem = menu.findItem(R.id.about);
        aboutMenuItem.setVisible(!getClass().getSimpleName().equals("AboutMe"));

        MenuItem menuMenuItem = menu.findItem(R.id.Menu);
        menuMenuItem.setVisible(!getClass().getSimpleName().equals("GameMenuActivity"));

        MenuItem signOutMenuItem = menu.findItem(R.id.signOut);
        signOutMenuItem.setVisible(FirebaseManager.isSignedIn());

        return super.onPrepareOptionsMenu(menu);
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
            if (FirebaseManager.isSignedIn())
                intent = new Intent(this, GameMenuActivity.class);
            else
                intent = new Intent(this, MainActivity.class);

            startActivity(intent);
        }

        // Sign out menu item asks the user if they want to sign out before signing out
        if (id == R.id.signOut) {
            new AlertDialog.Builder(this).setTitle("Exit").
                    setMessage("Are you sure you want to sign out?").
                    setNeutralButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    }).setNegativeButton("Yes", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            intent = new Intent(BaseMenuActivity.this, MainActivity.class);
                            FirebaseManager.signOut();
                            startActivity(intent);
                        }
                    }).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}