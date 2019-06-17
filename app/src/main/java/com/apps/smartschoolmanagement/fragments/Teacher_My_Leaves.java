package com.apps.smartschoolmanagement.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.Nullable;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener;
import com.google.android.material.tabs.TabLayout.Tab;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.apps.smartschoolmanagement.R;
import com.apps.smartschoolmanagement.utils.JsonFragment;

public class Teacher_My_Leaves extends JsonFragment {
    private FragmentActivity myContext;
    View rootView;
    TabLayout tabLayout;

    /* renamed from: com.apps.smartschoolmanagement.fragments.Teacher_My_Leaves$1 */
    class C13811 implements OnTabSelectedListener {
        C13811() {
        }

        public void onTabSelected(Tab tab) {
            FragmentTransaction transaction = Teacher_My_Leaves.this.myContext.getSupportFragmentManager().beginTransaction();
            if (Teacher_My_Leaves.this.tabLayout.getSelectedTabPosition() == 0) {
                transaction.replace(R.id.container, new AllLeaves()).commit();
            } else if (Teacher_My_Leaves.this.tabLayout.getSelectedTabPosition() == 1) {
                transaction.replace(R.id.container, new ApplyLeave()).commit();
            }
        }

        public void onTabUnselected(Tab tab) {
        }

        private void selectTab(Tab tab) {
            Teacher_My_Leaves.this.myContext.getSupportFragmentManager().beginTransaction().replace(R.id.container, new AllLeaves()).commit();
        }

        public void onTabReselected(Tab tab) {
            if (tab.getPosition() == 0) {
                selectTab(tab);
            }
        }
    }

    /* renamed from: com.apps.smartschoolmanagement.fragments.Teacher_My_Leaves$2 */
    class C13822 implements Runnable {
        C13822() {
        }

        public void run() {
            Teacher_My_Leaves.this.tabLayout.getTabAt(0).select();
        }
    }

    public void onAttach(Activity activity) {
        this.myContext = (FragmentActivity) activity;
        super.onAttach(activity);
    }

    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.rootView = inflater.inflate(R.layout.tab_layout_top, container, false);
        this.tabLayout = (TabLayout) this.rootView.findViewById(R.id.tabs);
        this.tabLayout.addTab(this.tabLayout.newTab().setText("My Leaves"));
        this.tabLayout.addTab(this.tabLayout.newTab().setText("Apply Leave"));
        this.tabLayout.addOnTabSelectedListener(new C13811());
        return this.rootView;
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        new Handler().postDelayed(new C13822(), 500);
    }
}
