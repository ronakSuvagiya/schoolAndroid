package com.apps.smartschoolmanagement.utils.horizontalcalendar;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.OnScrollListener;
import android.view.View;
import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public final class HorizontalCalendar {
    private final int calendarId;
    HorizontalCalendarListener calendarListener;
    HorizontalCalendarView calendarView;
    private Date dateEndCalendar;
    private SimpleDateFormat dateFormat;
    private Date dateStartCalendar;
    private final String formatDayName;
    private final String formatDayNumber;
    private final String formatMonth;
    private final DateHandler handler;
    boolean loading;
    HorizontalCalendarAdapter mCalendarAdapter;
    ArrayList<Date> mListDays;
    private final int numberOfDatesOnScreen;
    final OnScrollListener onScrollListener = new HorizontalCalendarScrollListener();
    private final View rootView;
    private Drawable selectedDateBackground;
    private Integer selectorColor;
    private final boolean showDayName;
    private final boolean showMonthName;
    private int textColorNormal;
    private int textColorSelected;
    private float textSizeDayName;
    private float textSizeDayNumber;
    private float textSizeMonthName;

    /* renamed from: com.apps.smartschoolmanagement.utils.horizontalcalendar.HorizontalCalendar$1 */
    class C14611 implements Runnable {
        C14611() {
        }

        public void run() {
            HorizontalCalendar.this.mCalendarAdapter.notifyDataSetChanged();
        }
    }

    public static class Builder {
        Date dateEndCalendar;
        Date dateStartCalendar;
        Date defaultSelectedDate;
        String formatDayName;
        String formatDayNumber;
        String formatMonth;
        int numberOfDatesOnScreen;
        final View rootView;
        Drawable selectedDateBackground;
        Integer selectorColor;
        boolean showDayName = true;
        boolean showMonthName = true;
        int textColorNormal;
        int textColorSelected;
        float textSizeDayName;
        float textSizeDayNumber;
        float textSizeMonthName;
        final int viewId;

        public Builder(View rootView, int viewId) {
            this.rootView = rootView;
            this.viewId = viewId;
        }

        public Builder(Activity activity, int viewId) {
            this.rootView = activity.getWindow().getDecorView();
            this.viewId = viewId;
        }

        public Builder defaultSelectedDate(Date date) {
            this.defaultSelectedDate = date;
            return this;
        }

        public Builder startDate(Date dateStartCalendar) {
            this.dateStartCalendar = dateStartCalendar;
            return this;
        }

        public Builder endDate(Date dateEndCalendar) {
            this.dateEndCalendar = dateEndCalendar;
            return this;
        }

        public Builder datesNumberOnScreen(int numberOfItemsOnScreen) {
            this.numberOfDatesOnScreen = numberOfItemsOnScreen;
            return this;
        }

        public Builder dayNameFormat(String format) {
            this.formatDayName = format;
            return this;
        }

        public Builder dayNumberFormat(String format) {
            this.formatDayNumber = format;
            return this;
        }

        public Builder monthFormat(String format) {
            this.formatMonth = format;
            return this;
        }

        public Builder textColor(int textColorNormal, int textColorSelected) {
            this.textColorNormal = textColorNormal;
            this.textColorSelected = textColorSelected;
            return this;
        }

        public Builder selectedDateBackground(Drawable background) {
            this.selectedDateBackground = background;
            return this;
        }

        public Builder selectorColor(int selectorColor) {
            this.selectorColor = Integer.valueOf(selectorColor);
            return this;
        }

        public Builder textSize(float textSizeMonthName, float textSizeDayNumber, float textSizeDayName) {
            this.textSizeMonthName = textSizeMonthName;
            this.textSizeDayNumber = textSizeDayNumber;
            this.textSizeDayName = textSizeDayName;
            return this;
        }

        public Builder textSizeMonthName(float textSizeMonthName) {
            this.textSizeMonthName = textSizeMonthName;
            return this;
        }

        public Builder textSizeDayNumber(float textSizeDayNumber) {
            this.textSizeDayNumber = textSizeDayNumber;
            return this;
        }

        public Builder textSizeDayName(float textSizeDayName) {
            this.textSizeDayName = textSizeDayName;
            return this;
        }

        public Builder showDayName(boolean value) {
            this.showDayName = value;
            return this;
        }

        public Builder showMonthName(boolean value) {
            this.showMonthName = value;
            return this;
        }

        public HorizontalCalendar build() {
            initDefaultValues();
            HorizontalCalendar horizontalCalendar = new HorizontalCalendar(this);
            horizontalCalendar.loadHorizontalCalendar();
            return horizontalCalendar;
        }

        private void initDefaultValues() {
            if (this.numberOfDatesOnScreen <= 0) {
                this.numberOfDatesOnScreen = 5;
            }
            if (this.formatDayName == null && this.showDayName) {
                this.formatDayName = "EEE";
            }
            if (this.formatDayNumber == null) {
                this.formatDayNumber = "dd";
            }
            if (this.formatMonth == null && this.showMonthName) {
                this.formatMonth = "MMM";
            }
            if (this.dateStartCalendar == null) {
                Calendar c = Calendar.getInstance();
                c.add(2, -1);
                this.dateStartCalendar = c.getTime();
            }
            if (this.dateEndCalendar == null) {
                Calendar c2 = Calendar.getInstance();
                c2.add(2, 1);
                this.dateEndCalendar = c2.getTime();
            }
            if (this.defaultSelectedDate == null) {
                this.defaultSelectedDate = new Date();
            }
        }
    }

    private static class DateHandler extends Handler {
        public Date date = null;
        private final WeakReference<HorizontalCalendar> horizontalCalendar;
        public boolean immediate = true;

        public DateHandler(HorizontalCalendar horizontalCalendar, Date defaultDate) {
            this.horizontalCalendar = new WeakReference(horizontalCalendar);
            this.date = defaultDate;
        }

        public void handleMessage(Message msg) {
            HorizontalCalendar calendar = (HorizontalCalendar) this.horizontalCalendar.get();
            if (calendar != null) {
                calendar.loading = false;
                if (this.date != null) {
                    calendar.selectDate(this.date, this.immediate);
                }
            }
        }
    }

    private class HorizontalCalendarScrollListener extends OnScrollListener {
        int lastSelectedItem = -1;
        final Runnable selectedItemRefresher = new SelectedItemRefresher();

        private class SelectedItemRefresher implements Runnable {
            SelectedItemRefresher() {
            }

            public void run() {
                int positionOfCenterItem = HorizontalCalendar.this.calendarView.getPositionOfCenterItem();
                if (HorizontalCalendarScrollListener.this.lastSelectedItem == -1 || HorizontalCalendarScrollListener.this.lastSelectedItem != positionOfCenterItem) {
                    HorizontalCalendar.this.mCalendarAdapter.notifyItemChanged(positionOfCenterItem, "UPDATE_SELECTOR");
                    if (HorizontalCalendarScrollListener.this.lastSelectedItem != -1) {
                        HorizontalCalendar.this.mCalendarAdapter.notifyItemChanged(HorizontalCalendarScrollListener.this.lastSelectedItem, "UPDATE_SELECTOR");
                    }
                    HorizontalCalendarScrollListener.this.lastSelectedItem = positionOfCenterItem;
                }
            }
        }

        HorizontalCalendarScrollListener() {
        }

        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            HorizontalCalendar.this.post(this.selectedItemRefresher);
            if (HorizontalCalendar.this.calendarListener != null) {
                HorizontalCalendar.this.calendarListener.onCalendarScroll(HorizontalCalendar.this.calendarView, dx, dy);
            }
        }
    }

    private class InitializeDatesList extends AsyncTask<Void, Void, Void> {
        InitializeDatesList() {
        }

        protected void onPreExecute() {
            HorizontalCalendar.this.loading = true;
            super.onPreExecute();
        }

        protected Void doInBackground(Void... params) {
            GregorianCalendar calendar = new GregorianCalendar();
            calendar.setTime(HorizontalCalendar.this.dateStartCalendar);
            calendar.add(5, -(HorizontalCalendar.this.numberOfDatesOnScreen / 2));
            Date dateStartBefore = calendar.getTime();
            calendar.setTime(HorizontalCalendar.this.dateEndCalendar);
            calendar.add(5, HorizontalCalendar.this.numberOfDatesOnScreen / 2);
            Date dateEndAfter = calendar.getTime();
            for (Date date = dateStartBefore; !date.after(dateEndAfter); date = calendar.getTime()) {
                HorizontalCalendar.this.mListDays.add(date);
                calendar.setTime(date);
                calendar.add(5, 1);
            }
            return null;
        }

        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            HorizontalCalendar.this.mCalendarAdapter = new HorizontalCalendarAdapter(HorizontalCalendar.this.calendarView, HorizontalCalendar.this.mListDays);
            HorizontalCalendar.this.mCalendarAdapter.setHasStableIds(true);
            HorizontalCalendar.this.calendarView.setAdapter(HorizontalCalendar.this.mCalendarAdapter);
            HorizontalCalendar.this.calendarView.setLayoutManager(new HorizontalLayoutManager(HorizontalCalendar.this.calendarView.getContext(), false));
            HorizontalCalendar.this.show();
            HorizontalCalendar.this.handler.sendMessage(new Message());
            HorizontalCalendar.this.calendarView.addOnScrollListener(HorizontalCalendar.this.onScrollListener);
        }
    }

    HorizontalCalendar(Builder builder) {
        this.rootView = builder.rootView;
        this.calendarId = builder.viewId;
        this.textColorNormal = builder.textColorNormal;
        this.textColorSelected = builder.textColorSelected;
        this.selectedDateBackground = builder.selectedDateBackground;
        this.selectorColor = builder.selectorColor;
        this.formatDayName = builder.formatDayName;
        this.formatDayNumber = builder.formatDayNumber;
        this.formatMonth = builder.formatMonth;
        this.textSizeMonthName = builder.textSizeMonthName;
        this.textSizeDayNumber = builder.textSizeDayNumber;
        this.textSizeDayName = builder.textSizeDayName;
        this.numberOfDatesOnScreen = builder.numberOfDatesOnScreen;
        this.dateStartCalendar = builder.dateStartCalendar;
        this.dateEndCalendar = builder.dateEndCalendar;
        this.showDayName = builder.showDayName;
        this.showMonthName = builder.showMonthName;
        this.handler = new DateHandler(this, builder.defaultSelectedDate);
    }

    void loadHorizontalCalendar() {
        this.dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        this.mListDays = new ArrayList();
        this.calendarView = (HorizontalCalendarView) this.rootView.findViewById(this.calendarId);
        this.calendarView.setHasFixedSize(true);
        this.calendarView.setHorizontalScrollBarEnabled(false);
        this.calendarView.setHorizontalCalendar(this);
        new HorizontalSnapHelper().attachToHorizontalCalendaar(this);
        hide();
        new InitializeDatesList().execute(new Void[0]);
    }

    public HorizontalCalendarListener getCalendarListener() {
        return this.calendarListener;
    }

    public void setCalendarListener(HorizontalCalendarListener calendarListener) {
        this.calendarListener = calendarListener;
    }

    public void goToday(boolean immediate) {
        selectDate(new Date(), immediate);
    }

    public void selectDate(Date date, boolean immediate) {
        if (this.loading) {
            this.handler.date = date;
            this.handler.immediate = immediate;
        } else if (immediate) {
            int datePosition = positionOfDate(date);
            centerToPositionWithNoAnimation(datePosition);
            if (this.calendarListener != null) {
                this.calendarListener.onDateSelected(date, datePosition);
            }
        } else {
            this.calendarView.setSmoothScrollSpeed(1.0f);
            centerCalendarToPosition(positionOfDate(date));
        }
    }

    void centerCalendarToPosition(int position) {
        if (position != -1) {
            int shiftCells = this.numberOfDatesOnScreen / 2;
            int centerItem = this.calendarView.getPositionOfCenterItem();
            if (position > centerItem) {
                this.calendarView.smoothScrollToPosition(position + shiftCells);
            } else if (position < centerItem) {
                this.calendarView.smoothScrollToPosition(position - shiftCells);
            }
        }
    }

    private void centerToPositionWithNoAnimation(int position) {
        if (position != -1) {
            int shiftCells = this.numberOfDatesOnScreen / 2;
            int centerItem = this.calendarView.getPositionOfCenterItem();
            if (position > centerItem) {
                this.calendarView.scrollToPosition(position + shiftCells);
            } else if (position < centerItem) {
                this.calendarView.scrollToPosition(position - shiftCells);
            }
            this.calendarView.post(new C14611());
        }
    }

    public void show() {
        this.calendarView.setVisibility(0);
    }

    public void hide() {
        this.calendarView.setVisibility(4);
    }

    public void post(Runnable runnable) {
        this.calendarView.post(runnable);
    }

    @TargetApi(21)
    public void setElevation(float elevation) {
        this.calendarView.setElevation(elevation);
    }

    public Date getSelectedDate() {
        return (Date) this.mListDays.get(this.calendarView.getPositionOfCenterItem());
    }

    public int getSelectedDatePosition() {
        return this.calendarView.getPositionOfCenterItem();
    }

    public Date getDateAt(int position) throws IndexOutOfBoundsException {
        return this.mCalendarAdapter.getItem(position);
    }

    public boolean contains(Date date) {
        return this.mListDays.contains(date);
    }

    public Date getDateStartCalendar() {
        return this.dateStartCalendar;
    }

    public Date getDateEndCalendar() {
        return this.dateEndCalendar;
    }

    public String getFormatDayName() {
        return this.formatDayName;
    }

    public String getFormatDayNumber() {
        return this.formatDayNumber;
    }

    public String getFormatMonth() {
        return this.formatMonth;
    }

    public boolean isShowDayName() {
        return this.showDayName;
    }

    public boolean isShowMonthName() {
        return this.showMonthName;
    }

    public int getNumberOfDatesOnScreen() {
        return this.numberOfDatesOnScreen;
    }

    public Drawable getSelectedDateBackground() {
        return this.selectedDateBackground;
    }

    public void setSelectedDateBackground(Drawable selectedDateBackground) {
        this.selectedDateBackground = selectedDateBackground;
    }

    public int getTextColorNormal() {
        return this.textColorNormal;
    }

    public void setTextColorNormal(int textColorNormal) {
        this.textColorNormal = textColorNormal;
    }

    public int getTextColorSelected() {
        return this.textColorSelected;
    }

    public void setTextColorSelected(int textColorSelected) {
        this.textColorSelected = textColorSelected;
    }

    public Integer getSelectorColor() {
        return this.selectorColor;
    }

    public void setSelectorColor(int selectorColor) {
        this.selectorColor = Integer.valueOf(selectorColor);
    }

    public float getTextSizeMonthName() {
        return this.textSizeMonthName;
    }

    public void setTextSizeMonthName(float textSizeMonthName) {
        this.textSizeMonthName = textSizeMonthName;
    }

    public float getTextSizeDayNumber() {
        return this.textSizeDayNumber;
    }

    public void setTextSizeDayNumber(float textSizeDayNumber) {
        this.textSizeDayNumber = textSizeDayNumber;
    }

    public float getTextSizeDayName() {
        return this.textSizeDayName;
    }

    public void setTextSizeDayName(float textSizeDayName) {
        this.textSizeDayName = textSizeDayName;
    }

    public int positionOfDate(Date date) {
        if (date.after(this.dateEndCalendar) || date.before(this.dateStartCalendar)) {
            return -1;
        }
        if (isDatesDaysEquals(date, this.dateStartCalendar)) {
            return 0;
        }
        if (isDatesDaysEquals(date, this.dateEndCalendar)) {
            return this.mListDays.size() - 1;
        }
        return ((int) ((date.getTime() - this.dateStartCalendar.getTime()) / 86400000)) + 2;
    }

    public boolean isDatesDaysEquals(Date date1, Date date2) {
        return this.dateFormat.format(date1).equals(this.dateFormat.format(date2));
    }
}
