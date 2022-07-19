package com.example.glass.voicerecognitionsample;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.glass.ui.GlassGestureDetector;

import java.util.List;

public class TimerActivity extends AppCompatActivity
        implements GlassGestureDetector.OnGestureListener {
    private static final int REQUEST_CODE = 999;

    private TextView counttimer;
    private GlassGestureDetector glassGestureDetector;
    private CountDownTimer countDownTimer;
    private boolean timerRunning;
    private long timeLeftInMilliseconds = 600000; // 10mins


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        glassGestureDetector = new GlassGestureDetector(this, this);

        setContentView(R.layout.activity_timer);

        counttimer = findViewById(R.id.counttimer);
        startStop();

        updateTimer();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            final List<String> results = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            if (results != null && results.size() > 0 && !results.get(0).isEmpty()) {
                if(results.get(0).equals("1 minute"))
                {
                    Intent intent = new Intent(TimerActivity.this, TimerActivity.class);
                    startActivity(intent);
                }
                else if (results.get(0).equals("5 minutes"))
                {
                    Intent intent = new Intent(TimerActivity.this, TimerActivity.class);
                    startActivity(intent);
                }
                else if (results.get(0).equals("10 minutes"))
                {
                    Intent intent = new Intent(TimerActivity.this, TimerActivity.class);
                    startActivity(intent);
                }
                else if (results.get(0).equals("30 seconds"))
                {
                    Intent intent = new Intent(TimerActivity.this, TimerActivity.class);
                    startActivity(intent);
                }
                else if(results.get(0).equals("10 seconds"))
                {
                    Intent intent = new Intent(TimerActivity.this, TimerActivity.class);
                    startActivity(intent);
                }

            }
        }
        else{
            finish();
        }
    }

    public void startStop()
    {
        if (timerRunning)
        {
            stopTimer();
        }
        else
        {
            startTimer();
        }
    }

    public void startTimer() {
        countDownTimer = new CountDownTimer(timeLeftInMilliseconds, 1000) {
            @Override
            public void onTick(long l) {
                timeLeftInMilliseconds = 1;
            }

            @Override
            public void onFinish() {

            }
        }.start();

        timerRunning = true;
    }

    void stopTimer() {
        countDownTimer.cancel();
        timerRunning = false;
    }

    void updateTimer()
    {
        int minutes = (int) timeLeftInMilliseconds / 60000;
        int seconds = (int) timeLeftInMilliseconds % 60000 / 1000;

        String timeLeftText;

        timeLeftText = "" + minutes;
        timeLeftText += ":";
        if (seconds <10) timeLeftText += "0";
        timeLeftText += seconds;

        counttimer.setText(timeLeftText);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return glassGestureDetector.onTouchEvent(ev) || super.dispatchTouchEvent(ev);
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
            case SWIPE_BACKWARD:
            default:
                return false;
        }
    }
    private void requestVoiceRecognition() {
        final String[] keywords = {"1 minute", "5 minute", "10 minute", "30 seconds", "10 seconds"};

        final Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        startActivityForResult(intent, REQUEST_CODE);
    }


}


