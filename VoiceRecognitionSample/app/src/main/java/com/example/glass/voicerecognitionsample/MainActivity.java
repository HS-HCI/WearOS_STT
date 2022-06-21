/*
 * Copyright 2020 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.glass.voicerecognitionsample;

import android.Manifest;
import android.app.SearchManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.glass.ui.GlassGestureDetector;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements
    GlassGestureDetector.OnGestureListener {

  private static final int REQUEST_CODE = 999;
  private static final int FEATURE_VOICE_COMMANDS = 14;
  private static final String TAG = MainActivity.class.getSimpleName();
  private static final String DELIMITER = "\n";

  private TextView resultTextView;
  private GlassGestureDetector glassGestureDetector;
  private List<String> mVoiceResults = new ArrayList<>(4);

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    getWindow().requestFeature(FEATURE_VOICE_COMMANDS);
    setContentView(R.layout.activity_main);
    resultTextView = findViewById(R.id.results);
    glassGestureDetector = new GlassGestureDetector(this, this);

    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, 200);
  }
  @Override
  public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
    if (requestCode == 200) {
      for (int result : grantResults) {
        if (result != PackageManager.PERMISSION_GRANTED) {
          Log.d(TAG, "Permission denied. Voice commands menu is disabled.");
        }
      }
    } else {
      super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
  }

  /*
  @Override
  public boolean onCreatePanelMenu(int featureId, @NonNull Menu menu) {
    getMenuInflater().inflate(R.menu.menu, menu);
    return true;
  }


  @Override
  public boolean onContextItemSelected(@NonNull MenuItem item) {
    switch (item.getItemId()) {
      // Handle selected menu item
      case R.id.menu1:
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivity(intent);
        // Handle menu1 action
        break;
      case R.id.menu2:
        Intent intent2 = new Intent(Intent.ACTION_VIEW, Uri.parse("content://media/internal/images/media"));
        startActivity(intent2);
        break;
      case R.id.menu3:
        Uri uri = Uri.parse("https://www.google.com");
        Intent intent3 = new Intent(Intent.ACTION_VIEW,uri);
        startActivity(intent3);
        break;
      case R.id.menu4:
        Intent intent4 = new Intent(Intent.ACTION_BATTERY_CHANGED);
        startActivity(intent4);
        break;
      case R.id.menu5:
        Intent intent5 = new Intent(Intent.ACTION_WEB_SEARCH);
        intent5.putExtra(SearchManager.QUERY,"hci");
        startActivity(intent5);
        break;

      default:
       return super.onContextItemSelected(item);
    }
    return true;
  }
*/


  @Override
  protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

    if (resultCode == RESULT_OK) {
      final List<String> results = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
      Log.d(TAG, "results: " + results.toString());
      if (results != null && results.size() > 0 && !results.get(0).isEmpty()) {
        if(results.get(0).equals("one"))
        {
          Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
          startActivity(intent);
        }
        else if (results.get(0).equals("back"))
        {
          Intent intent2 = new Intent(MainActivity.this, MediaActivity.class);
          startActivity(intent2);
        }
        else if (results.get(0).equals("shoulder"))
        {
          Intent intent = new Intent(MainActivity.this, MediaActivity2.class);
          startActivity(intent);
        }
        else if (results.get(0).equals("battery"))
        {
          Intent intent = new Intent(Intent.ACTION_BATTERY_CHANGED);
          startActivity(intent);
        }
        else if(results.get(0).equals("Hi"))
        {
          /*Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
          intent.putExtra(SearchManager.QUERY,"hci");
          startActivity(intent);
          */
          Intent intent = new Intent(Intent.ACTION_BATTERY_CHANGED);
          startActivity(intent);
        }

        updateUI(results.get(0));
      }
    } else {
      Log.d(TAG, "Result not OK");
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
    final String[] keywords = {"shoulder", "back", "chest", "Abs", "lower body"};

    final Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
    intent.putExtra("recognition-phrases", keywords);
    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
    startActivityForResult(intent, REQUEST_CODE);
  }

  private void updateUI(String result) {
    if (mVoiceResults.size() >= 4) {
      mVoiceResults.remove(mVoiceResults.size() - 1);
    }
    mVoiceResults.add(0, result);
    final String recognizedText = String.join(DELIMITER, mVoiceResults);
    resultTextView.setText(recognizedText);
  }
}
