package com.example.glass.voicerecognitionsample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.VideoView;
import com.example.glass.ui.GlassGestureDetector;

import java.io.IOException;

public class MediaActivity2 extends AppCompatActivity implements
        GlassGestureDetector.OnGestureListener {

    private static final int REQUEST_CODE = 999;
    private MediaPlayer mediaPlayer;
    private SurfaceHolder holder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media);

        SurfaceView surfaceView = findViewById(R.id.surfaceView);
        final ProgressBar progressBar = findViewById(R.id.progressBar);

        mediaPlayer = new MediaPlayer();

        try{
            Uri uri = Uri.parse("android.resource://" + getPackageName() + "/raw/shoulder");
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
    @Override
    public boolean onGesture(GlassGestureDetector.Gesture gesture) {
        switch (gesture) {
            case TAP:
                requestVoiceRecognition();
                return true;
            case SWIPE_DOWN:
                finish();
                return true;
            default:
                return false;
        }
    }

    private void requestVoiceRecognition()
    {
        final Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        final String[] keywords = {"one", "image", "Google", "battery", "Hi"};
        intent.putExtra("recognition-phrases", keywords);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        startActivityForResult(intent, REQUEST_CODE);
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
    }
}


