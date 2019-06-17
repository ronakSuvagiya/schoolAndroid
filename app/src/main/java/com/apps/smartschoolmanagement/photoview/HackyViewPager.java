package com.apps.smartschoolmanagement.photoview;

import android.content.Context;
import androidx.viewpager.widget.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class HackyViewPager extends ViewPager {
    private boolean isLocked = false;

    public HackyViewPager(Context context) {
        super(context);
    }

    public HackyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean z = false;
        if (!this.isLocked) {
            try {
                z = super.onInterceptTouchEvent(ev);
            } catch (IllegalArgumentException e) {
            }
        }
        return z;
    }

    public boolean onTouchEvent(MotionEvent event) {
        return !this.isLocked && super.onTouchEvent(event);
    }

    public void toggleLock() {
        this.isLocked = !this.isLocked;
    }

    public void setLocked(boolean isLocked) {
        this.isLocked = isLocked;
    }

    public boolean isLocked() {
        return this.isLocked;
    }
}
