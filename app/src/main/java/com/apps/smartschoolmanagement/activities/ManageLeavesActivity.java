package com.apps.smartschoolmanagement.activities;

import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.Nullable;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener;
import com.google.android.material.tabs.TabLayout.Tab;
import androidx.fragment.app.FragmentTransaction;
import com.apps.smartschoolmanagement.R;
import com.apps.smartschoolmanagement.fragments.Teacher_My_Leaves;
import com.apps.smartschoolmanagement.fragments.Teacher_Student_Leaves;
import com.apps.smartschoolmanagement.utils.BaseFinishActivity;

public class ManageLeavesActivity extends BaseFinishActivity {
    TabLayout tabLayout;

    /* renamed from: com.apps.smartschoolmanagement.activities.ManageLeavesActivity$1 */
    class C12291 implements OnTabSelectedListener {
        C12291() {
        }

        public void onTabSelected(Tab tab) {
            FragmentTransaction transaction = ManageLeavesActivity.this.getSupportFragmentManager().beginTransaction();
            if (ManageLeavesActivity.this.tabLayout.getSelectedTabPosition() == 0) {
                transaction.replace(R.id.container_frame, new Teacher_Student_Leaves()).commit();
            } else if (ManageLeavesActivity.this.tabLayout.getSelectedTabPosition() == 1) {
                transaction.replace(R.id.container_frame, new Teacher_My_Leaves()).commit();
            }
        }

        public void onTabUnselected(Tab tab) {
        }

        private void selectTab(Tab tab) {
            ManageLeavesActivity.this.getSupportFragmentManager().beginTransaction().replace(R.id.container_frame, new Teacher_Student_Leaves()).commit();
        }

        public void onTabReselected(Tab tab) {
            if (tab.getPosition() == 0) {
                selectTab(tab);
            }
        }
    }

    /* renamed from: com.apps.smartschoolmanagement.activities.ManageLeavesActivity$2 */
    class C12302 implements Runnable {
        C12302() {
        }

        public void run() {
            ManageLeavesActivity.this.tabLayout.getTabAt(0).select();
        }
    }

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab_layout);
        getSupportActionBar().setElevation(0.0f);
        setTitle("Leave Management");
        this.tabLayout = (TabLayout) findViewById(R.id.tabs);
        findViewById(R.id.viewpager).setVisibility(8);
        findViewById(R.id.container_frame).setVisibility(0);
        this.tabLayout.addTab(this.tabLayout.newTab().setText("Student Leaves"));
        this.tabLayout.addTab(this.tabLayout.newTab().setText("My Leaves"));
        this.tabLayout.addOnTabSelectedListener(new C12291());
        new Handler().postDelayed(new C12302(), 500);
    }
}
