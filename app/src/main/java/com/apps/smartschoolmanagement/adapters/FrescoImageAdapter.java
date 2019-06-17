package com.apps.smartschoolmanagement.adapters;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import java.util.ArrayList;

public class FrescoImageAdapter extends BaseAdapter {
    private Context mContext;
    public ArrayList<String> paths = new ArrayList();

    public FrescoImageAdapter(ArrayList<String> paths, Context c) {
        this.mContext = c;
        this.paths = paths;
    }

    public int getCount() {
        return this.paths.size();
    }

    public Object getItem(int position) {
        return this.paths.get(position);
    }

    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView simpleDraweeView;
        if (convertView == null) {
            simpleDraweeView = new ImageView(this.mContext);
            simpleDraweeView.setScaleType(ScaleType.CENTER_CROP);
        } else {
            simpleDraweeView = (ImageView) convertView;
        }
        simpleDraweeView.setImageURI(Uri.parse((String) this.paths.get(position)));
        return simpleDraweeView;
    }
}
