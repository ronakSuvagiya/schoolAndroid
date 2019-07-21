package com.apps.smartschoolmanagement.activities;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff.Mode;
import android.os.Bundle;
import android.preference.PreferenceManager;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener;
import com.google.android.material.tabs.TabLayout.Tab;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AlertDialog.Builder;
import com.apps.smartschoolmanagement.R;
import com.apps.smartschoolmanagement.fragments.Home_Fragment;
import com.apps.smartschoolmanagement.fragments.ManagerProfile;
import com.apps.smartschoolmanagement.fragments.Notifications;
import com.apps.smartschoolmanagement.fragments.StaffProfile;
import com.apps.smartschoolmanagement.fragments.StudentProfile;
import com.apps.smartschoolmanagement.models.UserStaticData;
import com.apps.smartschoolmanagement.utils.TabPagerAdapter;
import com.apps.smartschoolmanagement.utils.basehelpers.BaseActivity;

public class HomeActivity extends BaseActivity {
    public static int[] images = null;
    public static boolean saved = false;
    public static String[] titles = null;
    TabPagerAdapter adapter;
    private int[] tabIcons = new int[]{R.drawable.img_home, R.drawable.img_user, R.drawable.img_notification};
    TabLayout tabLayout;
    ViewPager viewPager;
    String user_type = null;
    SharedPreferences sp;
    /* renamed from: com.apps.smartschoolmanagement.activities.HomeActivity$1 */
    class C12161 implements OnTabSelectedListener {
        C12161() {
        }

        public void onTabSelected(Tab tab) {
            if (tab.getPosition() == 0) {
                HomeActivity.this.setTitle("Home");
            }
            if (tab.getPosition() == 1) {
                HomeActivity.this.setTitle("My Profile");
            }
            if (tab.getPosition() == 2) {
                HomeActivity.this.setTitle("Notice Board");
            }
        }

        public void onTabUnselected(Tab tab) {
        }

        public void onTabReselected(Tab tab) {
        }
    }

    /* renamed from: com.apps.smartschoolmanagement.activities.HomeActivity$2 */
//    class C12172 implements OnClickListener {
//        C12172() {
//        }
//
//        public void onClick(DialogInterface dialogInterface, int i) {
//            dialogInterface.dismiss();
//        }
//    }

    /* renamed from: com.apps.smartschoolmanagement.activities.HomeActivity$3 */
//    class C12183 implements OnClickListener {
//        C12183() {
//        }
//
//        public void onClick(DialogInterface dialogInterface, int i) {
//            dialogInterface.dismiss();
//            HomeActivity.this.finishAffinity();
//            HomeActivity.this.startActivity(new Intent(HomeActivity.this, StartActivity.class));
//        }
//    }

    protected void onResume() {
        super.onResume();
        setTitle("Home");
        if (this.tabLayout != null && this.tabLayout.getSelectedTabPosition() == 1 && saved) {
            this.adapter.notifyDataSetChanged();
            setupTabIcons();
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (UserStaticData.user_type == 0) {
            setTheme(R.style.AppTheme1);
        }
        setContentView(R.layout.tab_layout);
        setTitle("Home");
        sp =  PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        user_type = sp.getString("usertype", null);
        if (getIntent().getStringArrayExtra("titles") != null) {
            titles = getIntent().getStringArrayExtra("titles");
        }
        if (getIntent().getIntArrayExtra("images") != null) {
            images = getIntent().getIntArrayExtra("images");
        }
        this.viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(this.viewPager);
        this.viewPager.setOffscreenPageLimit(2);
        this.tabLayout = (TabLayout) findViewById(R.id.tabs);
        this.tabLayout.setupWithViewPager(this.viewPager);
        if (UserStaticData.user_type == 0) {
            this.tabLayout.setBackground(ContextCompat.getDrawable(this, R.drawable.background_student));
        }
        this.tabLayout.addOnTabSelectedListener(new C12161());
        setupTabIcons();


    }

    private void setupTabIcons() {
        this.tabLayout.getTabAt(0).setIcon(this.tabIcons[0]);
        this.tabLayout.getTabAt(1).setIcon(this.tabIcons[1]);
        this.tabLayout.getTabAt(2).setIcon(this.tabIcons[2]);
        this.tabLayout.getTabAt(0).getIcon().setColorFilter(-1, Mode.SRC_IN);
        this.tabLayout.getTabAt(1).getIcon().setColorFilter(-1, Mode.SRC_IN);
        this.tabLayout.getTabAt(2).getIcon().setColorFilter(-1, Mode.SRC_IN);
    }

    private void setupViewPager(ViewPager viewPager) {
        this.adapter = new TabPagerAdapter(getSupportFragmentManager());
        this.adapter.addTab(new Home_Fragment(), "");
        if ("student".equals(this.user_type)) {
            this.adapter.addTab(new StudentProfile(), "");
        } else if ("teacher".equals(this.user_type)) {
            this.adapter.addTab(new StaffProfile(), "");
        } else if ("teacher".equals(this.user_type)) {
            this.adapter.addTab(new ManagerProfile(), "");
        }
        this.adapter.addTab(new Notifications(), "");
        viewPager.setAdapter(this.adapter);
    }

    public void onBackPressed() {
        finish();
    }

//    public void showDialogBox() {
//        new Builder(this).setTitle("Log Out").setMessage("Do you want to Log Out from the Session?").setPositiveButton(17039370, new C12183()).setNegativeButton(17039360, new C12172()).show();
//    }
}
