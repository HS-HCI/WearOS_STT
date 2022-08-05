package com.example.glass.voicerecognitionsample;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.glass.ui.GlassGestureDetector;

import android.os.Environment;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

public class VoiceMemoActivity extends AppCompatActivity
        implements GlassGestureDetector.OnGestureListener {
    private static final int REQUEST_CODE = 999;

    private GlassGestureDetector glassGestureDetector;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo);

        glassGestureDetector = new GlassGestureDetector(this, this);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, 200);

        String[] permissions = {
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };
        checkPermission(permissions);

    }
    public void checkPermission(String[] permissions)
    {
        ArrayList<String> targetList = new ArrayList<String>();

        for(int i=0; i< permissions.length; i++)
        {
            String curPermission = permissions[i];
            int permissionCheck = ContextCompat.checkSelfPermission(this, curPermission);
            if(permissionCheck == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, curPermission + "권한있음", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(this, curPermission + "권한 없음", Toast.LENGTH_SHORT).show();
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, curPermission)) {
                    Toast.makeText(this, curPermission + "권한 설명 필요", Toast.LENGTH_SHORT).show();
                } else
                {
                    targetList.add(curPermission);
                }
            }
        }
        String[] targets = new String[targetList.size()];
        targetList.toArray(targets);

        ActivityCompat.requestPermissions(this, targets, 200);
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode)
        {
            case 200:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    Toast.makeText(this, "권한을 승인함", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(this, "승인 거부됨", Toast.LENGTH_SHORT).show();

                }
                return;
        }
    }


    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        ArrayList<String> memoList = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
        String result = memoList.get(0);
        Toast.makeText(VoiceMemoActivity.this, result, Toast.LENGTH_LONG).show();

        Uri audioUri = data.getData();

        ContentResolver contentResolver = getContentResolver();
        InputStream inputStream = null;
        OutputStream outputStream = null;

        try {
            inputStream = contentResolver.openInputStream(audioUri);
            outputStream = null;

            File targetFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/test");
            if(!targetFile.exists())
            {
                targetFile.mkdir();
            }
            String fileName = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + ".amr";
            outputStream = new FileOutputStream(targetFile + "/" + fileName);

            int read = 0;
            byte[] bytes = new byte[1024];

            while((read = inputStream.read(bytes)) != -1);
            {
                outputStream.write(bytes, 0, read);
            }
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        finally {
        if(inputStream != null)
        {
            try {
                inputStream.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        if (outputStream != null)
        {
            try {
                outputStream.close();
            }
            catch (IOException e){
                e.printStackTrace();
            }
        }
        }

       }

//    public void recordAudio()
//    {
//        recorder = new MediaRecorder();
//
//        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
//        recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
//        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
//
//        recorder.setOutputFile(filename);
//
//        try
//        {
//            recorder.prepare();
//        } catch (Exception e)
//        {
//            e.printStackTrace();
//        }
//        recorder.start();
//        Toast.makeText(getApplicationContext(),"녹음시작",Toast.LENGTH_SHORT).show();
//    }
//
//    public void stopRecording()
//    {
//        if(recorder != null)
//        {
//            recorder.stop();
//            recorder.release();
//            recorder = null;
//
//            Toast.makeText(getApplicationContext(),"녹음중지",Toast.LENGTH_LONG).show();
//        }
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
                //recordAudio();
                return true;
            case SWIPE_DOWN:
                //stopRecording();
                finish();
                return true;
            default:
                return true;
        }
    }
    private void requestVoiceRecognition() {
        Log.i("speak", "call");

        final Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getPackageName());
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra("android.speech.extra.GET_AUDIO_FORMAT", "audio/AMR");
        intent.putExtra("android.speech.GET_AUDIO", true);

        startActivityForResult(intent, REQUEST_CODE);
    }
}
