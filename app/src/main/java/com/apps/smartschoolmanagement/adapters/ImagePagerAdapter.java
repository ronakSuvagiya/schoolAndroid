package com.apps.smartschoolmanagement.adapters;

import androidx.viewpager.widget.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import java.util.List;

public class ImagePagerAdapter extends PagerAdapter {
    private List<ImageView> images;

    public ImagePagerAdapter(List<ImageView> images) {
        this.images = images;
    }

    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imageView = (ImageView) this.images.get(position);
        container.addView(imageView);
        return imageView;
    }

    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) this.images.get(position));
    }

    public int getCount() {
        return this.images.size();
    }

    public boolean isViewFromObject(View view, Object o) {
        return view == o;
    }
}
