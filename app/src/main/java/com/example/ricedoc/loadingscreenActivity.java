package com.example.ricedoc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;

public class loadingscreenActivity extends AppCompatActivity {

    Timer timer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loadingscreen);

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent(loadingscreenActivity.this, description_brownspotActivity.class);
                startActivity(intent);
                finish();
            }
        },2000);
    }
}