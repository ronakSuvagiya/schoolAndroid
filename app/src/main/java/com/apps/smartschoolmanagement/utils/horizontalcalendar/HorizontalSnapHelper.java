package com.apps.smartschoolmanagement.utils.horizontalcalendar;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.LayoutManager;
import android.view.View;

public class HorizontalSnapHelper extends LinearSnapHelper {
    private HorizontalCalendar horizontalCalendar;

    public View findSnapView(LayoutManager layoutManager) {
        View snapView = super.findSnapView(layoutManager);
        if (this.horizontalCalendar.calendarView.getScrollState() != 1) {
            int selectedItemPosition = 0;
            if (snapView == null) {
                selectedItemPosition = this.horizontalCalendar.getSelectedDatePosition();
            } else {
                int[] snapDistance = calculateDistanceToFinalSnap(layoutManager, snapView);
                if (snapDistance[0] == 0 && snapDistance[1] == 0) {
                    selectedItemPosition = layoutManager.getPosition(snapView);
                }
            }
            this.horizontalCalendar.calendarListener.onDateSelected(this.horizontalCalendar.getDateAt(selectedItemPosition), selectedItemPosition);
        }
        return snapView;
    }

    public void attachToRecyclerView(@Nullable RecyclerView recyclerView) throws IllegalStateException {
    }

    public void attachToHorizontalCalendaar(@Nullable HorizontalCalendar horizontalCalendar) throws IllegalStateException {
        this.horizontalCalendar = horizontalCalendar;
        attachToRecyclerView();
    }

    private void attachToRecyclerView() {
        super.attachToRecyclerView(this.horizontalCalendar.calendarView);
    }
}
