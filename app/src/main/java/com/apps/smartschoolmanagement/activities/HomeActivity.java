package com.apps.smartschoolmanagement.activities;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff.Mode;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.apps.smartschoolmanagement.Comman.URL;
import com.apps.smartschoolmanagement.photoutil.MainActivity;
import com.apps.smartschoolmanagement.utils.URLs;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener;
import com.google.android.material.tabs.TabLayout.Tab;

import androidx.annotation.NonNull;
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
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import org.json.JSONException;
import org.json.JSONObject;

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
        sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        user_type = sp.getString("usertype", null);
        if (getIntent().getStringArrayExtra("titles") != null) {
            titles = getIntent().getStringArrayExtra("titles");
        }
        if (getIntent().getIntArrayExtra("images") != null) {
            images = getIntent().getIntArrayExtra("images");
        }
        String usertype = sp.getString("usertype","");
        if(usertype.equals("student")) {
            if (sp.getString("token", "").equals("")) {
                token();
            }
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

    public void token() {
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w("HOmeActivity", "getInstanceId failed", task.getException());
                            return;
                        }
                        String token = task.getResult().getToken();
                       String channel = (sp.getString("schoolid", ""));
                        String stdid = (sp.getString("stdId", ""));
                        String DeviceID = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
                        PostNotification(token,DeviceID,channel,stdid);

                    }
                });
    }

    public void PostNotification(String token, String device, String school, String std) {
        RequestQueue MyRequestQueue = Volley.newRequestQueue(this);
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("tokan", token);
            jsonBody.put("deviceID", device);
            jsonBody.put("school", school);
            jsonBody.put("std", std);


            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,URLs.sendToken, jsonBody, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        int code = Integer.parseInt(String.valueOf(response.get("code")));
                        if(code == 200)
                        {
                            SharedPreferences.Editor e = sp.edit();
                            e.putString("token","true");
                            e.commit();
                            Toast.makeText(HomeActivity.this,"token Successfully send!",Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(HomeActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(HomeActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });

            MyRequestQueue.add(jsonObjectRequest);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
//    public void showDialogBox() {
//        new Builder(this).setTitle("Log Out").setMessage("Do you want to Log Out from the Session?").setPositiveButton(17039370, new C12183()).setNegativeButton(17039360, new C12172()).show();
//    }
}
