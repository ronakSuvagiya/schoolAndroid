package com.apps.smartschoolmanagement.utils.horizontalcalendar;

import android.content.Context;
import android.graphics.Point;
import android.os.Build.VERSION;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import android.text.format.DateFormat;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;
import com.apps.smartschoolmanagement.R;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

class HorizontalCalendarAdapter extends Adapter<HorizontalCalendarAdapter.DayViewHolder> {
    private final Context context;
    private ArrayList<Date> datesList;
    private HorizontalCalendar horizontalCalendar;
    private HorizontalCalendarView horizontalCalendarView;
    private int numberOfDates = this.horizontalCalendar.getNumberOfDatesOnScreen();
    private int widthCell;

    static class DayViewHolder extends ViewHolder {
        View layoutBackground;
        View rootView;
        View selectionView;
        TextView txtDayName;
        TextView txtDayNumber;
        TextView txtMonthName;

        public DayViewHolder(View rootView) {
            super(rootView);
            this.rootView = rootView;
            this.txtDayNumber = (TextView) rootView.findViewById(R.id.dayNumber);
            this.txtDayName = (TextView) rootView.findViewById(R.id.dayName);
            this.txtMonthName = (TextView) rootView.findViewById(R.id.monthName);
            this.layoutBackground = rootView.findViewById(R.id.layoutBackground);
            this.selectionView = rootView.findViewById(R.id.selection_view);
        }
    }

    HorizontalCalendarAdapter(HorizontalCalendarView horizontalCalendarView, ArrayList<Date> datesList) {
        this.horizontalCalendarView = horizontalCalendarView;
        this.context = horizontalCalendarView.getContext();
        this.datesList = datesList;
        this.horizontalCalendar = horizontalCalendarView.getHorizontalCalendar();
        calculateCellWidth();
    }

