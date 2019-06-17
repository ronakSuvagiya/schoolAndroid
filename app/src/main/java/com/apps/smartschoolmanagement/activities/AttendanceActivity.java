package com.apps.smartschoolmanagement.activities;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener;
import com.google.android.material.tabs.TabLayout.Tab;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;
import com.apps.smartschoolmanagement.R;
import com.apps.smartschoolmanagement.fragments.StudentAttendance;
import com.apps.smartschoolmanagement.fragments.TeacherAttendance;

public class AttendanceActivity extends AppCompatActivity {
    TabLayout tabLayout;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab_layout_top);
        getSupportActionBar().setElevation(0.0f);
        setTitle("Attendance");
        FragmentTransaction transaction = AttendanceActivity.this.getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, new StudentAttendance()).commit();
    }
}
