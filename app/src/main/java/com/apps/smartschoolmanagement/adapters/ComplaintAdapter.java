package com.apps.smartschoolmanagement.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.apps.smartschoolmanagement.R;
import com.apps.smartschoolmanagement.models.ListData;

import java.util.ArrayList;

public class ComplaintAdapter extends BaseAdapter {
    private ArrayList<ListData> ids = null;
    private Context mContext = null;
    private int resId;

    private static class ViewHolder {
        TextView complaint_title;
        TextView assignment_subject;
        TextView complaint_desc;
        CardView cardView;

        private ViewHolder() {
        }
    }

    public ComplaintAdapter(Context c, int resource, ArrayList<ListData> values) {
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
        ComplaintAdapter.ViewHolder viewHolder;
        View result;

        if (convertView == null) {
            viewHolder = new ComplaintAdapter.ViewHolder();
            convertView = LayoutInflater.from(this.mContext).inflate(this.resId, parent, false);
            viewHolder.complaint_title = (TextView) convertView.findViewById(R.id.complaint_title);
            viewHolder.complaint_desc = (TextView) convertView.findViewById(R.id.complaint_desc);
            viewHolder.cardView = (CardView) convertView.findViewById(R.id.cardview);
            convertView.findViewById(R.id.layout_status).setVisibility(8);
            result = convertView;
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ComplaintAdapter.ViewHolder) convertView.getTag();
            result = convertView;
        }
        final ListData data = (ListData) getItem(position);
        if (viewHolder.complaint_title != null) {
            viewHolder.complaint_title.setText(data.getComplaint_title());
        }
        if (viewHolder.complaint_desc != null) {
            viewHolder.complaint_desc.setText(data.getComplaint_desc());
        }
        return result;
    }
}