    public DayViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        View convertView = LayoutInflater.from(this.context).inflate(R.layout.item_calendar, viewGroup, false);
        convertView.setMinimumWidth(this.widthCell);
        final DayViewHolder holder = new DayViewHolder(convertView);
        Integer selectorColor = this.horizontalCalendar.getSelectorColor();
        if (selectorColor != null) {
            holder.selectionView.setBackgroundColor(selectorColor.intValue());
        }
        holder.rootView.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (holder.getAdapterPosition() != -1) {
                    Date date = (Date) HorizontalCalendarAdapter.this.datesList.get(holder.getAdapterPosition());
                    if (!date.before(HorizontalCalendarAdapter.this.horizontalCalendar.getDateStartCalendar()) && !date.after(HorizontalCalendarAdapter.this.horizontalCalendar.getDateEndCalendar())) {
                        HorizontalCalendarAdapter.this.horizontalCalendarView.setSmoothScrollSpeed(0.6f);
                        HorizontalCalendarAdapter.this.horizontalCalendar.centerCalendarToPosition(holder.getAdapterPosition());
                    }
                }
            }
        });
        holder.rootView.setOnLongClickListener(new OnLongClickListener() {
            public boolean onLongClick(View v) {
                Date date = (Date) HorizontalCalendarAdapter.this.datesList.get(holder.getAdapterPosition());
                HorizontalCalendarListener calendarListener = HorizontalCalendarAdapter.this.horizontalCalendar.getCalendarListener();
                if (calendarListener == null || date.before(HorizontalCalendarAdapter.this.horizontalCalendar.getDateStartCalendar()) || date.after(HorizontalCalendarAdapter.this.horizontalCalendar.getDateEndCalendar())) {
                    return false;
                }
                return calendarListener.onDateLongClicked(date, holder.getAdapterPosition());
            }
        });
        return holder;
    }

    public void onBindViewHolder(DayViewHolder holder, int position) {
        Date day = (Date) this.datesList.get(position);
        if (position == this.horizontalCalendar.getSelectedDatePosition()) {
            holder.txtDayNumber.setTextColor(this.horizontalCalendar.getTextColorSelected());
            holder.txtMonthName.setTextColor(this.horizontalCalendar.getTextColorSelected());
            holder.txtDayName.setTextColor(this.horizontalCalendar.getTextColorSelected());
            if (VERSION.SDK_INT >= 16) {
                holder.layoutBackground.setBackground(this.horizontalCalendar.getSelectedDateBackground());
            } else {
                holder.layoutBackground.setBackgroundDrawable(this.horizontalCalendar.getSelectedDateBackground());
            }
            holder.selectionView.setVisibility(0);
        } else {
            holder.txtDayNumber.setTextColor(this.horizontalCalendar.getTextColorNormal());
            holder.txtMonthName.setTextColor(this.horizontalCalendar.getTextColorNormal());
            holder.txtDayName.setTextColor(this.horizontalCalendar.getTextColorNormal());
            if (VERSION.SDK_INT >= 16) {
                holder.layoutBackground.setBackground(null);
            } else {
                holder.layoutBackground.setBackgroundDrawable(null);
            }
            holder.selectionView.setVisibility(4);
        }
        holder.txtDayNumber.setText(DateFormat.format(this.horizontalCalendar.getFormatDayNumber(), day).toString());
        holder.txtDayNumber.setTextSize(2, this.horizontalCalendar.getTextSizeDayNumber());
        if (this.horizontalCalendar.isShowDayName()) {
            holder.txtDayName.setText(DateFormat.format(this.horizontalCalendar.getFormatDayName(), day).toString());
            holder.txtDayName.setTextSize(2, this.horizontalCalendar.getTextSizeDayName());
        } else {
            holder.txtDayName.setVisibility(8);
        }
        if (this.horizontalCalendar.isShowMonthName()) {
            holder.txtMonthName.setText(DateFormat.format(this.horizontalCalendar.getFormatMonth(), day).toString());
            holder.txtMonthName.setTextSize(2, this.horizontalCalendar.getTextSizeMonthName());
            return;
        }
        holder.txtMonthName.setVisibility(8);
    }

    public void onBindViewHolder(DayViewHolder holder, int position, List<Object> payloads) {
        if (payloads == null || payloads.isEmpty()) {
            onBindViewHolder(holder, position);
        } else if (position == this.horizontalCalendar.getSelectedDatePosition()) {
            holder.txtDayNumber.setTextColor(this.horizontalCalendar.getTextColorSelected());
            holder.txtMonthName.setTextColor(this.horizontalCalendar.getTextColorSelected());
            holder.txtDayName.setTextColor(this.horizontalCalendar.getTextColorSelected());
            if (VERSION.SDK_INT >= 16) {
                holder.layoutBackground.setBackground(this.horizontalCalendar.getSelectedDateBackground());
            } else {
                holder.layoutBackground.setBackgroundDrawable(this.horizontalCalendar.getSelectedDateBackground());
            }
            holder.selectionView.setVisibility(0);
        } else {
            holder.txtDayNumber.setTextColor(this.horizontalCalendar.getTextColorNormal());
            holder.txtMonthName.setTextColor(this.horizontalCalendar.getTextColorNormal());
            holder.txtDayName.setTextColor(this.horizontalCalendar.getTextColorNormal());
            if (VERSION.SDK_INT >= 16) {
                holder.layoutBackground.setBackground(null);
            } else {
                holder.layoutBackground.setBackgroundDrawable(null);
            }
            holder.selectionView.setVisibility(4);
        }
    }

    public int getItemCount() {
        return this.datesList.size();
    }

    public Date getItem(int position) {
        return (Date) this.datesList.get(position);
    }

    public long getItemId(int position) {
        return (long) position;
    }

    private void calculateCellWidth() {
        int widthScreen;
        Display display = ((WindowManager) this.context.getSystemService("window")).getDefaultDisplay();
        Point size = new Point();
        if (VERSION.SDK_INT >= 13) {
            display.getSize(size);
            widthScreen = size.x;
        } else {
            widthScreen = display.getWidth();
        }
        this.widthCell = widthScreen / this.numberOfDates;
    }
}
