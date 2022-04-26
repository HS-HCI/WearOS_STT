package com.example.glass.voicerecognitionsample;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ListView;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.glass.ui.GlassGestureDetector;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SecondActivity extends AppCompatActivity {
    TextView text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.okgoogle);
        text =(TextView) findViewById(R.id.textView1);
        text.setText("This app is developed by  Hyoseock " +
                " Nahyun  " +
                "Siyeon   ");
    }
}
