package com.apps.smartschoolmanagement.utils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Path.Direction;
import android.graphics.RectF;
import androidx.appcompat.widget.AppCompatImageView;
import android.util.AttributeSet;

public class RoundCornerImageView extends AppCompatImageView {
    private Path path;
    private float radius = 38.0f;
    private RectF rect;

    public RoundCornerImageView(Context context) {
        super(context);
        init();
    }

    public RoundCornerImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RoundCornerImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        this.path = new Path();
    }

    protected void onDraw(Canvas canvas) {
        this.rect = new RectF(0.0f, 0.0f, (float) getWidth(), (float) getHeight());
        this.path.addRoundRect(this.rect, this.radius, this.radius, Direction.CW);
        canvas.clipPath(this.path);
        super.onDraw(canvas);
    }
}
