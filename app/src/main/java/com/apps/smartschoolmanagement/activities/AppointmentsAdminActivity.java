package com.apps.smartschoolmanagement.activities;

import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.Nullable;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener;
import com.google.android.material.tabs.TabLayout.Tab;
import androidx.fragment.app.FragmentTransaction;
import com.apps.smartschoolmanagement.R;
import com.apps.smartschoolmanagement.fragments.ListFragment;
import com.apps.smartschoolmanagement.models.ListData;
import com.apps.smartschoolmanagement.utils.JsonClass;
import com.apps.smartschoolmanagement.utils.ProfileInfo;
import com.apps.smartschoolmanagement.utils.URLs;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AppointmentsAdminActivity extends JsonClass {
    public static ArrayList<ListData> students = new ArrayList();
    public static ArrayList<ListData> teachers = new ArrayList();
    ListFragment listFragment = new ListFragment();
    ListFragment listFragment1 = new ListFragment();
    TabLayout tabLayout;

    /* renamed from: com.apps.smartschoolmanagement.activities.AppointmentsAdminActivity$1 */
    class C11921 implements VolleyCallback {
        C11921() {
        }

        public void onSuccess(String result) {
            try {
                AppointmentsAdminActivity.this.processJSONResult(new JSONObject(result));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    /* renamed from: com.apps.smartschoolmanagement.activities.AppointmentsAdminActivity$2 */
    class C11932 implements OnTabSelectedListener {
        C11932() {
        }

        public void onTabSelected(Tab tab) {
            FragmentTransaction transaction = AppointmentsAdminActivity.this.getSupportFragmentManager().beginTransaction();
            if (AppointmentsAdminActivity.this.tabLayout.getSelectedTabPosition() == 0) {
                ProfileInfo.getInstance().setSelectedTab(1);
                transaction.replace(R.id.container, AppointmentsAdminActivity.this.listFragment).commit();
            } else if (AppointmentsAdminActivity.this.tabLayout.getSelectedTabPosition() == 1) {
                ProfileInfo.getInstance().setSelectedTab(2);
                transaction.replace(R.id.container, AppointmentsAdminActivity.this.listFragment1).commit();
            }
        }

        public void onTabUnselected(Tab tab) {
        }

        private void selectTab(Tab tab) {
            FragmentTransaction transaction = AppointmentsAdminActivity.this.getSupportFragmentManager().beginTransaction();
            ProfileInfo.getInstance().setSelectedTab(1);
            transaction.replace(R.id.container, AppointmentsAdminActivity.this.listFragment).commit();
        }

        public void onTabReselected(Tab tab) {
            if (tab.getPosition() == 0) {
                selectTab(tab);
            }
        }
    }

    /* renamed from: com.apps.smartschoolmanagement.activities.AppointmentsAdminActivity$3 */
    class C11943 implements Runnable {
        C11943() {
        }

        public void run() {
            AppointmentsAdminActivity.this.tabLayout.getTabAt(0).select();
        }
    }

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab_layout_top);
        setTitle("Appointments");
       // getJsonResponse(URLs.appointmentsList, this, new C11921());
        this.tabLayout = (TabLayout) findViewById(R.id.tabs);
        this.tabLayout.addTab(this.tabLayout.newTab().setText("Students"));
        this.tabLayout.addTab(this.tabLayout.newTab().setText("Teachers"));
        this.tabLayout.addOnTabSelectedListener(new C11932());
        new Handler().postDelayed(new C11943(), 500);
    }

    public void processJSONResult(JSONObject jsonObject) {
        try {
            int i;
            JSONObject jsonObject1;
            ListData listData;
            students.clear();
            ProfileInfo.getInstance().getStud().clear();
            if (!jsonObject.isNull("studentList")) {
                JSONArray jsonArray = jsonObject.getJSONArray("studentList");
                for (i = 0; i < jsonArray.length(); i++) {
                    jsonObject1 = jsonArray.getJSONObject(i);
                    listData = new ListData();
                    if (!jsonObject1.isNull("appointment_id")) {
                        listData.setAppointmentid(jsonObject1.getString("appointment_id"));
                    }
                    if (!jsonObject1.isNull("student_id")) {
                        listData.setId(jsonObject1.getString("student_id"));
                    }
                    if (!jsonObject1.isNull("regarding")) {
                        listData.setRegarding(jsonObject1.getString("regarding"));
                    }
                    if (!jsonObject1.isNull("roll")) {
                        listData.setRoll(jsonObject1.getString("roll"));
                    }
                    if (!jsonObject1.isNull("date_of_appinment")) {
                        listData.setDate(jsonObject1.getString("date_of_appinment"));
                    }
                    if (!jsonObject1.isNull("time")) {
                        listData.setTime(jsonObject1.getString("time"));
                    }
                    if (!jsonObject1.isNull("studentName")) {
                        listData.setName(jsonObject1.getString("studentName"));
                    }
                    if (!jsonObject1.isNull("className")) {
                        listData.setClassname(jsonObject1.getString("className"));
                    }
                    if (!jsonObject1.isNull("file_path")) {
                        listData.setPhoto(jsonObject1.getString("file_path"));
                    }
                    students.add(listData);
                }
            }
            if (!jsonObject.isNull("teacherList")) {
                teachers.clear();
                ProfileInfo.getInstance().getTeach().clear();
                JSONArray jsonArray1 = jsonObject.getJSONArray("teacherList");
                for (i = 0; i < jsonArray1.length(); i++) {
                    jsonObject1 = jsonArray1.getJSONObject(i);
                    listData = new ListData();
                    if (!jsonObject1.isNull("appointment_id")) {
                        listData.setAppointmentid(jsonObject1.getString("appointment_id"));
                    }
                    if (!jsonObject1.isNull("teacher_id")) {
                        listData.setId(jsonObject1.getString("teacher_id"));
                    }
                    if (!jsonObject1.isNull("regarding")) {
                        listData.setRegarding(jsonObject1.getString("regarding"));
                    }
                    if (!jsonObject1.isNull("date_of_appinment")) {
                        listData.setDate(jsonObject1.getString("date_of_appinment"));
                    }
                    if (!jsonObject1.isNull("time")) {
                        listData.setTime(jsonObject1.getString("time"));
                    }
                    if (!jsonObject1.isNull("teacherName")) {
                        listData.setName(jsonObject1.getString("teacherName"));
                    }
                    if (!jsonObject1.isNull("className")) {
                        listData.setClassname(jsonObject1.getString("className"));
                    }
                    if (!jsonObject1.isNull("file_path")) {
                        listData.setPhoto(jsonObject1.getString("file_path"));
                    }
                    teachers.add(listData);
                }
            }
            ProfileInfo.getInstance().setStud(students);
            ProfileInfo.getInstance().setTeach(teachers);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<ListData> getStudents() {
        return students;
    }

    public ArrayList<ListData> getTeachers() {
        return teachers;
    }
}
