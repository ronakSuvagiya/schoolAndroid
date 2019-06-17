package com.kosalgeek.android.photoutil;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.drawable.Drawable;
import java.io.File;
import java.io.FileNotFoundException;

public class ImageLoader {
    private static ImageLoader instance;
    private String filePath;
    private int height = 128;
    private int width = 128;

    protected ImageLoader() {
    }

    public static ImageLoader init() {
        if (instance == null) {
            synchronized (ImageLoader.class) {
                if (instance == null) {
                    instance = new ImageLoader();
                }
            }
        }
        return instance;
    }

    public ImageLoader from(String filePath) {
        this.filePath = filePath;
        return instance;
    }

    public ImageLoader requestSize(int width, int height) {
        this.height = width;
        this.width = height;
        return instance;
    }

    public Bitmap getBitmap() throws FileNotFoundException {
        if (new File(this.filePath).exists()) {
            Options options = new Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(this.filePath, options);
            options.inSampleSize = calculateInSampleSize(options, this.width, this.height);
            options.inJustDecodeBounds = false;
            return BitmapFactory.decodeFile(this.filePath, options);
        }
        throw new FileNotFoundException();
    }

    public Drawable getImageDrawable() throws FileNotFoundException {
        if (new File(this.filePath).exists()) {
            return Drawable.createFromPath(this.filePath);
        }
        throw new FileNotFoundException();
    }

    private int calculateInSampleSize(Options options, int reqWidth, int reqHeight) {
        int height = options.outHeight;
        int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            int halfHeight = height / 2;
            int halfWidth = width / 2;
            while (halfHeight / inSampleSize > reqHeight && halfWidth / inSampleSize > reqWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }
}
