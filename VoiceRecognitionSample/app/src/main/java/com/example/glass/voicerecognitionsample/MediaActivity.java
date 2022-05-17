package com.example.glass.voicerecognitionsample;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.VideoView;
import com.example.glass.ui.GlassGestureDetector;

public class MediaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media);
        MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.cat);
        mediaPlayer.start(); // no need to call prepare(); create() does that for you
    }
}