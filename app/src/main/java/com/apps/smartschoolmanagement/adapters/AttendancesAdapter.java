package com.apps.smartschoolmanagement.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.apps.smartschoolmanagement.R;
import com.apps.smartschoolmanagement.activities.AttendanceActivity;
import com.apps.smartschoolmanagement.activities.StudentAttendanceActivity;
import com.apps.smartschoolmanagement.fragments.StudentAttendance;

import java.util.List;

public class AttendancesAdapter extends ArrayAdapter {

    private int resourceLayout;
    private Context mContext;
    private List obj;
    EventListener listener;
    StudentAttendance st;

    public interface EventListener {
        void onEvent(int data);
    }

    public AttendancesAdapter(@NonNull Context context, int resource, @NonNull List objects,StudentAttendance st) {
        super(context, resource, objects);
        this.resourceLayout = resource;
        this.mContext = context;
        this.obj = objects;
        this.st =st;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(mContext);
            v = vi.inflate(resourceLayout, null);
        }


            TextView tt1 = (TextView) v.findViewById(R.id.tv_item);
            tt1.setText((CharSequence) obj.get(position));
            Switch onoff = v.findViewById(R.id.switch1);
            onoff.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                   st.myClickHandler(position,b);
                }
            });


        return v;
    }
}
