package com.apps.smartschoolmanagement.activities;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import android.widget.ListView;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.apps.smartschoolmanagement.R;
import com.apps.smartschoolmanagement.adapters.ListViewAdapter;
import com.apps.smartschoolmanagement.models.ListData;
import com.apps.smartschoolmanagement.models.UserStaticData;
import com.apps.smartschoolmanagement.utils.JsonClass;
import com.apps.smartschoolmanagement.utils.ProfileInfo;
import com.apps.smartschoolmanagement.utils.URLs;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LibraryActivity extends JsonClass {
    ListView listView;

    /* renamed from: com.apps.smartschoolmanagement.activities.LibraryActivity$1 */
    class C12211 implements VolleyCallback {
        C12211() {
        }

        public void onSuccess(String result) {
            try {
                LibraryActivity.this.processJSONResult(new JSONObject(result));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if (UserStaticData.user_type == 0) {
            setTheme(R.style.AppTheme1);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview);
        setTitle("Library");
        KProgressHUD progressHUD = KProgressHUD.create(this).setLabel("Loading");
        this.listView = (ListView) findViewById(R.id.listview);
        if (UserStaticData.user_type == 0) {
            this.params.put("student_id", ProfileInfo.getInstance().getLoginData().get("userId"));
        } else if (UserStaticData.user_type == 1) {
            this.params.put("teacer_id", ProfileInfo.getInstance().getLoginData().get("userId"));
        }
        getJsonResponse(URLs.books, this, new C12211());
    }

    public void processJSONResult(JSONObject jsonObject) {
        try {
            this.values.clear();
            JSONArray jsonArray = jsonObject.getJSONArray("Response");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                String studentName = jsonObject1.getString("studentName");
                String bookName = jsonObject1.getString("bookName");
                String dateoftaken = jsonObject1.getString("dateoftaken");
                String dateofsubmit = jsonObject1.getString("dateofsubmit");
                String status_text = jsonObject1.getString(NotificationCompat.CATEGORY_STATUS);
                ListData listData = new ListData();
                listData.setLibrary_studentName(studentName);
                listData.setLibrary_bookName(bookName);
                listData.setLibrary_dateoftaken(dateoftaken);
                listData.setLibrary_dateofsubmit(dateofsubmit);
                listData.setLibrary_status_text(status_text);
                this.values.add(listData);
            }
            if (jsonArray.length() > 0) {
                findViewById(R.id.error).setVisibility(8);
            } else {
                findViewById(R.id.error).setVisibility(0);
            }
            this.listView.setAdapter(new ListViewAdapter(this, R.layout.item_layout_library, this.values));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
