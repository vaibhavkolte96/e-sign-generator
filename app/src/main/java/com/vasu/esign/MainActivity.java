package com.vasu.esign;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.ContextWrapper;
import android.gesture.GestureOverlayView;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    GestureOverlayView gestureView;
    String path;
    File file;
    Bitmap bitmap;

    public boolean gestureTouch = false;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = MainActivity.this;
        Button donebutton = (Button) findViewById(R.id.DoneButton);
        donebutton.setText("Done");
        Button clearButton = (Button) findViewById(R.id.ClearButton);
        clearButton.setText("Clear");

        path = Environment.getExternalStorageDirectory() + "/signature.png";
        file = new File(path);
        file.delete();
        gestureView = (GestureOverlayView) findViewById(R.id.signaturePad);
        gestureView.setDrawingCacheEnabled(true);

        gestureView.setAlwaysDrawnWithCacheEnabled(true);
        gestureView.setHapticFeedbackEnabled(false);
        gestureView.cancelLongPress();
        gestureView.cancelClearAnimation();
        gestureView.addOnGestureListener(new GestureOverlayView.OnGestureListener() {

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
                if (arg1.getAction() == MotionEvent.ACTION_MOVE) {
                    gestureTouch = false;
                } else {
                    gestureTouch = true;
                }
            }
        });

        donebutton.setOnClickListener(v -> {
            // TODO Auto-generated method stub
            try {
                bitmap = Bitmap.createBitmap(gestureView.getDrawingCache());

                MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, " yourTitle ", " yourDescription");

//                    saveImageToInternalStorage(context,bitmap);


//                    saveImage(bitmap);

//                    saveImage(context,bitmap,"mySign","png");


//                    new ImageSaver(context).
//                            setFileName("myImage.png").
//                            setDirectoryName("images").
//                            save(bitmap);


//                    if(file.createNewFile()){
//                        Toast.makeText(context, "file exist", Toast.LENGTH_SHORT).show();
//                    }
//                    FileOutputStream fos = new FileOutputStream(file);
//                    fos = new FileOutputStream(file);
//                    // compress to specified format (PNG), quality - which is
//                    // ignored for PNG, and out stream
//                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
//                    fos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (!gestureTouch) {
                setResult(0);
//                    finish();
            } else {
                setResult(1);
//                    finish();
            }
            clearButton.performClick();
        });


        clearButton.setOnClickListener(arg0 -> {
            // TODO Auto-generated method stub
            gestureView.invalidate();
            gestureView.clear(true);
            gestureView.clearAnimation();
            gestureView.cancelClearAnimation();
        });
    }

    public void saveImageToInternalStorage(Context mContext, Bitmap bitmap) {

        String mTimeStamp = new SimpleDateFormat("ddMMyyyy_HHmm").format(new Date());

        String mImageName = "snap_" + mTimeStamp + ".jpg";

        ContextWrapper wrapper = new ContextWrapper(mContext);

        File file = wrapper.getDir("Images", MODE_PRIVATE);

        file = new File(file, "snap_" + mImageName + ".jpg");

        try {

            OutputStream stream = null;

            stream = new FileOutputStream(file);

            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, " yourTitle ", " yourDescription");
            stream.flush();

            stream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

//        Uri mImageUri = Uri.parse(file.getAbsolutePath());
//
//        return mImageUri;
    }

    public void saveImage(Context context, Bitmap bitmap, String name, String extension) {
        name = name + "." + extension;
        FileOutputStream fileOutputStream;
        try {
            fileOutputStream = context.openFileOutput(name, Context.MODE_PRIVATE);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, fileOutputStream);
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void saveImage(Bitmap bitmap) {
//        File dir = new File(Environment.getDataDirectory(),"saveImage");
//        if(!dir.exists()){
//            dir.mkdir();
//        }

        path = Environment.getExternalStorageDirectory() + "/signature.png";
        file = new File(path);
//        File file = new File(dir,".jpg");
        try {
            FileOutputStream outputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, outputStream);
            Toast.makeText(this, "Done", Toast.LENGTH_LONG).show();
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}