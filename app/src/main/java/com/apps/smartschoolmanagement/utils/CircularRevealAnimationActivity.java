package com.apps.smartschoolmanagement.utils;

import android.animation.Animator;
import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.animation.AccelerateInterpolator;

public abstract class CircularRevealAnimationActivity extends AppCompatActivity {
    public static final String EXTRA_CIRCULAR_REVEAL_X = "EXTRA_CIRCULAR_REVEAL_X";
    public static final String EXTRA_CIRCULAR_REVEAL_Y = "EXTRA_CIRCULAR_REVEAL_Y";
    private int revealX;
    private int revealY;

    /* renamed from: com.apps.smartschoolmanagement.utils.CircularRevealAnimationActivity$1 */
    class C13911 implements OnGlobalLayoutListener {
        C13911() {
        }

        public void onGlobalLayout() {
            CircularRevealAnimationActivity.this.revealActivity(CircularRevealAnimationActivity.this.revealX, CircularRevealAnimationActivity.this.revealY);
            CircularRevealAnimationActivity.this.getRootLayout().getViewTreeObserver().removeOnGlobalLayoutListener(this);
        }
    }

    public abstract Intent getIntent();

    public abstract View getRootLayout();

    public abstract View getView();

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        if (intent == null) {
            return;
        }
        if (savedInstanceState == null && VERSION.SDK_INT >= 21 && intent.hasExtra("EXTRA_CIRCULAR_REVEAL_X") && intent.hasExtra("EXTRA_CIRCULAR_REVEAL_Y")) {
            getRootLayout().setVisibility(4);
            this.revealX = intent.getIntExtra("EXTRA_CIRCULAR_REVEAL_X", 0);
            this.revealY = intent.getIntExtra("EXTRA_CIRCULAR_REVEAL_Y", 0);
            ViewTreeObserver viewTreeObserver = getRootLayout().getViewTreeObserver();
            if (viewTreeObserver.isAlive()) {
                viewTreeObserver.addOnGlobalLayoutListener(new C13911());
                return;
            }
            return;
        }
        getRootLayout().setVisibility(0);
    }

    public void presentActivity(View view) {
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, view, "transition");
        int revealX = (int) (view.getX() + ((float) (view.getWidth() / 2)));
        int revealY = (int) (view.getY() + ((float) (view.getHeight() / 2)));
        Intent intent = getIntent();
        intent.putExtra("EXTRA_CIRCULAR_REVEAL_X", revealX);
        intent.putExtra("EXTRA_CIRCULAR_REVEAL_Y", revealY);
        ActivityCompat.startActivity(this, intent, options.toBundle());
    }

    protected void revealActivity(int x, int y) {
        if (VERSION.SDK_INT >= 21) {
            Animator circularReveal = ViewAnimationUtils.createCircularReveal(getRootLayout(), x, y, 0.0f, (float) (((double) Math.max(getRootLayout().getWidth(), getRootLayout().getHeight())) * 1.1d));
            circularReveal.setDuration(400);
            circularReveal.setInterpolator(new AccelerateInterpolator());
            getRootLayout().setVisibility(0);
            circularReveal.start();
            return;
        }
        finish();
    }
}
