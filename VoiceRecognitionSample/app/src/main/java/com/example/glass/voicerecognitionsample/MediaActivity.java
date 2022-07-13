package com.example.glass.voicerecognitionsample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.VideoView;
import com.example.glass.ui.GlassGestureDetector;

import java.io.IOException;

public class MediaActivity extends AppCompatActivity implements
        GlassGestureDetector.OnGestureListener {


    private static final int REQUEST_CODE = 999;
    private MediaPlayer mediaPlayer;
    private SurfaceHolder holder;
    private GlassGestureDetector glassGestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media);
        Intent intent = getIntent();

        String part = intent.getStringExtra("part");
        //System.out.println(back);


        glassGestureDetector = new GlassGestureDetector(this, this);

        SurfaceView surfaceView = findViewById(R.id.surfaceView);
        final ProgressBar progressBar = findViewById(R.id.progressBar);

        mediaPlayer = new MediaPlayer();

        // shoulder
        if (part.equals("shoulder") == true)
        {
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
        // back
        if (part.equals("back") == true)
        {
            try{
                Uri uri = Uri.parse("android.resource://" + getPackageName() + "/raw/back");
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
        // chest
        if (part.equals("chest") == true)
        {
            try{
                Uri uri = Uri.parse("android.resource://" + getPackageName() + "/raw/chest");
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
        // abs
        if (part.equals("abs") == true)
        {
            try{
                Uri uri = Uri.parse("android.resource://" + getPackageName() + "/raw/abs");
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
        // lower body
        if (part.equals("lower body") == true)
        {
            try{
                Uri uri = Uri.parse("android.resource://" + getPackageName() + "/raw/lower_body");
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

    }
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return glassGestureDetector.onTouchEvent(ev) || super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onGesture(GlassGestureDetector.Gesture gesture) {
        switch (gesture) {
            case TAP:
                mediaPlayer.start();
                return true;
            case SWIPE_DOWN:
                finish();
                return true;
            case SWIPE_BACKWARD:
                mediaPlayer.pause();
                return true;
            case TWO_FINGER_TAP:
                Timer();
                return true;
            default:
                return false;
        }
    }

    private void requestVoiceRecognition()
    {
        final String[] keywords = {"shoulder", "back", "chest", "abs", "lower body"};

        final Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra("recognition-phrases", keywords);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        startActivityForResult(intent, REQUEST_CODE);
    }

    private void Timer()
    {
        Intent intent = new Intent(MediaActivity.this,TimerActivity.class);
        startActivity(intent);
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


