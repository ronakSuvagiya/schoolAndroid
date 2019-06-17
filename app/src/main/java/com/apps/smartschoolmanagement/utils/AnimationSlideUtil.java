package com.apps.smartschoolmanagement.utils;

import android.content.Context;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AnimationUtils;
import com.apps.smartschoolmanagement.R;

public class AnimationSlideUtil {
    public static void slideInFromLeft(Context context, View view) {
        runSimpleAnimation(context, view, R.anim.slide_from_left);
    }

    public static void slideOutToLeft(Context context, View view) {
        runSimpleAnimation(context, view, R.anim.slide_to_left);
    }

    public static void slideInFromRight(Context context, View view) {
        runSimpleAnimation(context, view, R.anim.slide_from_right);
    }

    public static void slideOutToRight(Context context, View view) {
        runSimpleAnimation(context, view, R.anim.slide_to_right);
    }

    public static void slideInFromTop(Context context, View view) {
        runSimpleAnimation(context, view, R.anim.slide_from_top);
    }

    public static void slideInFromBottom(Context context, View view) {
        runSimpleAnimation(context, view, R.anim.slide_from_bottom);
    }

    public static void fadeIn(Context context, View view) {
        runSimpleAnimation(context, view, R.anim.fade_in);
    }

    public static void fadeOut(Context context, View view) {
        runSimpleAnimation(context, view, R.anim.fade_out);
    }

    public static void zoomEnter(Context context, View view) {
        runSimpleAnimation(context, view, R.anim.zoom_enter);
    }

    public static void zoomExit(Context context, View view) {
        runSimpleAnimation(context, view, R.anim.zoom_exit);
    }

    public static void bounceInterpolator(Context context, View view) {
        runSimpleAnimation(context, view, R.anim.bounce);
    }

    public static void activityFade(Context context) {
        ((AppCompatActivity) context).overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    public static void activityZoom(Context context) {
        ((AppCompatActivity) context).overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
    }

    private static void runSimpleAnimation(Context context, View view, int animationId) {
        view.startAnimation(AnimationUtils.loadAnimation(context, animationId));
    }
}
