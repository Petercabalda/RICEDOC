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

private CardView Card1, Card2, Card3, Card4, Card5, Card6, Card7;

    private BottomNavigationView bottomNavigationView;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

        Card1 = (CardView) findViewById(R.id.card1);
        Card1.setOnClickListener((View.OnClickListener)this);

        Card2 = (CardView) findViewById(R.id.card2);
        Card2.setOnClickListener((View.OnClickListener)this);

        Card3 = (CardView) findViewById(R.id.card3);
        Card3.setOnClickListener((View.OnClickListener)this);

        Card4 = (CardView) findViewById(R.id.card4);
        Card4.setOnClickListener((View.OnClickListener)this);

        Card5 = (CardView) findViewById(R.id.card5);
        Card5.setOnClickListener((View.OnClickListener)this);

        Card6 = (CardView) findViewById(R.id.card6);
        Card6.setOnClickListener((View.OnClickListener)this);

        Card7 = (CardView) findViewById(R.id.card7);
        Card7.setOnClickListener((View.OnClickListener)this);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {


            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.About:
                        Intent aboutIntent = new Intent(guideActivity.this, aboutUsActivity.class);
                        aboutIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(aboutIntent);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        return true;
                    case R.id.Main:
                        Intent mainIntent = new Intent(guideActivity.this, mainpageActivity.class);
                        mainIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(mainIntent);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
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
          case R.id.card2:
              i = new Intent(this,Card2.class);
              startActivity(i);
              break;
          case R.id.card3:
              i = new Intent(this,Card3.class);
              startActivity(i);
              break;
          case R.id.card4:
              i = new Intent(this,Card4.class);
              startActivity(i);
              break;
          case R.id.card5:
              i = new Intent(this,Card5.class);
              startActivity(i);
              break;
          case R.id.card6:
              i = new Intent(this,Card6.class);
              startActivity(i);
              break;
          case R.id.card7:
              i = new Intent(this,Card7.class);
              startActivity(i);
              break;
      }

    }
}