package com.example.tetrateam;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import androidx.annotation.Nullable;

// Music service
public class MusicService extends Service {

    // variables
    private static MediaPlayer mediaPlayer;

    // constructor
    public MusicService() {
    }

    public void onCreate() {
        super.onCreate();
        // set up the media player with the music (because I used only one music defined in the onCreate function)
        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.tetris_theme_music);
        mediaPlayer.setLooping(true);
    }

    // start the music
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mediaPlayer.start(); // Start playing the music
        return START_STICKY;
    }

    // stop the music when the service is destroyed
    @Override
    public void onDestroy() {
        super.onDestroy();
        mediaPlayer.stop();
        mediaPlayer.release();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
