package com.example.glass.voicerecognitionsample;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.MotionEvent;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.glass.ui.GlassGestureDetector;

public class TimerActivity extends AppCompatActivity
        implements GlassGestureDetector.OnGestureListener {

    private TextView counttimer;
    private GlassGestureDetector glassGestureDetector;
    private CountDownTimer countDownTimer;

    FrameLayout timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        counttimer = findViewById(R.id.counttimer);

    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return glassGestureDetector.onTouchEvent(ev) || super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onGesture(GlassGestureDetector.Gesture gesture) {
        switch (gesture) {
            case TAP:
                return true;
            case SWIPE_DOWN:
                finish();
                return true;
            case SWIPE_BACKWARD:
            default:
                return false;
        }
    }


}


