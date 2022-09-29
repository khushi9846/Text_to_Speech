package com.example.text_to_speech;

import static android.content.ContentValues.TAG;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.text_to_speech.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding activityMainBinding;
    TextToSpeech textToSpeech;

    private static final int REQUEST_CODE_SPEECH_INPUT = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(activityMainBinding.getRoot());

        final Random random = new Random();

        textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if (i != TextToSpeech.ERROR) {
                    textToSpeech.setLanguage(Locale.ENGLISH);

                } else {
                    Log.i(TAG, "onInit: some error");
                }
            }
        });

        activityMainBinding.speak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "onClick: ayaa yha tak");

                activityMainBinding.input1.setText(String.valueOf(random.nextInt(10)));
                activityMainBinding.input2.setText(String.valueOf(random.nextInt(10)));
                textToSpeech.speak(activityMainBinding.input1.getText().toString(), TextToSpeech.QUEUE_ADD, null);
                textToSpeech.speak("+", TextToSpeech.QUEUE_ADD, null);
                textToSpeech.speak(activityMainBinding.input2.getText().toString(), TextToSpeech.QUEUE_ADD, null);
            }
        });

        activityMainBinding.mic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Log.i(TAG, "onClick: ayaa yha tak");
                Intent intent
                        = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                        RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,
                        Locale.getDefault());
                intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak to set text");
                //   Log.i(TAG, "onClick: ayaa yha tak");
                try {
                    startActivityForResult(intent, REQUEST_CODE_SPEECH_INPUT);

                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, " " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        });

activityMainBinding.submit.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        int x= Integer.parseInt(activityMainBinding.input1.getText().toString());
        int y =Integer.parseInt(activityMainBinding.input2.getText().toString());
        int sum1 = Integer.parseInt((activityMainBinding.output.getText().toString()));
        int sum = x+y;
        if(sum != sum1){
            textToSpeech.speak("The answer is incorrect",TextToSpeech.QUEUE_ADD,null);

        }else {
            textToSpeech.speak("The answer is correct",TextToSpeech.QUEUE_ADD,null);
        }    }
});
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_SPEECH_INPUT) {
            if (resultCode == RESULT_OK && data != null) {
                ArrayList<String> result = data.getStringArrayListExtra(
                        RecognizerIntent.EXTRA_RESULTS);
                activityMainBinding.output.setText(
                        Objects.requireNonNull(result).get(0));
            }
        }
    }

    public void sum(View view)

    {


    }


}

