package com.example.glass.voicerecognitionsample;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.VideoView;
import com.example.glass.ui.GlassGestureDetector;

import java.io.IOException;

public class MediaActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;
    private SurfaceHolder holder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media);

        final VideoView videoView = (VideoView)findViewById(R.id.videoView);

        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/raw/cat");
        videoView.setVideoURI(uri);

        final MediaController mediaController = new MediaController(this);
        videoView.setMediaController(mediaController);

        videoView.postDelayed(new Runnable() {
            @Override
            public void run() {
                mediaController.show(0);
                videoView.start();
            }
        }, 100);
/*
        SurfaceView surfaceView = findViewById(R.id.surfaceView);
        final ProgressBar progressBar = findViewById(R.id.progressBar);

        mediaPlayer = new MediaPlayer();

        try{
            Uri uri = Uri.parse("android.resource://" + getPackageName() + "/raw/cat");
            mediaPlayer.setDataSource(this, uri);
            holder=surfaceView.getHolder();
            holder.addCallback(new MyCallBack());
            mediaPlayer.prepare();
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    progressBar.setVisibility(View.INVISIBLE);
                    mediaPlayer.start();
                    mediaPlayer.setLooping(true);
                }
            });
        }
        catch (IOException e) {}

    }
    private class MyCallBack implements SurfaceHolder.Callback {
        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            mediaPlayer.setDisplay(holder);
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {

        }

 */
    }
}


