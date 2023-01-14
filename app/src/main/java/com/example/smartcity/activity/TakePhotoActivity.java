package com.example.smartcity.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.smartcity.R;

import java.io.FileNotFoundException;
import java.io.IOException;

public class TakePhotoActivity extends AppCompatActivity {

    private static final String TAG = "TakePhotoActivity";
    private static final int REQUEST_CAMERA = 0;
    private static final int REQUEST_ALBUM = 1;

    private Toolbar mToolbar;
    private ImageView mPhotoImageView;
    private Button mPhotoButton;

    private Intent mIntent = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_photo);

        mToolbar = findViewById(R.id.tool_bar);
        mPhotoImageView = findViewById(R.id.photo_image_view);
        mPhotoButton = findViewById(R.id.photo_button);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
            builder.detectFileUriExposure();
        }

        checkPermission();
        initListener();
    }

    private void checkPermission() {
        if (ContextCompat.checkSelfPermission(TakePhotoActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(TakePhotoActivity.this, Manifest.permission.CAMERA)) {
                Toast.makeText(this, "禁止访问", Toast.LENGTH_SHORT).show();
            } else {
                ActivityCompat.requestPermissions(TakePhotoActivity.this, new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA);
            }
        } else {
            takePhoto();
        }
    }

    private void initListener() {
        mToolbar.setNavigationIcon(R.mipmap.top_bar_left_back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mPhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(TakePhotoActivity.this)
                        .setIcon(R.mipmap.picture)
                        .setMessage("插入图片")
                        .setPositiveButton("拍照", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                takePhoto();
                            }
                        })
                        .setNegativeButton("相册", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                chooseFromAlbum();
                            }
                        })
                        .create().show();

            }
        });
    }

    public void takePhoto() {
        mIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        mIntent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(mIntent, REQUEST_CAMERA);
    }

    public void chooseFromAlbum() {
        mIntent = new Intent();
        mIntent.setType("image/*");
        mIntent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(mIntent, REQUEST_ALBUM);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Uri uri = data.getData();
            ContentResolver contentResolver = getContentResolver();
            Bitmap bitmap = null;
            Bundle extras = null;
            if (requestCode == REQUEST_ALBUM) {
                try {
                    bitmap = BitmapFactory.decodeStream(contentResolver.openInputStream(uri));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            } else if (requestCode == REQUEST_CAMERA) {
                try {
                    if (uri != null) {
                        bitmap = MediaStore.Images.Media.getBitmap(contentResolver, uri);
                    } else {
                        extras = data.getExtras();
                        bitmap = extras.getParcelable("data");
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            int imgWidth = bitmap.getWidth();
            int imgHeight = bitmap.getHeight();
            double partion = imgWidth * 1.0 / imgHeight;
            double sqrtLength = Math.sqrt(partion * partion + 1);

            double newImgW = 680 * (partion / sqrtLength);
            double newImgH = 680 * (1 / sqrtLength);
            float scaleW = (float) (newImgW / imgWidth);
            float scaleH = (float) (newImgH / imgHeight);

            Matrix mx = new Matrix();
            mx.postScale(scaleW, scaleH);
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, imgWidth, imgHeight, mx, true);
            bitmap = getBitmapWidth(bitmap);
            mPhotoImageView.setImageBitmap(bitmap);
        }
    }

    public Bitmap getBitmapWidth(Bitmap bitmap) {
        float frameSize = 0.2f;
        Matrix matrix = new Matrix();
        // 用来做底图
        Bitmap mbitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        // 设置底图为画布
        Canvas canvas = new Canvas(mbitmap);
        canvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG));
        float scale_x = (bitmap.getWidth() - 2 * frameSize - 2) * 1f / (bitmap.getWidth());
        float scale_y = (bitmap.getHeight() - 2 * frameSize - 2) * 1f / (bitmap.getHeight());
        matrix.reset();
        matrix.postScale(scale_x, scale_y);

        // 减去边框的大小
        bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);

        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setStrokeWidth(1);
        paint.setStyle(Paint.Style.FILL);

        // 绘制底图边框
        canvas.drawRect(new Rect(0, 0, mbitmap.getWidth(), mbitmap.getHeight()), paint);
        // 绘制灰色边框
        paint.setColor(Color.GRAY);
        canvas.drawRect(new Rect((int) (frameSize), (int) (frameSize), mbitmap.getWidth() - (int) (frameSize), mbitmap.getHeight() - (int) (frameSize)), paint);
        canvas.drawBitmap(bitmap, frameSize + 2, frameSize + 2, paint);
        return mbitmap;
    }
}