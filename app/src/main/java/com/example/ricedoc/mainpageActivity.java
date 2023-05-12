package com.example.ricedoc;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.io.IOException;

public class mainpageActivity extends AppCompatActivity {

    private Bitmap bitmap;
    private ImageView imageView;
    private CardView captureBtn, galleryBtn;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_mainpage);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.About:
                        Intent aboutUsIntent = new Intent(mainpageActivity.this, aboutUsActivity.class);
                        aboutUsIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(aboutUsIntent);
                        return true;
                    case R.id.Main:
                        return true;
                    case R.id.Guide:
                        Intent guideIntent = new Intent(mainpageActivity.this, guideActivity.class);
                        guideIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(guideIntent);
                        return true;
                    default:
                        return false;
                }
            }
        });


        getPermission();
        captureBtn = (CardView) findViewById(R.id.captureBtn);
        galleryBtn = (CardView) findViewById(R.id.galleryBtn);
        /*predictBtn = (ImageButton) findViewById(R.id.predictBtn);*/
        imageView = findViewById(R.id.imageView);


      /*  predictBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bitmap != null) {
                    Intent intent = new Intent(mainpageActivity.this, loadingscreenActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(mainpageActivity.this, "Please select an image first", Toast.LENGTH_SHORT).show();
                }
            }
        });*/

        galleryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 10);
            }
        });

        captureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 12);
            }
        });

    }

    void getPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(mainpageActivity.this, new String[]{Manifest.permission.CAMERA}, 11);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 11) {
            if (grantResults.length > 0) {
                if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    this.getPermission();
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 10) {
            if (data != null) {
                Uri uri = data.getData();
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                    imageView.setImageBitmap(bitmap);
                    navigateToNextActivity(); // Navigate to the next activity after selecting an image
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else if (requestCode == 12) {
            if (data != null && data.getExtras() != null) {
                bitmap = (Bitmap) data.getExtras().get("data");
                imageView.setImageBitmap(bitmap);
                navigateToNextActivity(); // Navigate to the next activity after capturing an image
            } else {
                // handle case where user cancelled image capture
                Toast.makeText(this, "Image capture cancelled", Toast.LENGTH_SHORT).show();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void navigateToNextActivity() {
        // Add code to navigate to the next activity here
        Intent intent = new Intent(mainpageActivity.this, loadingscreenActivity.class);
        startActivity(intent);
    }
}