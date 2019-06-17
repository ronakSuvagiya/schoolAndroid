package com.apps.smartschoolmanagement.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.apps.smartschoolmanagement.R;
import com.apps.smartschoolmanagement.models.PhotoAlbum;
import com.apps.smartschoolmanagement.models.UserStaticData;
import java.util.ArrayList;

public class GridViewAdapter extends BaseAdapter {
    private int[] Imageid = null;
    LayoutInflater inflater;
    int layoutResourceId;
    private Context mContext;
    ArrayList<PhotoAlbum> values = new ArrayList();
    private String[] web;

    static class ViewHolder {
        ImageView imageView;
        TextView titleTextView;

        ViewHolder() {
        }
    }

    public GridViewAdapter(Context c, String[] web, int[] Imageid, int resourceId) {
        this.mContext = c;
        this.Imageid = Imageid;
        this.web = web;
        this.layoutResourceId = resourceId;
        this.inflater = (LayoutInflater) this.mContext.getSystemService("layout_inflater");
    }

    public GridViewAdapter(Context c, ArrayList<PhotoAlbum> values, int resourceId) {
        this.mContext = c;
        this.values = values;
        this.layoutResourceId = resourceId;
        this.inflater = (LayoutInflater) this.mContext.getSystemService("layout_inflater");
    }

    public int getCount() {
        return this.web.length;
    }

    public Object getItem(int position) {
        return Integer.valueOf(position);
    }

    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = this.inflater.inflate(this.layoutResourceId, parent, false);
            holder = new ViewHolder();
            holder.titleTextView = (TextView) convertView.findViewById(R.id.image_title);
            holder.imageView = (ImageView) convertView.findViewById(R.id.image);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.titleTextView.setText(this.web[position]);
        if (this.Imageid != null) {
            holder.imageView.setImageResource(this.Imageid[position]);
        } else if (UserStaticData.imageIds != null) {
            holder.imageView.setImageResource(((Integer)UserStaticData.imageIds.get(this.web[position])).intValue());
        }
        return convertView;
    }
}
