package com.example.ricedoc;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class description_brownspot extends AppCompatActivity {

    TextView nameDisease;
    TextView confidencelevel;
    ImageView pictureDisease;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.description_brownspot);

        String result = getIntent().getStringExtra("text");
        nameDisease = findViewById(R.id.textView);
        nameDisease.setText(result);

        // Receive the image data from the Intent
        byte[] byteArray = getIntent().getByteArrayExtra("imageByteArray");
        Bitmap receivedBitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);

        pictureDisease = findViewById(R.id.imageView3);
        pictureDisease.setImageBitmap(receivedBitmap);

        String conPercentage = getIntent().getStringExtra("confident_key");
        confidencelevel = findViewById(R.id.confidencelevel);
        confidencelevel.setText(conPercentage);

    }
    @Override
    public void onBackPressed(){
        nameDisease.setText("");
        confidencelevel.setText("");
        super.onBackPressed();
    }
}
