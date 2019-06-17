package com.apps.smartschoolmanagement.activities;

import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.apps.smartschoolmanagement.R;
import com.apps.smartschoolmanagement.utils.CircleDisplay;

public class TestActivity extends AppCompatActivity {
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manage_leaves);
        CircleDisplay mCircleDisplay1 = (CircleDisplay) findViewById(R.id.circleDisplay1);
        CircleDisplay mCircleDisplay2 = (CircleDisplay) findViewById(R.id.circleDisplay2);
        mCircleDisplay1.setAnimDuration(0);
        mCircleDisplay1.setValueWidthPercent(15.0f);
        mCircleDisplay1.setDimAlpha(100);
        mCircleDisplay1.setColorinner(Color.parseColor("#1D89E4"));
        mCircleDisplay1.setColorArc(Color.parseColor("#1976D3"));
        mCircleDisplay1.showValue(100.0f, 100.0f, true);
        mCircleDisplay2.setAnimDuration(0);
        mCircleDisplay2.setValueWidthPercent(15.0f);
        mCircleDisplay2.setDimAlpha(100);
        mCircleDisplay2.setColorinner(Color.parseColor("#9C1EE4"));
        mCircleDisplay2.setColorArc(Color.parseColor("#7A1FA2"));
        mCircleDisplay2.showValue(100.0f, 100.0f, true);
    }
}
