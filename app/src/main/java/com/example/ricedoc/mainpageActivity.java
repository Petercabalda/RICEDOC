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
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.service.controls.templates.ThumbnailTemplate;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;
import java.io.ByteArrayOutputStream;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import android.graphics.Color;

import org.tensorflow.lite.DataType;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import com.example.ricedoc.ml.Mobilenetmodel;

public class mainpageActivity extends AppCompatActivity {

    private Bitmap bitmap;
    private CardView captureBtn, galleryBtn;

    BottomNavigationView bottomNavigationView;
    int imagesize = 224;

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
        captureBtn = findViewById(R.id.captureBtn);
        galleryBtn = findViewById(R.id.galleryBtn);

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


//    private Bitmap preprocessImage(Bitmap originalBitmap) {
//        // Resize the original bitmap to the desired size
//        Bitmap resizedBitmap = Bitmap.createScaledBitmap(originalBitmap, imagesize, imagesize, false);
//
//        // Create a new bitmap for preprocessing
//        Bitmap preprocessedBitmap = resizedBitmap.copy(Bitmap.Config.ARGB_8888, true);
//
//        int width = preprocessedBitmap.getWidth();
//        int height = preprocessedBitmap.getHeight();
//        int[] pixels = new int[width * height];
//        preprocessedBitmap.getPixels(pixels, 0, width, 0, 0, width, height);
//        for (int i = 0; i < pixels.length; i++) {
//            int color = pixels[i];
//            int red = (color >> 16) & 0xFF;
//            int green = (color >> 8) & 0xFF;
//            int blue = color & 0xFF;
//
//            // Multiply each channel by 255
//            red = red * 255 / 255; // In this case, multiplying by 255 does not change the value
//            green = green * 255;
//            blue = blue * 255;
//
//            pixels[i] = Color.rgb(red, green, blue);
//        }
//        preprocessedBitmap.setPixels(pixels, 0, width, 0, 0, width, height);
//
//        return preprocessedBitmap;
//    }

    public void classifyImage(Bitmap resizedBitmap){
        try {
            Mobilenetmodel model = Mobilenetmodel.newInstance(getApplication());

            // Creates inputs for reference.
            TensorBuffer inputFeature0 = TensorBuffer.createFixedSize(new int[]{1, 224, 224, 3}, DataType.FLOAT32);
            ByteBuffer byteBuffer = ByteBuffer.allocateDirect(4 * imagesize * imagesize * 3);

            int[] intValues = new int[imagesize * imagesize];
            resizedBitmap.getPixels(intValues, 0, resizedBitmap.getWidth(), 0, 0, resizedBitmap.getWidth(), resizedBitmap.getHeight());
            int pixel = 0;
            //Iterate over each pixel and extract R, G and B values. Add these values individually to the byte buffer.
            for(int i = 0; i < imagesize; i++){
                for(int j = 0; j < imagesize;  j++){
                    int val = intValues[pixel++]; //RGB
                    byteBuffer.putFloat(((val >> 16) & 0xFF) * (1.f / 255.f));
                    byteBuffer.putFloat(((val >> 8) & 0xFF) * (1.f / 255.f));
                    byteBuffer.putFloat((val & 0xFF) * (1.f / 255.f));
                }
            }

            inputFeature0.loadBuffer(byteBuffer);

            // Runs model inference and gets result.
            Mobilenetmodel.Outputs outputs = model.process(inputFeature0);
            TensorBuffer outputFeature0 = outputs.getOutputFeature0AsTensorBuffer();

            float[] confidences = outputFeature0.getFloatArray();
            //find the index of the class with the biggest confidence.
            int maxPos = 0;
            float maxConfidence = 0;
            for (int i = 0; i < confidences.length; i++) {
                if (confidences[i] > maxConfidence) {
                    maxConfidence = confidences[i];
                    maxPos = i;
                }
            }
            String[] classes = {"Bacterial Leaf Blight", "Brown Spot", "Healthy", "Leaf Blast"};
            String result = classes[maxPos];
            String conPercentage = String.format("%.2f%%", maxConfidence * 100);
            navigateToNextActivityWithBitmap(resizedBitmap, result, conPercentage);
            model.close();
        } catch (IOException e) {
            // TODO Handle the exception
        }

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 10) {
            if (data != null) {
                Uri uri = data.getData();

                try {
                    Bitmap originalBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                    int dimension = Math.min(originalBitmap.getWidth(), originalBitmap.getHeight());
                    Bitmap thumbnailBitmap = ThumbnailUtils.extractThumbnail(originalBitmap, dimension, dimension);
                    Bitmap resizedBitmap = originalBitmap.createScaledBitmap(originalBitmap, imagesize, imagesize, false);

                    classifyImage(resizedBitmap);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else if (requestCode == 12) {
            if (data != null && data.getExtras() != null) {
                Bitmap originalBitmap = (Bitmap) data.getExtras().get("data");
                int dimension = Math.min(originalBitmap.getWidth(), originalBitmap.getHeight());
                Bitmap thumbnailBitmap = ThumbnailUtils.extractThumbnail(originalBitmap, dimension, dimension);
                Bitmap resizedBitmap = originalBitmap.createScaledBitmap(originalBitmap, imagesize, imagesize, false);

                classifyImage(resizedBitmap);
            } else {
                // handle case where user cancelled image capture
                Toast.makeText(this, "Image capture cancelled", Toast.LENGTH_SHORT).show();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void navigateToNextActivityWithBitmap(Bitmap resizedBitmap, String result, String conPercentage) {

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        resizedBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();

        Intent intent = new Intent(this, description_brownspot.class);
        intent.putExtra("imageByteArray", byteArray);
        intent.putExtra("text", result);
        intent.putExtra("confident_key", conPercentage);
        startActivity(intent);
    }
}