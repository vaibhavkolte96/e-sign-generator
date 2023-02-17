package com.vasu.esign;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import com.vasu.esign.databinding.ActivityHomeBinding;


public class HomeActivity extends AppCompatActivity {

    private static final String TAG = HomeActivity.class.getName();
    private ActivityHomeBinding binding;
    private Uri imageUri;
    private static final int CAMERA = 10;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
//        setContentView(R.layout.activity_home);

        binding.btnGetSign.setOnClickListener(v -> {
            startActivity(new Intent(this,MainActivity.class));
        });

        binding.btnCaptureImage.setOnClickListener(v -> {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            Uri imagePath = createImage();
            intent.putExtra(MediaStore.EXTRA_OUTPUT,imagePath);
            startActivityForResult(intent,CAMERA);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CAMERA){
            if(resultCode == RESULT_OK){
                Toast.makeText(this, "Image Captured", Toast.LENGTH_SHORT).show();
                Log.e(TAG, "onActivityResult: Image URI : "+imageUri );
                binding.imgCapture.setImageURI(imageUri);
            }
        }
    }

    private Uri createImage(){
        Uri uri;
        ContentResolver resolver = getContentResolver();
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
            uri = MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY);
        }else{
            uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        }
        String imgName = String.valueOf(System.currentTimeMillis());
        ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.Images.Media.DISPLAY_NAME,imgName+".jpg");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            contentValues.put(MediaStore.Images.Media.RELATIVE_PATH,"Pictures/"+"My Images/");
        }
        imageUri = resolver.insert(uri,contentValues);
        return uri;
    }
}