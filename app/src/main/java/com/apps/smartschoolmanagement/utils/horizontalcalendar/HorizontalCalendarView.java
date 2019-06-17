package com.apps.smartschoolmanagement.utils.horizontalcalendar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.TypedValue;
import com.apps.smartschoolmanagement.R;

public class HorizontalCalendarView extends RecyclerView {
    private final float DEFAULT_TEXT_SIZE_DAY_NAME = 14.0f;
    private final float DEFAULT_TEXT_SIZE_DAY_NUMBER = 24.0f;
    private final float DEFAULT_TEXT_SIZE_MONTH_NAME = 14.0f;
    private final float FLING_SCALE_DOWN_FACTOR = 0.5f;
    private HorizontalCalendar horizontalCalendar;
    private Drawable selectedDateBackground;
    private int selectorColor;
    private int textColorNormal;
    private int textColorSelected;
    private float textSizeDayName;
    private float textSizeDayNumber;
    private float textSizeMonthName;

    public HorizontalCalendarView(Context context) {
        super(context);
    }

    public HorizontalCalendarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.HorizontalCalendarView, 0, 0);
        try {
            this.textColorNormal = a.getColor(2, -3355444);
            this.textColorSelected = a.getColor(3, ViewCompat.MEASURED_STATE_MASK);
            this.selectedDateBackground = a.getDrawable(0);
            this.selectorColor = a.getColor(1, fetchAccentColor());
            this.textSizeMonthName = getRawSizeValue(a, 6, 14.0f);
            this.textSizeDayNumber = getRawSizeValue(a, 5, 24.0f);
            this.textSizeDayName = getRawSizeValue(a, 4, 14.0f);
        } finally {
            a.recycle();
        }
    }

    private float getRawSizeValue(TypedArray a, int index, float defValue) {
        TypedValue outValue = new TypedValue();
        return !a.getValue(index, outValue) ? defValue : TypedValue.complexToFloat(outValue.data);
    }

    public boolean fling(int velocityX, int velocityY) {
        return super.fling((int) (((float) velocityX) * 0.5f), velocityY);
    }

    protected void onMeasure(int widthSpec, int heightSpec) {
        if (isInEditMode()) {
            setMeasuredDimension(widthSpec, 150);
        } else {
            super.onMeasure(widthSpec, heightSpec);
        }
    }

    public float getSmoothScrollSpeed() {
        return getLayoutManager().getSmoothScrollSpeed();
    }

    public void setSmoothScrollSpeed(float smoothScrollSpeed) {
        getLayoutManager().setSmoothScrollSpeed(smoothScrollSpeed);
    }

    public HorizontalCalendarAdapter getAdapter() {
        return (HorizontalCalendarAdapter) super.getAdapter();
    }

    public HorizontalLayoutManager getLayoutManager() {
        return (HorizontalLayoutManager) super.getLayoutManager();
    }

    private int fetchAccentColor() {
        TypedValue typedValue = new TypedValue();
        TypedArray a = getContext().obtainStyledAttributes(typedValue.data, new int[]{R.attr.colorAccent});
        int color = a.getColor(0, 0);
        a.recycle();
        return color;
    }

    public HorizontalCalendar getHorizontalCalendar() {
        return this.horizontalCalendar;
    }

    public void setHorizontalCalendar(HorizontalCalendar horizontalCalendar) {
        if (horizontalCalendar.getTextColorNormal() == 0) {
            horizontalCalendar.setTextColorNormal(this.textColorNormal);
        }
        if (horizontalCalendar.getTextColorSelected() == 0) {
            horizontalCalendar.setTextColorSelected(this.textColorSelected);
        }
        if (horizontalCalendar.getSelectorColor() == null) {
            horizontalCalendar.setSelectorColor(this.selectorColor);
        }
        if (horizontalCalendar.getSelectedDateBackground() == null) {
            horizontalCalendar.setSelectedDateBackground(this.selectedDateBackground);
        }
        if (horizontalCalendar.getTextSizeMonthName() == 0.0f) {
            horizontalCalendar.setTextSizeMonthName(this.textSizeMonthName);
        }
        if (horizontalCalendar.getTextSizeDayNumber() == 0.0f) {
            horizontalCalendar.setTextSizeDayNumber(this.textSizeDayNumber);
        }
        if (horizontalCalendar.getTextSizeDayName() == 0.0f) {
            horizontalCalendar.setTextSizeDayName(this.textSizeDayName);
        }
        this.horizontalCalendar = horizontalCalendar;
    }

    public int getPositionOfCenterItem() {
        int numberOfDatesOnScreen = this.horizontalCalendar.getNumberOfDatesOnScreen();
        int firstVisiblePosition = getLayoutManager().findFirstCompletelyVisibleItemPosition();
        if (firstVisiblePosition == -1) {
            return -1;
        }
        return (numberOfDatesOnScreen / 2) + firstVisiblePosition;
    }
}
