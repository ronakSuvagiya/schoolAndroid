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
import com.apps.smartschoolmanagement.fragments.StudentHealthStatus;
import com.apps.smartschoolmanagement.fragments.TeacherHealthStatus;

public class HealthStatusActivity extends AppCompatActivity {
    TabLayout tabLayout;

    /* renamed from: com.apps.smartschoolmanagement.activities.HealthStatusActivity$1 */
    class C12141 implements OnTabSelectedListener {
        C12141() {
        }

        public void onTabSelected(Tab tab) {
            FragmentTransaction transaction = HealthStatusActivity.this.getSupportFragmentManager().beginTransaction();
            if (HealthStatusActivity.this.tabLayout.getSelectedTabPosition() == 0) {
                transaction.replace(R.id.container, new StudentHealthStatus()).commit();
            } else if (HealthStatusActivity.this.tabLayout.getSelectedTabPosition() == 1) {
                transaction.replace(R.id.container, new TeacherHealthStatus()).commit();
            }
        }

        public void onTabUnselected(Tab tab) {
        }

        private void selectTab() {
            HealthStatusActivity.this.getSupportFragmentManager().beginTransaction().replace(R.id.container, new StudentHealthStatus()).commit();
        }

        public void onTabReselected(Tab tab) {
            if (tab.getPosition() == 0) {
                selectTab();
            }
        }
    }

    /* renamed from: com.apps.smartschoolmanagement.activities.HealthStatusActivity$2 */
    class C12152 implements Runnable {
        C12152() {
        }

        public void run() {
            HealthStatusActivity.this.tabLayout.getTabAt(0).select();
        }
    }

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab_layout_top);
        getSupportActionBar().setElevation(0.0f);
        setTitle("Health Status");
        this.tabLayout = (TabLayout) findViewById(R.id.tabs);
        this.tabLayout.addTab(this.tabLayout.newTab().setText("Student's Health Status"));
        this.tabLayout.addTab(this.tabLayout.newTab().setText("My Health Status"));
        this.tabLayout.addOnTabSelectedListener(new C12141());
        new Handler().postDelayed(new C12152(), 500);
    }
}
