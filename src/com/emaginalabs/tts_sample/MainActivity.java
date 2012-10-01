package com.emaginalabs.tts_sample;

import java.util.Locale;

import android.app.Activity;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

public class MainActivity extends Activity implements
        TextToSpeech.OnInitListener {

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    /** Called when the activity is first created. */

    private TextToSpeech tts;
    private Button btnSpeak;
    private EditText txtText;
    private RadioGroup radioGroup;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tts = new TextToSpeech(this, this);
        btnSpeak = (Button) findViewById(R.id.speak);
        radioGroup = (RadioGroup) findViewById(R.id.language_selector);
        txtText = (EditText) findViewById(R.id.textToSpeak);

        // button on click event
        btnSpeak.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                speakOut();
            }

        });
    }

    @Override
    public void onDestroy() {
        // Don't forget to shutdown tts!
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onDestroy();
    }

    @Override
    public void onInit(int status) {

        if (status == TextToSpeech.SUCCESS) {
            speakOut();
        } else {
            Log.e("TTS", "Initilization Failed!");
        }

    }

    private void speakOut() {
        selectLanguage();
        String text = txtText.getText().toString();
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }

    private void selectLanguage() {
        Locale locale = getSelectedLanguage();
        int result = tts.setLanguage(locale);
        if (result == TextToSpeech.LANG_MISSING_DATA
                || result == TextToSpeech.LANG_NOT_SUPPORTED) {
            Log.e("TTS", "This Language is not supported");
        }
    }

    private Locale getSelectedLanguage() {
        Locale locale = new Locale("en");
        switch (radioGroup.getCheckedRadioButtonId()) {
        case R.id.language_en:
            locale = Locale.US;
            break;
        case R.id.language_es:
            locale = new Locale("es");
            break;
        }
        return locale;


    }
}
