package com.apps.smartschoolmanagement.activities;

import android.animation.Animator;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build.VERSION;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager.widget.ViewPager.PageTransformer;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;

import com.apps.smartschoolmanagement.R;
import com.apps.smartschoolmanagement.adapters.FrescoImageAdapter;
import com.apps.smartschoolmanagement.adapters.ImagePagerAdapter;
import com.apps.smartschoolmanagement.utils.BaseFinishActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerActivity extends BaseFinishActivity {
    public static final String EXTRA_CIRCULAR_REVEAL_X = "EXTRA_CIRCULAR_REVEAL_X";
    public static final String EXTRA_CIRCULAR_REVEAL_Y = "EXTRA_CIRCULAR_REVEAL_Y";
    ArrayList<Bitmap> bitmaps = new ArrayList();
    int[] images = null;
    ArrayList<String> paths = new ArrayList();
    int position;
    private int revealX;
    private int revealY;
    View rootLayout;

    /* renamed from: com.apps.smartschoolmanagement.activities.ViewPagerActivity$1 */
    class C13091 implements OnGlobalLayoutListener {
        C13091() {
        }

        public void onGlobalLayout() {
            ViewPagerActivity.this.revealActivity(ViewPagerActivity.this.revealX, ViewPagerActivity.this.revealY);
            ViewPagerActivity.this.rootLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
        }
    }

    public class ZoomOutPageTransformer implements PageTransformer {
        private static final float MIN_ALPHA = 0.5f;
        private static final float MIN_SCALE = 0.85f;

        public void transformPage(View view, float position) {
            int pageWidth = view.getWidth();
            int pageHeight = view.getHeight();
            if (position < -1.0f) {
                view.setAlpha(0.0f);
            } else if (position <= 1.0f) {
                float scaleFactor = Math.max(MIN_SCALE, 1.0f - Math.abs(position));
                float vertMargin = (((float) pageHeight) * (1.0f - scaleFactor)) / 2.0f;
                float horzMargin = (((float) pageWidth) * (1.0f - scaleFactor)) / 2.0f;
                if (position < 0.0f) {
                    view.setTranslationX(horzMargin - (vertMargin / 2.0f));
                } else {
                    view.setTranslationX((-horzMargin) + (vertMargin / 2.0f));
                }
                view.setScaleX(scaleFactor);
                view.setScaleY(scaleFactor);
                view.setAlpha((((scaleFactor - MIN_SCALE) / 0.14999998f) * MIN_ALPHA) + MIN_ALPHA);
            } else {
                view.setAlpha(0.0f);
            }
        }
    }

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewpager);
        Intent intent = getIntent();
        this.rootLayout = findViewById(R.id.root_layout);
        if (savedInstanceState == null && VERSION.SDK_INT >= 21 && intent.hasExtra("EXTRA_CIRCULAR_REVEAL_X") && intent.hasExtra("EXTRA_CIRCULAR_REVEAL_Y")) {
            this.rootLayout.setVisibility(4);
            this.revealX = intent.getIntExtra("EXTRA_CIRCULAR_REVEAL_X", 0);
            this.revealY = intent.getIntExtra("EXTRA_CIRCULAR_REVEAL_Y", 0);
            ViewTreeObserver viewTreeObserver = this.rootLayout.getViewTreeObserver();
            if (viewTreeObserver.isAlive()) {
                viewTreeObserver.addOnGlobalLayoutListener(new C13091());
            }
        } else {
            this.rootLayout.setVisibility(0);
        }
        Intent p = getIntent();
        this.position = p.getExtras().getInt("id");
        if (p.getStringArrayListExtra("paths") != null) {
            this.paths = p.getStringArrayListExtra("paths");
        }
        if (p.getIntArrayExtra("images") != null) {
            this.images = p.getIntArrayExtra("images");
        }
        if (p.getIntArrayExtra("bitmaps") != null) {
            this.bitmaps = p.getParcelableArrayListExtra("bitmaps");
        }
        FrescoImageAdapter imageAdapter = new FrescoImageAdapter(this.paths, this);
        List<ImageView> images = new ArrayList();
        for (int i = 0; i < imageAdapter.getCount(); i++) {
            ImageView imageView = new ImageView(this);
            Glide.with(this).load((String) imageAdapter.paths.get(i)).fitCenter().placeholder(R.drawable.loading11).crossFade().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(imageView);
            images.add(imageView);
        }
        ViewPager viewpager = (ViewPager) findViewById(R.id.viewpager);
        viewpager.setAdapter(new ImagePagerAdapter(images));
        viewpager.setCurrentItem(this.position);
        viewpager.setPageTransformer(true, new ZoomOutPageTransformer());
    }

    protected void revealActivity(int x, int y) {
        if (VERSION.SDK_INT >= 21) {
            Animator circularReveal = ViewAnimationUtils.createCircularReveal(this.rootLayout, x, y, 0.0f, (float) (((double) Math.max(this.rootLayout.getWidth(), this.rootLayout.getHeight())) * 1.1d));
            circularReveal.setDuration(400);
            circularReveal.setInterpolator(new AccelerateInterpolator());
            this.rootLayout.setVisibility(0);
            circularReveal.start();
            return;
        }
        finish();
    }
}
