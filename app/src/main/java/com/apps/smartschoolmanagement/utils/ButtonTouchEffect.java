package com.apps.smartschoolmanagement.utils;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.Context;
import android.graphics.Canvas;
import androidx.appcompat.widget.AppCompatButton;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import com.apps.smartschoolmanagement.R;

public class ButtonTouchEffect extends AppCompatButton {

    /* renamed from: com.apps.smartschoolmanagement.utils.ButtonTouchEffect$1 */
    class C13891 extends SimpleOnGestureListener {
        C13891() {
        }

        public boolean onSingleTapConfirmed(MotionEvent e) {
            return true;
        }

        public void onLongPress(MotionEvent e) {
            super.onLongPress(e);
        }

        public boolean onDoubleTap(MotionEvent e) {
            return super.onDoubleTap(e);
        }
    }

    public ButtonTouchEffect(Context context) {
        super(context);
    }

    public ButtonTouchEffect(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ButtonTouchEffect(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    protected void onDraw(Canvas canvas) {
        final GestureDetector gestureDetector = new GestureDetector(getContext(), new C13891());
        setOnTouchListener(new OnTouchListener() {
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case 0:
                        AnimatorSet reducer = (AnimatorSet) AnimatorInflater.loadAnimator(ButtonTouchEffect.this.getContext(), R.animator.reduce_size);
                        reducer.setTarget(view);
                        reducer.start();
                        break;
                    case 1:
                        AnimatorSet regainer = (AnimatorSet) AnimatorInflater.loadAnimator(ButtonTouchEffect.this.getContext(), R.animator.regain_size);
                        regainer.setTarget(view);
                        regainer.start();
                        break;
                    case 3:
                        AnimatorSet regainer1 = (AnimatorSet) AnimatorInflater.loadAnimator(ButtonTouchEffect.this.getContext(), R.animator.regain_size);
                        regainer1.setTarget(view);
                        regainer1.start();
                        break;
                }
                return gestureDetector.onTouchEvent(motionEvent);
            }
        });
        super.onDraw(canvas);
    }
}
