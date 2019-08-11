package com.apps.smartschoolmanagement.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.Nullable;

import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.ListView;
//import com.google.firebase.auth.PhoneAuthProvider;
import com.apps.smartschoolmanagement.R;
import com.apps.smartschoolmanagement.activities.AddPhotoGalleryActivity.Utils;
import com.apps.smartschoolmanagement.adapters.ListViewAdapter;
import com.apps.smartschoolmanagement.models.ListData;
import com.apps.smartschoolmanagement.models.UserStaticData;
import com.apps.smartschoolmanagement.utils.JsonClass;
import com.apps.smartschoolmanagement.utils.URLs;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class StaffProfileActivity extends JsonClass {
    ListView listView;
    SharedPreferences sp;

    /* renamed from: com.apps.smartschoolmanagement.activities.StaffProfileActivity$1 */
//    class C12961 implements VolleyCallback {
//        C12961() {
//        }
//
//        public void onSuccess(String result) {
//            try {
//                StaffProfileActivity.this.processJSONResult(new JSONObject(result));
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }
//    }

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if (UserStaticData.user_type == 0) {
            setTheme(R.style.AppTheme1);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview);
        setTitle("Staff Profile");
        sp = PreferenceManager.getDefaultSharedPreferences(StaffProfileActivity.this);
        String channel = (sp.getString("schoolid", ""));
        this.listView = (ListView) findViewById(R.id.listview);
        getJsonResponse(URLs.getStaffData + channel, StaffProfileActivity.this, new StaffProfileActivity.getStaffApi());

        //getJsonResponse(URLs.allteacherslist, this, new C12961());
    }
    class getStaffApi implements VolleyCallbackJSONArray {

        @Override
        public void onSuccess(JSONArray jsonArray) {
            values.clear();
            for (int i = 0; i < jsonArray.length(); i++) {
                try {
                    ListData listData = new ListData();
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                    if (!jsonObject1.isNull("id")) {
                        listData.setStaff_id(jsonObject1.getString("id"));
                    }
                    if (!jsonObject1.isNull("name")) {
                        listData.setStaff_name(jsonObject1.getString("name"));
                    }
//                    if (!jsonObject1.isNull("birthday")) {
//                        listData.setStaff_birthday(jsonObject1.getString("birthday"));
//                    }
//                    if (!jsonObject1.isNull("sex")) {
//                        listData.setStaff_gender(jsonObject1.getString("sex"));
//                    }
//                    if (!jsonObject1.isNull("religion")) {
//                        listData.setStaff_religion(jsonObject1.getString("religion"));
//                    }
//                    if (!jsonObject1.isNull("blood_group")) {
//                        listData.setStaff_bloodgroup(jsonObject1.getString("blood_group"));
//                    }
//                    if (!jsonObject1.isNull("address")) {
//                        listData.setStaff_address(jsonObject1.getString("address"));
//                    }
                if (!jsonObject1.isNull("mobileNo")) {
                    listData.setStaff_phone(jsonObject1.getString("mobileNo"));
                }
//                    if (!jsonObject1.isNull("email")) {
//                        listData.setStaff_email(jsonObject1.getString("email"));
//                    }
//                    if (!jsonObject1.isNull("current_ctc")) {
//                        listData.setStaff_ctc(jsonObject1.getString("current_ctc"));
//                    }
//                    if (!jsonObject1.isNull("file_path")) {
//                        listData.setStaff_photo(jsonObject1.getString("file_path"));
//                    }
//                    if (!jsonObject1.isNull("experience")) {
//                        listData.setStaff_experience(jsonObject1.getString("experience"));
//                    }
//                    if (!jsonObject1.isNull("join_date")) {
//                        listData.setStaff_joindate(jsonObject1.getString("join_date"));
//                    }
//                    if (!jsonObject1.isNull("subjectName")) {
//                        listData.setStaff_subject(jsonObject1.getString("subjectName"));
//                    }
                    JSONObject department = jsonObject1.getJSONObject("department");
                    if (!department.isNull("stdName")) {
                        listData.setStaff_class(department.getString("stdName"));
                    }
                    values.add(listData);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            listView.setAdapter(new ListViewAdapter(StaffProfileActivity.this, R.layout.item_layout_profile_teacher, values));

        }
    }
//    public void processJSONResult(JSONObject jsonObject) {
//        try {
//            this.values.clear();
//            JSONArray jsonArray = jsonObject.getJSONArray("ResponseList");
//            for (int i = 0; i < jsonArray.length(); i++) {
//                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
//                ListData listData = new ListData();
//                if (!jsonObject1.isNull("teacher_id")) {
//                    listData.setStaff_id(jsonObject1.getString("teacher_id"));
//                }
//                if (!jsonObject1.isNull(Utils.imageName)) {
//                    listData.setStaff_name(jsonObject1.getString(Utils.imageName));
//                }
//                if (!jsonObject1.isNull("birthday")) {
//                    listData.setStaff_birthday(jsonObject1.getString("birthday"));
//                }
//                if (!jsonObject1.isNull("sex")) {
//                    listData.setStaff_gender(jsonObject1.getString("sex"));
//                }
//                if (!jsonObject1.isNull("religion")) {
//                    listData.setStaff_religion(jsonObject1.getString("religion"));
//                }
//                if (!jsonObject1.isNull("blood_group")) {
//                    listData.setStaff_bloodgroup(jsonObject1.getString("blood_group"));
//                }
//                if (!jsonObject1.isNull("address")) {
//                    listData.setStaff_address(jsonObject1.getString("address"));
//                }
////                if (!jsonObject1.isNull(PhoneAuthProvider.PROVIDER_ID)) {
////                    listData.setStaff_phone(jsonObject1.getString(PhoneAuthProvider.PROVIDER_ID));
////                }
//                if (!jsonObject1.isNull("email")) {
//                    listData.setStaff_email(jsonObject1.getString("email"));
//                }
//                if (!jsonObject1.isNull("current_ctc")) {
//                    listData.setStaff_ctc(jsonObject1.getString("current_ctc"));
//                }
//                if (!jsonObject1.isNull("file_path")) {
//                    listData.setStaff_photo(jsonObject1.getString("file_path"));
//                }
//                if (!jsonObject1.isNull("experience")) {
//                    listData.setStaff_experience(jsonObject1.getString("experience"));
//                }
//                if (!jsonObject1.isNull("join_date")) {
//                    listData.setStaff_joindate(jsonObject1.getString("join_date"));
//                }
//                if (!jsonObject1.isNull("subjectName")) {
//                    listData.setStaff_subject(jsonObject1.getString("subjectName"));
//                }
//                if (!jsonObject1.isNull("className")) {
//                    listData.setStaff_class(jsonObject1.getString("className"));
//                }
//                this.values.add(listData);
//            }
//            this.listView.setAdapter(new ListViewAdapter(this, R.layout.item_layout_profile_teacher, this.values));
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//    }
}
