package com.apps.smartschoolmanagement.utils;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.graphics.PointF;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import com.apps.smartschoolmanagement.R;
import java.text.DecimalFormat;

@SuppressLint({"NewApi"})
public class CircleDisplay extends View {
    private static final String LOG_TAG = "CircleDisplay";
    public static final int PAINT_ARC = 2;
    public static final int PAINT_INNER = 3;
    public static final int PAINT_TEXT = 1;
    String customText;
    private float mAngle = 0.0f;
    private Paint mArcPaint;
    private Paint mArcPaintoutersmall;
    private Paint mArcPainttransparent;
    private boolean mBoxSetup = false;
    private RectF mCircleBox = new RectF();
    private String[] mCustomText = null;
    private int mDimAlpha = 80;
    private ObjectAnimator mDrawAnimator;
    private boolean mDrawInner = true;
    private boolean mDrawText = true;
    private DecimalFormat mFormatValue = new DecimalFormat("###,###,###,##0.0");
    private GestureDetector mGestureDetector;
    private Paint mInnerCirclePaint;
    private SelectionListener mListener;
    private float mMaxValue = 0.0f;
    private float mPhase = 0.0f;
    private float mStartAngle = 270.0f;
    private float mStepSize = 1.0f;
    private Paint mTextPaint;
    private boolean mTouchEnabled = true;
    private String mUnit = "%";
    private float mValue = 0.0f;
    private float mValueWidthPercent = 30.0f;

    public interface SelectionListener {
        void onSelectionUpdate(float f, float f2);

        void onValueSelected(float f, float f2);
    }

    public static abstract class Utils {
        public static float convertDpToPixel(Resources r, float dp) {
            return dp * (((float) r.getDisplayMetrics().densityDpi) / 160.0f);
        }
    }

    public CircleDisplay(Context context) {
        super(context);
        init();
    }

    public CircleDisplay(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CircleDisplay(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        this.mBoxSetup = false;
        this.mArcPaint = new Paint(1);
        this.mArcPaint.setStyle(Style.FILL_AND_STROKE);
        this.mArcPaint.setColor(getResources().getColor(R.color.colorPrimary));
        this.mArcPaintoutersmall = new Paint(1);
        this.mArcPaintoutersmall.setStyle(Style.FILL_AND_STROKE);
        this.mArcPainttransparent = new Paint(1);
        this.mArcPainttransparent.setStyle(Style.FILL_AND_STROKE);
        this.mArcPainttransparent.setColor(0);
        this.mInnerCirclePaint = new Paint(1);
        this.mInnerCirclePaint.setStyle(Style.FILL);
        this.mTextPaint = new Paint(1);
        this.mTextPaint.setStyle(Style.STROKE);
        this.mTextPaint.setTextAlign(Align.CENTER);
        this.mTextPaint.setColor(getResources().getColor(17170445));
        this.mTextPaint.setTextSize(Utils.convertDpToPixel(getResources(), 16.0f));
        this.mDrawAnimator = ObjectAnimator.ofFloat(this, "phase", new float[]{this.mPhase, 1.0f}).setDuration(3000);
        this.mDrawAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!this.mBoxSetup) {
            this.mBoxSetup = true;
            setupBox();
        }
        drawWholeCircle(canvas);
        drawValue(canvas);
        if (this.mDrawInner) {
            drawInnerCircle(canvas);
        }
        if (!this.mDrawText) {
            return;
        }
        if (this.mCustomText != null) {
            drawCustomText(canvas);
        } else {
            drawText(canvas);
        }
    }

    private void drawText(Canvas c) {
        c.drawText(this.mFormatValue.format((double) (this.mValue * this.mPhase)) + " " + this.mUnit, (float) (getWidth() / 2), ((float) (getHeight() / 2)) + this.mTextPaint.descent(), this.mTextPaint);
    }

    public void setCircleText(Canvas c) {
        c.drawText(this.customText, (float) (getWidth() / 2), ((float) (getHeight() / 2)) + this.mTextPaint.descent(), this.mTextPaint);
    }

    public String setCustomText(String text) {
        this.customText = text;
        return text;
    }

    private void drawCustomText(Canvas c) {
        int index = (int) ((this.mValue * this.mPhase) / this.mStepSize);
        for (String drawText : this.mCustomText) {
            c.drawText(drawText, (float) (getWidth() / 2), ((float) (getHeight() / 2)) + this.mTextPaint.descent(), this.mTextPaint);
        }
    }

    private void drawWholeCircle(Canvas c) {
        this.mArcPaint.setAlpha(this.mDimAlpha);
        c.drawCircle((float) getWidth(), (float) getHeight(), getRadius(), this.mArcPainttransparent);
    }

    private void drawInnerCircle(Canvas c) {
        c.drawCircle((float) (getWidth() / 2), (float) (getHeight() / 2), (getRadius() / 100.0f) * (100.0f - this.mValueWidthPercent), this.mInnerCirclePaint);
    }

    private void drawValue(Canvas c) {
        this.mArcPaintoutersmall.setAlpha(255);
        Canvas canvas = c;
        canvas.drawArc(this.mCircleBox, this.mStartAngle, this.mAngle * this.mPhase, true, this.mArcPaintoutersmall);
    }

