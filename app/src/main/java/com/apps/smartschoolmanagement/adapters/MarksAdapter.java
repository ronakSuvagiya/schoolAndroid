package com.apps.smartschoolmanagement.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.apps.smartschoolmanagement.R;
import com.apps.smartschoolmanagement.models.Marks;
import java.util.ArrayList;

public class MarksAdapter extends BaseAdapter {
    private ArrayList<Marks> ids = null;
    private Context mContext = null;
    private int resId;

    private static class ViewHolder {
        TextView cgpa;
        TextView obtained;
        TextView result;
        TextView subjectName;
        TextView total;

        private ViewHolder() {
        }
    }

    public MarksAdapter(Context c, int resource, ArrayList<Marks> values) {
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
            viewHolder.subjectName = (TextView) convertView.findViewById(R.id.subject);
            viewHolder.obtained = (TextView) convertView.findViewById(R.id.obtained);
            viewHolder.total = (TextView) convertView.findViewById(R.id.total);
            viewHolder.result = (TextView) convertView.findViewById(R.id.result);
            viewHolder.cgpa = (TextView) convertView.findViewById(R.id.grade);
            result = convertView;
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }
        Marks data = (Marks) getItem(i);
        if (viewHolder.subjectName != null) {
            viewHolder.subjectName.setText(data.getSubject_name());
        }
        if (viewHolder.obtained != null) {
            viewHolder.obtained.setText(data.getObtained());
        }
        if (viewHolder.total != null) {
            viewHolder.total.setText(data.getTotal());
        }
        if (viewHolder.result != null) {
            viewHolder.result.setText(data.getResult());
        }
        if (viewHolder.cgpa != null) {
            viewHolder.cgpa.setText(data.getCgpa());
        }
        return result;
    }
}
