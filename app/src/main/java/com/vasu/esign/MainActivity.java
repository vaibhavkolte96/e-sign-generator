package com.vasu.esign;

import androidx.appcompat.app.AppCompatActivity;

import android.gesture.GestureOverlayView;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.MotionEvent;
import android.widget.Toast;

import com.vasu.esign.databinding.ActivityMainBinding;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private String path;
    private File file;
    private Bitmap bitmap;
    public boolean gestureTouch = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        path = Environment.getExternalStorageDirectory() + "/signature.png";
        file = new File(path);
        file.delete();
        binding.signaturePad.setDrawingCacheEnabled(true);

        binding.signaturePad.setAlwaysDrawnWithCacheEnabled(true);
        binding.signaturePad.setHapticFeedbackEnabled(false);
        binding.signaturePad.cancelLongPress();
        binding.signaturePad.cancelClearAnimation();
        binding.signaturePad.addOnGestureListener(new GestureOverlayView.OnGestureListener() {

            @Override
            public void onGesture(GestureOverlayView arg0, MotionEvent arg1) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onGestureCancelled(GestureOverlayView arg0,
                                           MotionEvent arg1) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onGestureEnded(GestureOverlayView arg0, MotionEvent arg1) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onGestureStarted(GestureOverlayView arg0,
                                         MotionEvent arg1) {
                // TODO Auto-generated method stub
                gestureTouch = arg1.getAction() != MotionEvent.ACTION_MOVE;
            }
        });

        binding.doneButton.setOnClickListener(v -> {
            // TODO Auto-generated method stub
            try {
                bitmap = Bitmap.createBitmap(binding.signaturePad.getDrawingCache());
                MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, " yourTitle ", " yourDescription");
            } catch (Exception e) {
                e.printStackTrace();
            }
            setResult(!gestureTouch ? 0 : 1);
            Toast.makeText(this, "Sign saved", Toast.LENGTH_SHORT).show();
            finish();
        });


        binding.clearButton.setOnClickListener(arg0 -> {
            // TODO Auto-generated method stub
            binding.signaturePad.invalidate();
            binding.signaturePad.clear(true);
            binding.signaturePad.clearAnimation();
            binding.signaturePad.cancelClearAnimation();
        });
    }
}