    private void setupBox() {
        int width = getWidth();
        int height = getHeight();
        float diameter = getDiameter();
        this.mCircleBox = new RectF(((float) (width / 2)) - (diameter / 2.0f), ((float) (height / 2)) - (diameter / 2.0f), ((float) (width / 2)) + (diameter / 2.0f), ((float) (height / 2)) + (diameter / 2.0f));
    }

    public void showValue(float toShow, float total, boolean animated) {
        this.mAngle = calcAngle((toShow / total) * 100.0f);
        this.mValue = toShow;
        this.mMaxValue = total;
        if (animated) {
            startAnim();
            return;
        }
        this.mPhase = 1.0f;
        invalidate();
    }

    public void setUnit(String unit) {
        this.mUnit = unit;
    }

    public float getValue() {
        return this.mValue;
    }

    public void startAnim() {
        this.mPhase = 0.0f;
        this.mDrawAnimator.start();
    }

    public void setAnimDuration(int durationmillis) {
        this.mDrawAnimator.setDuration((long) durationmillis);
    }

    public float getDiameter() {
        return (float) Math.min(getWidth(), getHeight());
    }

    public float getRadius() {
        return getDiameter() / 2.0f;
    }

    private float calcAngle(float percent) {
        return (percent / 100.0f) * 360.0f;
    }

    public void setStartAngle(float angle) {
        this.mStartAngle = angle;
    }

    public float getPhase() {
        return this.mPhase;
    }

    public void setPhase(float phase) {
        this.mPhase = phase;
        invalidate();
    }

    public void setDrawInnerCircle(boolean enabled) {
        this.mDrawInner = enabled;
    }

    public boolean isDrawInnerCircleEnabled() {
        return this.mDrawInner;
    }

    public void setDrawText(boolean enabled) {
        this.mDrawText = enabled;
    }

    public boolean isDrawTextEnabled() {
        return this.mDrawText;
    }

    public void setColor(int color) {
        this.mArcPaint.setColor(color);
    }

    public void setColorinner(int color) {
        this.mInnerCirclePaint.setColor(color);
    }

    public void setColorArc(int color) {
        this.mArcPaintoutersmall.setColor(color);
    }

    public void setTextSize(float size) {
        this.mTextPaint.setTextSize(Utils.convertDpToPixel(getResources(), size));
    }

    public void setValueWidthPercent(float percentFromTotalWidth) {
        this.mValueWidthPercent = percentFromTotalWidth;
    }

    public void setCustomText(String[] custom) {
        this.mCustomText = custom;
    }

    public void setFormatDigits(int digits) {
        StringBuffer b = new StringBuffer();
        for (int i = 0; i < digits; i++) {
            if (i == 0) {
                b.append(".");
            }
            b.append("0");
        }
        this.mFormatValue = new DecimalFormat("###,###,###,##0" + b.toString());
    }

    public void setDimAlpha(int alpha) {
        this.mDimAlpha = alpha;
    }

    public void setPaint(int which, Paint p) {
        switch (which) {
            case 1:
                this.mTextPaint = p;
                return;
            case 2:
                this.mArcPaint = p;
                return;
            case 3:
                this.mInnerCirclePaint = p;
                return;
            default:
                return;
        }
    }

    public void setStepSize(float stepsize) {
        this.mStepSize = stepsize;
    }

    public float getStepSize() {
        return this.mStepSize;
    }

    public PointF getCenter() {
        return new PointF((float) (getWidth() / 2), (float) (getHeight() / 2));
    }

    public void setTouchEnabled(boolean enabled) {
        this.mTouchEnabled = enabled;
    }

    public boolean isTouchEnabled() {
        return this.mTouchEnabled;
    }

    public void setSelectionListener(SelectionListener l) {
        this.mListener = l;
    }

    private void updateValue(float x, float y) {
        float angle = getAngleForPoint(x, y);
        float newVal = (this.mMaxValue * angle) / 360.0f;
        if (this.mStepSize == 0.0f) {
            this.mValue = newVal;
            this.mAngle = angle;
            return;
        }
        float remainder = newVal % this.mStepSize;
        if (remainder <= this.mStepSize / 2.0f) {
            newVal -= remainder;
        } else {
            newVal = (newVal - remainder) + this.mStepSize;
        }
        this.mAngle = getAngleForValue(newVal);
        this.mValue = newVal;
    }

    public float getAngleForPoint(float x, float y) {
        PointF c = getCenter();
        double tx = (double) (x - c.x);
        double ty = (double) (y - c.y);
        float angle = (float) Math.toDegrees(Math.acos(ty / Math.sqrt((tx * tx) + (ty * ty))));
        if (x > c.x) {
            angle = 360.0f - angle;
        }
        angle += 180.0f;
        if (angle > 360.0f) {
            return angle - 360.0f;
        }
        return angle;
    }

    public float getAngleForValue(float value) {
        return (value / this.mMaxValue) * 360.0f;
    }

    public float getValueForAngle(float angle) {
        return (angle / 360.0f) * this.mMaxValue;
    }

    public float distanceToCenter(float x, float y) {
        float xDist;
        float yDist;
        PointF c = getCenter();
        if (x > c.x) {
            xDist = x - c.x;
        } else {
            xDist = c.x - x;
        }
        if (y > c.y) {
            yDist = y - c.y;
        } else {
            yDist = c.y - y;
        }
        return (float) Math.sqrt(Math.pow((double) xDist, 2.0d) + Math.pow((double) yDist, 2.0d));
    }
}
