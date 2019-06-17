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
import com.apps.smartschoolmanagement.fragments.AssignmentCompleted;
import com.apps.smartschoolmanagement.fragments.AssignmentPending;

public class AssignmentStatusActivity extends AppCompatActivity {
    TabLayout tabLayout;

    /* renamed from: com.apps.smartschoolmanagement.activities.AssignmentStatusActivity$1 */
    class C11951 implements OnTabSelectedListener {
        C11951() {
        }

        public void onTabSelected(Tab tab) {
            FragmentTransaction transaction = AssignmentStatusActivity.this.getSupportFragmentManager().beginTransaction();
            if (AssignmentStatusActivity.this.tabLayout.getSelectedTabPosition() == 0) {
                transaction.replace(R.id.container, new AssignmentCompleted()).commit();
            } else if (AssignmentStatusActivity.this.tabLayout.getSelectedTabPosition() == 1) {
                transaction.replace(R.id.container, new AssignmentPending()).commit();
            }
        }

        public void onTabUnselected(Tab tab) {
        }

        private void selectTab(Tab tab) {
            AssignmentStatusActivity.this.getSupportFragmentManager().beginTransaction().replace(R.id.container, new AssignmentCompleted()).commit();
        }

        public void onTabReselected(Tab tab) {
            if (tab.getPosition() == 0) {
                selectTab(tab);
            }
        }
    }

    /* renamed from: com.apps.smartschoolmanagement.activities.AssignmentStatusActivity$2 */
    class C11962 implements Runnable {
        C11962() {
        }

        public void run() {
            AssignmentStatusActivity.this.tabLayout.getTabAt(0).select();
        }
    }

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab_layout_top);
        getSupportActionBar().setElevation(0.0f);
        setTitle("Assignment Status");
        this.tabLayout = (TabLayout) findViewById(R.id.tabs);
        this.tabLayout.addTab(this.tabLayout.newTab().setText("Completed"));
        this.tabLayout.addTab(this.tabLayout.newTab().setText("Pending"));
        this.tabLayout.addOnTabSelectedListener(new C11951());
        new Handler().postDelayed(new C11962(), 500);
    }
}
