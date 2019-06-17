package com.apps.smartschoolmanagement.adapters;

import android.content.Context;
import android.content.Intent;
import androidx.cardview.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.apps.smartschoolmanagement.R;
import com.apps.smartschoolmanagement.activities.AssignmentStatusActivity;
import com.apps.smartschoolmanagement.models.ListData;
import java.util.ArrayList;

public class TeacherAssignmentsAdapter extends BaseAdapter {
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

    public TeacherAssignmentsAdapter(Context c, int resource, ArrayList<ListData> values) {
        this.mContext = c;
        this.ids = values;
        this.resId = resource;
    }

    public int getCount() {
        return this.ids.size();
    }

    public Object getItem(int i) {
        return this.ids.get(i);
    }

    public long getItemId(int i) {
        return (long) i;
    }

    public View getView(int i, View convertView, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        View result;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(this.mContext).inflate(this.resId, viewGroup, false);
            viewHolder.assignment_assignment = (TextView) convertView.findViewById(R.id.assignment);
            viewHolder.assignment_subject = (TextView) convertView.findViewById(R.id.subject_name);
            viewHolder.assignment_submitdate = (TextView) convertView.findViewById(R.id.submission_date);
            viewHolder.cardView = (CardView) convertView.findViewById(R.id.cardview);
            convertView.findViewById(R.id.layout_status).setVisibility(8);
            result = convertView;
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }
        final ListData data = (ListData) getItem(i);
        if (viewHolder.assignment_assignment != null) {
            viewHolder.assignment_assignment.setText(data.getAssignment_assignment());
        }
        if (viewHolder.assignment_subject != null) {
            viewHolder.assignment_subject.setText(data.getAssignment_subject());
        }
        if (viewHolder.assignment_submitdate != null) {
            viewHolder.assignment_submitdate.setText(data.getAssignment_submit_date());
        }
        if (viewHolder.cardView != null) {
            viewHolder.cardView.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    Intent intent = new Intent(TeacherAssignmentsAdapter.this.mContext, AssignmentStatusActivity.class);
                    intent.putExtra("assignment_id", data.getAssignment_id());
                    TeacherAssignmentsAdapter.this.mContext.startActivity(intent);
                }
            });
        }
        return result;
    }
}
