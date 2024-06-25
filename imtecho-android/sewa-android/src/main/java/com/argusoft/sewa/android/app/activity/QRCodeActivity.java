package com.argusoft.sewa.android.app.activity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ShareCompat;
import androidx.core.content.FileProvider;

import com.argusoft.sewa.android.app.R;
import com.argusoft.sewa.android.app.constants.FieldNameConstants;
import com.argusoft.sewa.android.app.constants.LabelConstants;
import com.argusoft.sewa.android.app.util.SewaConstants;
import com.argusoft.sewa.android.app.util.UtilBean;
import com.google.android.material.textview.MaterialTextView;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import org.androidannotations.annotations.EActivity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by khyati on 14/3/23
 */
@EActivity
public class QRCodeActivity extends MenuActivity implements View.OnClickListener {

    private ImageView imageQr;
    private String qrString;
    private String familyId;
    private Bitmap bitmapQr;
    private LinearLayoutCompat detailsView;
    private MaterialTextView buttonDownload, buttonShare;
    private String fileName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        initView();
        try {
            Bundle extras = getIntent().getExtras();
            if (extras != null && extras.containsKey(FieldNameConstants.QR_DATA)) {
                familyId = extras.getString(FieldNameConstants.FAMILY_ID, "");
                qrString = extras.getString(FieldNameConstants.QR_DATA, "");
                AppCompatTextView familyIdView = findViewById(R.id.familyId);
                familyIdView.setText(familyId);
            }
            imageQr.setImageBitmap(encodeAsBitmap(qrString));
        } catch (WriterException e) {
            e.printStackTrace();
        }
        fileName = "QR_FAMILY_" + familyId.replace("/", "_") + ".png";
        setTitle(UtilBean.getTitleText(LabelConstants.VIEW_QR_CODE));
    }

    Bitmap encodeAsBitmap(@NonNull String str) throws WriterException {
        QRCodeWriter writer = new QRCodeWriter();
        BitMatrix bitMatrix = writer.encode(str, BarcodeFormat.QR_CODE, 600, 600);

        int w = bitMatrix.getWidth();
        int h = bitMatrix.getHeight();
        int[] pixels = new int[w * h];
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                pixels[y * w + x] = bitMatrix.get(x, y) ? Color.BLACK : Color.WHITE;
            }
        }
        bitmapQr = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        bitmapQr.setPixels(pixels, 0, w, 0, 0, w, h);
        return bitmapQr;
    }

    private void initView() {
        setContentView(R.layout.activity_qr_data);
        imageQr = findViewById(R.id.imageQr);

        buttonDownload = findViewById(R.id.buttonDownload);
        buttonShare = findViewById(R.id.buttonShare);
        detailsView = findViewById(R.id.detailsView);
        Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        buttonDownload.setOnClickListener(this);
        buttonShare.setOnClickListener(this);
    }

    private void saveImageToGallery(Bitmap myBitmap) {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.DISPLAY_NAME, fileName);
        values.put(MediaStore.Images.Media.TITLE, fileName);
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/png");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            values.put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES);
        }
        Uri imageUri;

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                imageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                assert imageUri != null;
                OutputStream os = getContentResolver().openOutputStream(imageUri);
                myBitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
                assert os != null;
                os.close();
                Toast.makeText(this, "Image saved to gallery", Toast.LENGTH_SHORT).show();
            } else {
                File storageDir = Environment.getExternalStorageDirectory();
                File file = new File(storageDir, fileName);
                FileOutputStream outputStream = new FileOutputStream(file);
                myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, outputStream);
                outputStream.flush();
                outputStream.close();
                values.put(MediaStore.Images.Media.DATA, file.getAbsolutePath());
                getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                Toast.makeText(this, "Image saved to gallery", Toast.LENGTH_SHORT).show();

            }
        } catch (IOException e) {
            Toast.makeText(this, "Error saving image to gallery", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private void shareQrCode(Bitmap myBitmap) {
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); // temp permission for receiving app to read this file
        shareIntent.setDataAndType(getImageUri(this, myBitmap), getContentResolver().getType(getImageUri(this, myBitmap)));
        shareIntent.putExtra(Intent.EXTRA_STREAM, getImageUri(this, myBitmap));
        startActivity(Intent.createChooser(shareIntent, "Choose An App"));
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonDownload:
                saveImageToGallery(UtilBean.getBitmapFromView(detailsView));
                break;
            case R.id.buttonShare:
                shareQrCode(UtilBean.getBitmapFromView(detailsView));
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        menu.findItem(R.id.menu_refresh).setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}