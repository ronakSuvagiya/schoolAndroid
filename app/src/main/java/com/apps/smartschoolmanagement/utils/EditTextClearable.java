package com.apps.smartschoolmanagement.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import androidx.appcompat.widget.AppCompatEditText;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;
import android.widget.EditText;
import com.apps.smartschoolmanagement.utils.TextWatcherAdapter.TextWatcherListener;

public class EditTextClearable extends AppCompatEditText implements OnTouchListener, OnFocusChangeListener, TextWatcherListener {
    /* renamed from: c */
    Context f65c;
    /* renamed from: f */
    private OnFocusChangeListener f66f;
    /* renamed from: l */
    private OnTouchListener f67l;
    private Listener listener;
    private Drawable xD;

    public interface Listener {
        void didClearText();
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public EditTextClearable(Context context) {
        super(context);
        this.f65c = context;
        init();
    }

    public EditTextClearable(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public EditTextClearable(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public void setOnTouchListener(OnTouchListener l) {
        this.f67l = l;
    }

    public void setOnFocusChangeListener(OnFocusChangeListener f) {
        this.f66f = f;
    }

    public boolean onTouch(View v, MotionEvent event) {
        if (getCompoundDrawables()[2] != null) {
            if (event.getX() > ((float) ((getWidth() - getPaddingRight()) - this.xD.getIntrinsicWidth()))) {
                if (event.getAction() != 1) {
                    return true;
                }
                setText("");
                requestFocus();
                if (this.listener == null) {
                    return true;
                }
                this.listener.didClearText();
                return true;
            }
        }
        if (v.isFocused()) {
            v.getParent().requestDisallowInterceptTouchEvent(true);
            switch (event.getAction() & 255) {
                case 1:
                    v.getParent().requestDisallowInterceptTouchEvent(false);
                    break;
            }
        }
        return this.f67l != null ? this.f67l.onTouch(v, event) : false;
    }

    public void onFocusChange(View v, boolean hasFocus) {
        boolean z = false;
        if (hasFocus) {
            if (!TextUtils.isEmpty(getText())) {
                z = true;
            }
            setClearIconVisible(z);
        } else {
            setClearIconVisible(false);
        }
        if (this.f66f != null) {
            this.f66f.onFocusChange(v, hasFocus);
        }
    }

    public void onTextChanged(EditText view, String text) {
        boolean z = true;
        if (isFocused()) {
            boolean z2;
            if (TextUtils.isEmpty(text)) {
                z2 = false;
            } else {
                z2 = true;
            }
            setClearIconVisible(z2);
        }
        if (TextUtils.isEmpty(text)) {
            z = false;
        }
        setClearIconVisible(z);
    }

    private void init() {
        this.xD = getCompoundDrawables()[2];
        if (this.xD == null) {
            this.xD = getResources().getDrawable(17301560);
            this.xD = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(((BitmapDrawable) this.xD).getBitmap(), convertDipToPixels(20.0f), convertDipToPixels(20.0f), true));
        }
        this.xD.setBounds(0, 0, this.xD.getIntrinsicWidth(), this.xD.getIntrinsicHeight());
        setClearIconVisible(false);
        super.setOnTouchListener(this);
        super.setOnFocusChangeListener(this);
        addTextChangedListener(new TextWatcherAdapter(this, this));
    }

    protected void setClearIconVisible(boolean visible) {
        setCompoundDrawables(getCompoundDrawables()[0], getCompoundDrawables()[1], visible ? this.xD : null, getCompoundDrawables()[3]);
    }

    public int convertDipToPixels(float dips) {
        return (int) ((getResources().getDisplayMetrics().density * dips) + 0.5f);
    }
}
