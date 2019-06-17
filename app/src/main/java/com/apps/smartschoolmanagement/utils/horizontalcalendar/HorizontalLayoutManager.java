package com.apps.smartschoolmanagement.utils.horizontalcalendar;

import android.content.Context;
import android.graphics.PointF;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.State;
import android.util.DisplayMetrics;
import android.util.TypedValue;

class HorizontalLayoutManager extends LinearLayoutManager {
    public static final float SPEED_FAST = 5.0f;
    public static final float SPEED_NORMAL = 1.0f;
    public static final float SPEED_SLOW = 0.6f;
    /* renamed from: Y */
    private final float f71Y = 1.0f;
    private float smoothScrollSpeed = 1.0f;

    HorizontalLayoutManager(Context context, boolean reverseLayout) {
        super(context, 0, reverseLayout);
    }

    public void smoothScrollToPosition(RecyclerView recyclerView, State state, int position) {
        LinearSmoothScroller smoothScroller = new LinearSmoothScroller(recyclerView.getContext()) {
            public PointF computeScrollVectorForPosition(int targetPosition) {
                return HorizontalLayoutManager.this.computeScrollVectorForPosition(targetPosition);
            }

            protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
                return 1.0f / TypedValue.applyDimension(1, HorizontalLayoutManager.this.smoothScrollSpeed, displayMetrics);
            }
        };
        smoothScroller.setTargetPosition(position);
        startSmoothScroll(smoothScroller);
    }

    public float getSmoothScrollSpeed() {
        return this.smoothScrollSpeed;
    }

    public void setSmoothScrollSpeed(float smoothScrollSpeed) {
        this.smoothScrollSpeed = smoothScrollSpeed;
    }
}
