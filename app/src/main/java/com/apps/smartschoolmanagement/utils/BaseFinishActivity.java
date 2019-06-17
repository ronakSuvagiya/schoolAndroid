package com.apps.smartschoolmanagement.utils;

import androidx.appcompat.app.AppCompatActivity;

import com.apps.smartschoolmanagement.R;

public class BaseFinishActivity extends AppCompatActivity {
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out);
    }
}
