package com.apps.smartschoolmanagement.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import java.util.ArrayList;

public class ImageAdapter extends BaseAdapter {
    public ArrayList<Bitmap> bitmaps = new ArrayList();
    private Context mContext;
    public int[] mThumbIds = null;
    public ArrayList<String> paths = new ArrayList();

    public ImageAdapter(Context c, int[] ids) {
        this.mContext = c;
        this.mThumbIds = ids;
    }

    public ImageAdapter(Context c, ArrayList<Bitmap> ids) {
        this.mContext = c;
        this.bitmaps = ids;
    }

    public ImageAdapter(ArrayList<String> paths, Context c) {
        this.mContext = c;
        this.paths = paths;
    }

    public int getCount() {
        if (this.mThumbIds != null) {
            return this.mThumbIds.length;
        }
        return this.bitmaps.size();
    }

    public Object getItem(int position) {
        if (this.mThumbIds != null) {
            return Integer.valueOf(this.mThumbIds[position]);
        }
        return this.bitmaps.get(position);
    }

    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            imageView = new ImageView(this.mContext);
            imageView.setScaleType(ScaleType.CENTER_CROP);
        } else {
            imageView = (ImageView) convertView;
        }
        if (this.mThumbIds != null) {
            imageView.setImageResource(this.mThumbIds[position]);
        } else if (this.bitmaps.size() > 0) {
            imageView.setImageBitmap((Bitmap) this.bitmaps.get(position));
        }
        return imageView;
    }
}
