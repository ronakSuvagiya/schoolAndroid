package com.apps.smartschoolmanagement.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.apps.smartschoolmanagement.fragments.AssignmentsHistory;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.apps.smartschoolmanagement.R;
import com.apps.smartschoolmanagement.adapters.ListViewAdapter;
import com.apps.smartschoolmanagement.models.ListData;
import com.apps.smartschoolmanagement.models.UserStaticData;
import com.apps.smartschoolmanagement.utils.JsonClass;
import com.apps.smartschoolmanagement.utils.OnClickDateListener;
import com.apps.smartschoolmanagement.utils.ProfileInfo;
import com.apps.smartschoolmanagement.utils.URLs;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AssignmentsActivity extends JsonClass {
    EditText date;
    ListView listView;
    KProgressHUD progressHUD;
    TextView titleView;
    SharedPreferences sp;
    String school = null;
    String std=  null;
    String div = null;
    public ArrayList<ListData> values = new ArrayList();

    /* renamed from: com.apps.smartschoolmanagement.activities.AssignmentsActivity$1 */


    class  todayAssignment implements VolleyCallbackJSONArray{
        @Override
        public void onSuccess(JSONArray jsonArray) {
            try {
                values.clear();
                    findViewById(R.id.error).setVisibility(8);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        ListData listData = new ListData();
                        if (!jsonObject1.isNull("date")) {
                            listData.setAssignment_submit_date(jsonObject1.getString("date"));
                        }
                        if (!jsonObject1.isNull("description")) {
                            listData.setAssignment_assignment(jsonObject1.getString("description"));
                        }
                        if (!jsonObject1.isNull("teacherMaster")) {
                            String teacherMaster = String.valueOf(jsonObject1.get("teacherMaster"));
                            JSONObject techserobj = new JSONObject(teacherMaster);
                            listData.setAssignment_teacher(techserobj.getString("name"));
                        }
                        if (!jsonObject1.isNull("subject")) {
                            String subject = String.valueOf(jsonObject1.get("subject"));
                            JSONObject subjectObj = new JSONObject(subject);
                            listData.setAssignment_subject(subjectObj.getString("name"));
                        }
                        values.add(listData);
                    }
               listView.setAdapter(new ListViewAdapter(AssignmentsActivity.this, R.layout.item_layout_assignments, values));
            } catch (JSONException e) {
                findViewById(R.id.error).setVisibility(0);
                e.printStackTrace();
            }
        }
    }
    class C11971 implements TextWatcher {
        C11971() {
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            getJsonResponse(URLs.getTodayAssignment + std + "&div=" + div + "&date=" + editable.toString(), AssignmentsActivity.this, new AssignmentsActivity.todayAssignment());
        }
    }
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if (UserStaticData.user_type == 0) {
            setTheme(R.style.AppTheme1);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignments);
        setTitle("Assignments");
        this.progressHUD = KProgressHUD.create(this).setLabel("Loading");
        this.listView = (ListView) findViewById(R.id.listview_assignments);
        this.titleView = (TextView) findViewById(R.id.title);
        this.date = (EditText) findViewById(R.id.date);
        this.date.setOnClickListener(new OnClickDateListener(this.date, this,"future"));
        this.date.setVisibility(8);
        loadData();
        this.date.addTextChangedListener(new C11971());
        sp = PreferenceManager.getDefaultSharedPreferences(this);
        this.std = (sp.getString("stdId", ""));
        this.div = (sp.getString("DivId", ""));
        getJsonResponse(URLs.getTodayAssignment + std + "&div=" + div + "&date=" + new SimpleDateFormat("yyyy-MM-dd").format(new Date()), this, new todayAssignment());
    }

    public void loadData() {
        this.params.put("student_id", ProfileInfo.getInstance().getLoginData().get("userId"));
        if (this.date.getText().toString().length() > 5) {
            this.params.put("date", this.date.getText().toString());
        } else {
            this.params.put("date",new SimpleDateFormat("yyyy-MM-dd").format(new Date()) );
        }
      //  getJsonResponse(URLs.assignments_List_bydate, this, new C11982());
    }



    public boolean onPrepareOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.calendar, menu);
        return super.onPrepareOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_calendar) {
            this.date.callOnClick();
        }
        return super.onOptionsItemSelected(item);
    }
}
