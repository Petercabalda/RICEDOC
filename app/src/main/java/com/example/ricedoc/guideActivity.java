package com.example.ricedoc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class guideActivity extends AppCompatActivity implements View.OnClickListener {

private CardView Card1;

    private BottomNavigationView bottomNavigationView;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_guide);

        Card1 = (CardView) findViewById(R.id.card1);
        Card1.setOnClickListener((View.OnClickListener)this);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {


            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.About:
                        Intent aboutIntent = new Intent(guideActivity.this, aboutUsActivity.class);
                        aboutIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(aboutIntent);
                        return true;
                    case R.id.Main:
                        Intent mainIntent = new Intent(guideActivity.this, mainpageActivity.class);
                        mainIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(mainIntent);
                        return true;
                    case R.id.Guide:
                        return true;
                    default:
                        return false;
                }
            }

        });



    }

    @Override
    public void onClick(View v) {
      Intent i;

      switch (v.getId()){
          case R.id.card1:
              i = new Intent(this,Card1.class);
              startActivity(i);
              break;
      }
    }
}