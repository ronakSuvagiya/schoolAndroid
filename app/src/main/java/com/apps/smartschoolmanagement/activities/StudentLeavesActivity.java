package com.apps.smartschoolmanagement.activities;

import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.Nullable;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener;
import com.google.android.material.tabs.TabLayout.Tab;
import androidx.fragment.app.FragmentTransaction;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import com.apps.smartschoolmanagement.R;
import com.apps.smartschoolmanagement.fragments.AllLeaves;
import com.apps.smartschoolmanagement.fragments.ApplyLeave;
import com.apps.smartschoolmanagement.models.UserStaticData;

public class StudentLeavesActivity extends AppCompatActivity {
    TabLayout tabLayout;

    /* renamed from: com.apps.smartschoolmanagement.activities.StudentLeavesActivity$1 */
    class C13051 implements OnTabSelectedListener {
        C13051() {
        }

        public void onTabSelected(Tab tab) {
            FragmentTransaction transaction = StudentLeavesActivity.this.getSupportFragmentManager().beginTransaction();
            if (StudentLeavesActivity.this.tabLayout.getSelectedTabPosition() == 0) {
                transaction.replace(R.id.container, new AllLeaves()).commit();
            } else if (StudentLeavesActivity.this.tabLayout.getSelectedTabPosition() == 1) {
                transaction.replace(R.id.container, new ApplyLeave()).commit();
            }
        }

        public void onTabUnselected(Tab tab) {
        }

        private void selectTab() {
            StudentLeavesActivity.this.getSupportFragmentManager().beginTransaction().replace(R.id.container, new AllLeaves()).commit();
        }

        public void onTabReselected(Tab tab) {
            if (tab.getPosition() == 0) {
                selectTab();
            }
        }
    }

    /* renamed from: com.apps.smartschoolmanagement.activities.StudentLeavesActivity$2 */
    class C13062 implements Runnable {
        C13062() {
        }

        public void run() {
            StudentLeavesActivity.this.tabLayout.getTabAt(0).select();
        }
    }

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if (UserStaticData.user_type == 0) {
            setTheme(R.style.AppTheme1);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab_layout_top);
        this.tabLayout = (TabLayout) findViewById(R.id.tabs);
        this.tabLayout.addTab(this.tabLayout.newTab().setText("My Leaves"));
        this.tabLayout.addTab(this.tabLayout.newTab().setText("Apply Leave"));
        if (UserStaticData.user_type == 0) {
            this.tabLayout.setBackground(ContextCompat.getDrawable(this, R.drawable.background_student));
        }
        this.tabLayout.addOnTabSelectedListener(new C13051());
        new Handler().postDelayed(new C13062(), 500);
    }
}
