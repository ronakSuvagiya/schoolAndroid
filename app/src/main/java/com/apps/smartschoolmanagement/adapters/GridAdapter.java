package com.apps.smartschoolmanagement.adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.apps.smartschoolmanagement.R;
import com.apps.smartschoolmanagement.models.PhotoAlbum;
import java.util.ArrayList;

public class GridAdapter extends BaseAdapter {
    LayoutInflater inflater;
    int layoutResourceId;
    private Context mContext;
    ArrayList<String> paths = new ArrayList();
    ArrayList<PhotoAlbum> values = new ArrayList();

    static class ViewHolder {
        ImageView imageView;
        TextView titleTextView;

        ViewHolder() {
        }
    }

    public GridAdapter(Context c, ArrayList<PhotoAlbum> values, int resourceId) {
        this.mContext = c;
        this.values = values;
        this.layoutResourceId = resourceId;
        this.inflater = (LayoutInflater) this.mContext.getSystemService("layout_inflater");
    }

    public GridAdapter(Context c, int resourceId, ArrayList<String> values) {
        this.mContext = c;
        this.paths = values;
        this.layoutResourceId = resourceId;
        this.inflater = (LayoutInflater) this.mContext.getSystemService("layout_inflater");
    }

    public int getCount() {
        if (this.values.size() > 0) {
            return this.values.size();
        }
        return this.paths.size();
    }

    public Object getItem(int position) {
        if (this.values.size() > 0) {
            return this.values.get(position);
        }
        return this.paths.get(position);
    }

    public long getItemId(int position) {
        return (long) position;
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
        if (this.values.size() > 0) {
            PhotoAlbum data = (PhotoAlbum) getItem(position);
            if (holder.titleTextView != null) {
                holder.titleTextView.setText(data.getTitle());
            }
            if (holder.imageView != null) {
//                Glide.with(this.mContext).load(Uri.parse(data.getPathList()[0])).centerCrop().placeholder(R.drawable.loading11).crossFade().override(280, 320).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.imageView);
            }
        } else if (this.paths.size() > 0 && holder.imageView != null) {
//            Glide.with(this.mContext).load(Uri.parse((String) this.paths.get(position))).centerCrop().placeholder(R.drawable.loading11).crossFade().override(280, 320).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.imageView);
        }
        return convertView;
    }
}
