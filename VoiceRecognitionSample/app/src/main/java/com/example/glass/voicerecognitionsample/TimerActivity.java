package com.example.glass.voicerecognitionsample;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.EditText;
import android.widget.ImageView;
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
    private long miliisUntilFinished = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        glassGestureDetector = new GlassGestureDetector(this, this);

        setContentView(R.layout.activity_timer);

        counttimer = findViewById(R.id.counttimer);

        //startStop();

        //updateTimer();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            final List<String> results = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            if (results != null && results.size() > 0 && !results.get(0).isEmpty()) {
                if(results.get(0).equals("1 minute"))
                {
                    String conversionTime = "000100";
                    countDown(conversionTime);
                }
                else if (results.get(0).equals("5 minutes"))
                {
                    String conversionTime = "000500";
                    countDown(conversionTime);
                }
                else if (results.get(0).equals("10 minutes"))
                {
                    String conversionTime = "001000";
                    countDown(conversionTime);


//                    countDownTimer = new CountDownTimer(600000, 1000) {
//                        @Override
//                        public void onTick(long miliisUntilFinished) {
//                            counttimer.setText("seconds remaining: " + miliisUntilFinished / 1000);
//                            updateTimer();
//                        }
//
//                        @Override
//                        public void onFinish() {
//                            counttimer.setText("done!");
//
//                        }
//                    }.start();
                }
                else if (results.get(0).equals("30 seconds"))
                {
                    String conversionTime = "000030";
                    countDown(conversionTime);
                }
                else if(results.get(0).equals("10 seconds"))
                {
                    String conversionTime = "000010";
                    countDown(conversionTime);
                }

            }
        }
        else{
            finish();
        }
    }

    public void countDown(String time)
    {
        // 1000단위 = 1초
        // 60000 단위 = 1분
        // 60000 * 3600 = 1시간

        long conversionTime = 0;

        String getHour = time.substring(0,2);
        String getMin = time.substring(2,4);
        String getSecond = time.substring(4,6);

        if(getHour.substring(0,1) == "0")
        {
            getHour = getHour.substring(1,2);
        }

        if(getMin.substring(0,1) == "0")
        {
            getMin = getMin.substring(1,2);
        }

        if(getSecond.substring(0,1) == "0")
        {
            getSecond = getSecond.substring(1,2);
        }

        conversionTime = Long.valueOf(getHour) * 1000 * 3600 + Long.valueOf(getMin) * 60 * 1000 + Long.valueOf(getSecond) * 1000;

        CountDownTimer countDownTimer = new CountDownTimer(conversionTime, 1000)
        {
            public void onTick(long millisUntilFinished)
            {
                String hour = String.valueOf(millisUntilFinished / (60 * 60 * 1000));

                long getMin = millisUntilFinished - (millisUntilFinished / (60 * 60 * 1000));
                String min = String.valueOf(getMin / (60 * 1000));

                String second = String.valueOf((getMin % (60 * 1000)) / 1000);

                String millis = String.valueOf((getMin % (60 * 1000)) & 1000);

                if(hour.length() == 1)
                {
                    hour = "0" + hour;
                }

                if(min.length() == 1)
                {
                    min = "0" + min;
                }

                if(second.length() == 1)
                {
                    second = "0" + second;
                }

                counttimer.setText(hour + ":" + min + ":" + second);
            }

            public void onFinish()
            {
                counttimer.setText("done!");
            }

        }.start();
    }

//    public void startStop()
//    {
//        if (timerRunning)
//        {
//            stopTimer();
//        }
//        else
//        {
//            startTimer();
//        }
//    }

//    public void startTimer() {
//        countDownTimer = new CountDownTimer(30000, 1000) {
//            @Override
//            public void onTick(long miliisUntilFinished) {
//                counttimer.setText((int) miliisUntilFinished / 1000);
//                updateTimer();
//            }
//
//            @Override
//            public void onFinish() {
//
//            }
//        }.start();
//
//        timerRunning = true;
//    }

//    void stopTimer() {
//        countDownTimer.cancel();
//        timerRunning = false;
//    }
//
//    void updateTimer()
//    {
//        int minutes = (int) miliisUntilFinished / 60000;
//        int seconds = (int) miliisUntilFinished % 60000 / 1000;
//
//        String timeLeftText;
//
//        timeLeftText = "" + minutes;
//        timeLeftText += ":";
//        if (seconds <10) timeLeftText += "0";
//        timeLeftText += seconds;
//
//        counttimer.setText(timeLeftText);
//    }

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
            //멈추는 부분 작동 X
                countDownTimer.cancel();
            default:
                return true;
        }
    }
    private void requestVoiceRecognition() {
        final String[] keywords = {"1 minute", "5 minute", "10 minute", "30 seconds", "10 seconds"};

        final Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra("recognition-phrases", keywords);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        startActivityForResult(intent, REQUEST_CODE);
    }


}


