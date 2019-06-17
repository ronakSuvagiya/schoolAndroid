package com.kosalgeek.android.photoutil;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.util.Base64;
import java.io.ByteArrayOutputStream;

public class ImageBase64 {
    final String TAG = getClass().getSimpleName();

    public static String encode(Bitmap bitmap) {
        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        bitmap.compress(CompressFormat.JPEG, 100, bao);
        if (!(bitmap == null || bitmap.isRecycled())) {
            bitmap.recycle();
        }
        return Base64.encodeToString(bao.toByteArray(), 0);
    }

    public static Bitmap decode(String encodedImage) {
        byte[] decodedString = Base64.decode(encodedImage.substring(encodedImage.indexOf(",") + 1), 8);
        return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
    }
}
