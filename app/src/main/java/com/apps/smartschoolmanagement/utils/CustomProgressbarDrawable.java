package com.apps.smartschoolmanagement.utils;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;

public class CustomProgressbarDrawable extends Drawable {
    private ImageDownloadListener mListener;

    public CustomProgressbarDrawable(ImageDownloadListener listener) {
        this.mListener = listener;
    }

    public void draw(Canvas canvas) {
    }

    public void setAlpha(int alpha) {
    }

    public void setColorFilter(ColorFilter cf) {
    }

    public int getOpacity() {
        return 0;
    }

    protected boolean onLevelChange(int level) {
        int progress = (int) ((((double) level) / 10000.0d) * 100.0d);
        if (this.mListener != null) {
            this.mListener.onUpdate(progress);
        }
        return super.onLevelChange(level);
    }
}
