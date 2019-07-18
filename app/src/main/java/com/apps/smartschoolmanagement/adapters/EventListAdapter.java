package com.apps.smartschoolmanagement.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.apps.smartschoolmanagement.R;
import com.apps.smartschoolmanagement.activities.HolidayListActivity;
import com.apps.smartschoolmanagement.models.ListData;
import com.apps.smartschoolmanagement.models.UserStaticData;
import com.apps.smartschoolmanagement.utils.JsonClass;
import com.apps.smartschoolmanagement.utils.URLs;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class EventListAdapter extends BaseAdapter {
    private ArrayList<ListData> ids = null;
    private Context mContext = null;
    private int resId;

    private static class ViewHolder {
        TextView assignment_assignment;
        TextView assignment_subject;
        TextView assignment_startdate;
        TextView assignment_enddate;
        TextView assignment_starttime;

        CardView cardView;

        private ViewHolder() {
        }
    }

    public EventListAdapter(Context c, int resource, ArrayList<ListData> values) {
        this.mContext = c;
        this.ids = values;
        this.resId = resource;
    }
    @Override
    public int getCount() {
        return this.ids.size();
    }

    @Override
    public Object getItem(int position) {
        return this.ids.get(position);
    }

    @Override
    public long getItemId(int position) {
        return (long) position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        EventListAdapter.ViewHolder viewHolder;
        View result;

        if (convertView == null) {
            viewHolder = new EventListAdapter.ViewHolder();
            convertView = LayoutInflater.from(this.mContext).inflate(this.resId, parent, false);
            viewHolder.assignment_subject = (TextView) convertView.findViewById(R.id.subject_name);
            viewHolder.assignment_startdate = (TextView) convertView.findViewById(R.id.start_date);
            viewHolder.assignment_enddate = (TextView) convertView.findViewById(R.id.end_date);
            viewHolder.assignment_starttime = (TextView) convertView.findViewById(R.id.start_time);

            viewHolder.cardView = (CardView) convertView.findViewById(R.id.cardview);
            convertView.findViewById(R.id.layout_status).setVisibility(8);
            result = convertView;
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (EventListAdapter.ViewHolder) convertView.getTag();
            result = convertView;
        }
        final ListData data = (ListData) getItem(position);
        if (viewHolder.assignment_subject != null) {
            viewHolder.assignment_subject.setText(data.getAssignment_subject());
        }
        if (viewHolder.assignment_startdate != null) {
            viewHolder.assignment_startdate.setText(data.getAssignment_submit_date());
        }
        if (viewHolder.assignment_enddate != null) {
            viewHolder.assignment_enddate.setText(data.getAssignment_end_date());
        }
        if (viewHolder.assignment_starttime != null) {
            viewHolder.assignment_starttime.setText(data.getAssignment_start_time());
        }
        return result;
    }
}
