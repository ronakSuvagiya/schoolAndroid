package com.apps.smartschoolmanagement.activities;

import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.Nullable;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener;
import com.google.android.material.tabs.TabLayout.Tab;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;
import com.apps.smartschoolmanagement.R;
import com.apps.smartschoolmanagement.fragments.AssignmentsHistory;
import com.apps.smartschoolmanagement.fragments.PostAssignment;

public class AssignmentsTeacherActivity extends AppCompatActivity {
    TabLayout tabLayout;

    /* renamed from: com.apps.smartschoolmanagement.activities.AssignmentsTeacherActivity$1 */
    class C11991 implements OnTabSelectedListener {
        C11991() {
        }

        public void onTabSelected(Tab tab) {
            FragmentTransaction transaction = AssignmentsTeacherActivity.this.getSupportFragmentManager().beginTransaction();
            if (AssignmentsTeacherActivity.this.tabLayout.getSelectedTabPosition() == 0) {
                transaction.replace(R.id.container, new PostAssignment()).commit();
            } else if (AssignmentsTeacherActivity.this.tabLayout.getSelectedTabPosition() == 1) {
                transaction.replace(R.id.container, new AssignmentsHistory()).commit();
            }
        }

        public void onTabUnselected(Tab tab) {
        }

        private void selectTab(Tab tab) {
            AssignmentsTeacherActivity.this.getSupportFragmentManager().beginTransaction().replace(R.id.container, new PostAssignment()).commit();
        }

        public void onTabReselected(Tab tab) {
            if (tab.getPosition() == 0) {
                selectTab(tab);
            }
        }
    }

    /* renamed from: com.apps.smartschoolmanagement.activities.AssignmentsTeacherActivity$2 */
    class C12002 implements Runnable {
        C12002() {
        }

        public void run() {
            AssignmentsTeacherActivity.this.tabLayout.getTabAt(0).select();
        }
    }

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab_layout_top);
        getSupportActionBar().setElevation(0.0f);
        setTitle("Assignments");
        this.tabLayout = (TabLayout) findViewById(R.id.tabs);
        this.tabLayout.addTab(this.tabLayout.newTab().setText("Post Assignment"));
        this.tabLayout.addTab(this.tabLayout.newTab().setText("History"));
        this.tabLayout.addOnTabSelectedListener(new C11991());
        new Handler().postDelayed(new C12002(), 500);
    }
}
