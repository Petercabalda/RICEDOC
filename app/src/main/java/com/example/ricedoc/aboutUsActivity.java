package com.example.ricedoc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import android.view.View;


public class aboutUsActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.About:
                        /**if(!aboutUsActivity.this.getClass().equals(aboutUsActivity.class)) {
                            Intent aboutUsIntent = new Intent(aboutUsActivity.this, aboutUsActivity.class);
                            startActivity(aboutUsIntent);
                        }**/
                        return true;
                    case R.id.Main:
                        Intent mainIntent = new Intent(aboutUsActivity.this, mainpageActivity.class);
                        mainIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // Add this line
                        startActivity(mainIntent);
                        return true;
                    case R.id.Guide:
                        Intent guideIntent = new Intent(aboutUsActivity.this, guideActivity.class);
                        guideIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // Add this line
                        startActivity(guideIntent);
                        return true;
                    default:
                        return false;
                }
            }

        });
    }
}