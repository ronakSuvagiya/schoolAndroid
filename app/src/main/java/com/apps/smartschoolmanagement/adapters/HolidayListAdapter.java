package com.apps.smartschoolmanagement.adapters;

import androidx.cardview.widget.CardView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.apps.smartschoolmanagement.R;
import com.apps.smartschoolmanagement.models.ListData;

import java.util.ArrayList;

public class HolidayListAdapter extends BaseAdapter {
    private ArrayList<ListData> ids = null;
    private Context mContext = null;
    private int resId;

    private static class ViewHolder {
        TextView assignment_assignment;
        TextView assignment_subject;
        TextView assignment_submitdate;
        CardView cardView;

        private ViewHolder() {
        }
    }

    public HolidayListAdapter(Context c, int resource, ArrayList<ListData> values) {
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
        HolidayListAdapter.ViewHolder viewHolder;
        View result;

        if (convertView == null) {
            viewHolder = new HolidayListAdapter.ViewHolder();
            convertView = LayoutInflater.from(this.mContext).inflate(this.resId, parent, false);
            viewHolder.assignment_subject = (TextView) convertView.findViewById(R.id.subject_name);
            viewHolder.assignment_submitdate = (TextView) convertView.findViewById(R.id.submission_date);
            viewHolder.cardView = (CardView) convertView.findViewById(R.id.cardview);
            convertView.findViewById(R.id.layout_status).setVisibility(8);
            result = convertView;
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (HolidayListAdapter.ViewHolder) convertView.getTag();
            result = convertView;
        }
        final ListData data = (ListData) getItem(position);
        if (viewHolder.assignment_subject != null) {
            viewHolder.assignment_subject.setText(data.getAssignment_subject());
        }
        if (viewHolder.assignment_submitdate != null) {
            viewHolder.assignment_submitdate.setText(data.getAssignment_submit_date());
        }
        return result;
    }
}
