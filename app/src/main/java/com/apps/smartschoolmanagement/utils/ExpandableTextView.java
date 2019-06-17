package com.apps.smartschoolmanagement.utils;

import android.content.Context;
import androidx.appcompat.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import com.apps.smartschoolmanagement.R;

public class ExpandableTextView extends AppCompatTextView implements OnClickListener {
    private static final int MAX_LINES = 2;
    private int currentMaxLines = Integer.MAX_VALUE;

    /* renamed from: com.apps.smartschoolmanagement.utils.ExpandableTextView$1 */
    class C13931 implements Runnable {
        C13931() {
        }

        public void run() {
            if (ExpandableTextView.this.getLineCount() > 2) {
                ExpandableTextView.this.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, R.drawable.three_dots);
            } else {
                ExpandableTextView.this.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            }
            ExpandableTextView.this.setMaxLines(2);
        }
    }

    public ExpandableTextView(Context context) {
        super(context);
        setOnClickListener(this);
    }

    public ExpandableTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setOnClickListener(this);
    }

    public ExpandableTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOnClickListener(this);
    }

    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        post(new C13931());
    }

    public void setMaxLines(int maxLines) {
        this.currentMaxLines = maxLines;
        super.setMaxLines(maxLines);
    }

    public int getMyMaxLines() {
        return this.currentMaxLines;
    }

    public void onClick(View v) {
        if (getMyMaxLines() == Integer.MAX_VALUE) {
            setMaxLines(2);
        } else {
            setMaxLines(Integer.MAX_VALUE);
        }
    }
}
