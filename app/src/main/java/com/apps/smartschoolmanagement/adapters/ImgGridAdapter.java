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
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

public class ImgGridAdapter  extends BaseAdapter {
    LayoutInflater inflater;
    int layoutResourceId;
    private Context mContext;
    ArrayList<String> paths = new ArrayList();
    ArrayList<PhotoAlbum> values = new ArrayList();

    static class ViewHolder {
        ImageView imageView;
        ViewHolder() {
        }
    }

    public ImgGridAdapter(Context c, int resourceId, ArrayList<String> values) {
        this.mContext = c;
        this.paths = values;
        this.layoutResourceId = resourceId;
        this.inflater = (LayoutInflater) this.mContext.getSystemService("layout_inflater");
    }

    public int getCount() {

        return this.paths.size(); }

    public Object getItem(int position) {
        return this.paths.get(position);
    }

    public long getItemId(int position) {
        return (long) position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ImgGridAdapter.ViewHolder holder;
        if (convertView == null) {
            convertView = this.inflater.inflate(this.layoutResourceId, parent, false);
            holder = new ImgGridAdapter.ViewHolder();
            holder.imageView = (ImageView) convertView.findViewById(R.id.image);
            convertView.setTag(holder);
        } else {
            holder = (ImgGridAdapter.ViewHolder) convertView.getTag();
        }
            if (holder.imageView != null) {
                Glide.with(this.mContext).load(paths.get(position)).centerCrop().placeholder(R.drawable.loading11).crossFade().override(280, 320).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.imageView);
            }
        return convertView;
    }
}
