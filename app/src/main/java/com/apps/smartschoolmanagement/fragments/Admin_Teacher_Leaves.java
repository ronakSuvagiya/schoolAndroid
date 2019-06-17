package com.apps.smartschoolmanagement.fragments;

import android.content.Context;
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

public class Admin_Teacher_Leaves extends JsonFragment {
    private FragmentActivity myContext;
    View rootView;
    TabLayout tabLayout;

    /* renamed from: com.apps.smartschoolmanagement.fragments.Admin_Teacher_Leaves$1 */
    class C13361 implements OnTabSelectedListener {
        C13361() {
        }

        public void onTabSelected(Tab tab) {
            FragmentTransaction transaction = Admin_Teacher_Leaves.this.myContext.getSupportFragmentManager().beginTransaction();
            if (Admin_Teacher_Leaves.this.tabLayout.getSelectedTabPosition() == 0) {
                transaction.replace(R.id.container, new Leave_Teacher_Pending()).commit();
            } else if (Admin_Teacher_Leaves.this.tabLayout.getSelectedTabPosition() == 1) {
                transaction.replace(R.id.container, new Leave_Teacher_Approved()).commit();
            } else if (Admin_Teacher_Leaves.this.tabLayout.getSelectedTabPosition() == 2) {
                transaction.replace(R.id.container, new Leave_Teacher_Rejected()).commit();
            }
        }

        public void onTabUnselected(Tab tab) {
        }

        private void selectTab(Tab tab) {
            Admin_Teacher_Leaves.this.myContext.getSupportFragmentManager().beginTransaction().replace(R.id.container, new Leave_Teacher_Pending()).commit();
        }

        public void onTabReselected(Tab tab) {
            if (tab.getPosition() == 0) {
                selectTab(tab);
            }
        }
    }

    /* renamed from: com.apps.smartschoolmanagement.fragments.Admin_Teacher_Leaves$2 */
    class C13372 implements Runnable {
        C13372() {
        }

        public void run() {
            Admin_Teacher_Leaves.this.tabLayout.getTabAt(0).select();
        }
    }

    public void onAttach(Context context) {
        super.onAttach(context);
        this.myContext = (FragmentActivity) context;
    }

    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.rootView = inflater.inflate(R.layout.tab_layout_top, container, false);
        this.tabLayout = (TabLayout) this.rootView.findViewById(R.id.tabs);
        this.tabLayout.addTab(this.tabLayout.newTab().setText("Pending"));
        this.tabLayout.addTab(this.tabLayout.newTab().setText("Approved"));
        this.tabLayout.addTab(this.tabLayout.newTab().setText("Rejected"));
        this.tabLayout.addOnTabSelectedListener(new C13361());
        return this.rootView;
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        new Handler().postDelayed(new C13372(), 500);
    }
}
