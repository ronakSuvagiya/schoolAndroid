package com.apps.smartschoolmanagement.utils;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import java.util.Collection;
import java.util.HashSet;

public class DateDecorator implements DayViewDecorator {
    private Drawable background_drawable = null;
    private int color = 0;
    private Context context;
    private final HashSet<CalendarDay> dates;
    private ColorDrawable drawable;

    public DateDecorator(Context context, int color, Drawable background, Collection<CalendarDay> dates) {
        this.context = context;
        this.color = color;
        this.dates = new HashSet(dates);
        this.drawable = new ColorDrawable(color);
        this.background_drawable = background;
    }

    public DateDecorator(Context context, int color, Collection<CalendarDay> dates) {
        this.context = context;
        this.color = color;
        this.dates = new HashSet(dates);
        this.drawable = new ColorDrawable(color);
    }

    public boolean shouldDecorate(CalendarDay day) {
        return this.dates.contains(day);
    }

    public void decorate(DayViewFacade view) {
        if (this.background_drawable != null) {
            view.addSpan(new ForegroundColorSpan(-1));
            view.setSelectionDrawable(this.background_drawable);
            return;
        }
        view.addSpan(new StyleSpan(1));
        view.addSpan(new ForegroundColorSpan(-12303292));
    }
}
