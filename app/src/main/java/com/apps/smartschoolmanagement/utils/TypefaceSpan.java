package com.apps.smartschoolmanagement.utils;

import android.content.Context;
import android.graphics.Typeface;
import androidx.core.content.res.ResourcesCompat;
import android.text.TextPaint;
import android.text.style.MetricAffectingSpan;
import android.util.LruCache;

public class TypefaceSpan extends MetricAffectingSpan {
    private static LruCache<String, Typeface> sTypefaceCache = new LruCache(12);
    private Typeface mTypeface;

    public TypefaceSpan(Context context, int id) {
        this.mTypeface = (Typeface) sTypefaceCache.get("" + id);
        if (this.mTypeface == null) {
            this.mTypeface = ResourcesCompat.getFont(context, id);
            sTypefaceCache.put("" + id, this.mTypeface);
        }
    }

    public void updateMeasureState(TextPaint p) {
        p.setTypeface(this.mTypeface);
        p.setFlags(p.getFlags() | 128);
    }

    public void updateDrawState(TextPaint tp) {
        tp.setTypeface(this.mTypeface);
        tp.setFlags(tp.getFlags() | 128);
    }
}
