package com.apps.smartschoolmanagement.activities;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
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

    /* renamed from: com.apps.smartschoolmanagement.activities.AssignmentsActivity$1 */
    class C11971 implements TextWatcher {
        C11971() {
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            AssignmentsActivity.this.loadData();
            AssignmentsActivity.this.titleView.setText("Assignments on Day : " + AssignmentsActivity.this.date.getText().toString());
        }
    }

    /* renamed from: com.apps.smartschoolmanagement.activities.AssignmentsActivity$2 */
    class C11982 implements VolleyCallback {
        C11982() {
        }

        public void onSuccess(String result) {
            try {
                AssignmentsActivity.this.processJSONResult(new JSONObject(result));
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
        setContentView(R.layout.activity_assignments);
        setTitle("Assignments");
        this.progressHUD = KProgressHUD.create(this).setLabel("Loading");
        this.listView = (ListView) findViewById(R.id.listview_assignments);
        this.titleView = (TextView) findViewById(R.id.title);
        this.date = (EditText) findViewById(R.id.date);
        this.date.setOnClickListener(new OnClickDateListener(this.date, this));
        this.date.setVisibility(8);
        loadData();
        this.date.addTextChangedListener(new C11971());
    }

    public void loadData() {
        this.params.put("student_id", ProfileInfo.getInstance().getLoginData().get("userId"));
        if (this.date.getText().toString().length() > 5) {
            this.params.put("date", this.date.getText().toString());
        } else {
            this.params.put("date", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        }
      //  getJsonResponse(URLs.assignments_List_bydate, this, new C11982());
    }

    public void processJSONResult(JSONObject jsonObject) {
        try {
            this.values.clear();
            JSONArray jsonArray = jsonObject.getJSONArray("Response");
            if ("success".equals(jsonObject.getString(NotificationCompat.CATEGORY_STATUS))) {
                findViewById(R.id.error).setVisibility(8);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                    ListData listData = new ListData();
                    if (!jsonObject1.isNull("submit_date")) {
                        listData.setAssignment_submit_date(jsonObject1.getString("submit_date"));
                    }
                    if (!jsonObject1.isNull("id")) {
                        listData.setAssignment_id(jsonObject1.getString("id"));
                    }
                    if (!jsonObject1.isNull("Assignment")) {
                        listData.setAssignment_assignment(jsonObject1.getString("Assignment"));
                    }
                    if (!jsonObject1.isNull(NotificationCompat.CATEGORY_STATUS)) {
                        listData.setStatus(jsonObject1.getString(NotificationCompat.CATEGORY_STATUS));
                    }
                    if (!jsonObject1.isNull("teacher_id")) {
                        listData.setAssignment_teacher(jsonObject1.getString("teacher_id"));
                    }
                    if (!jsonObject1.isNull("subjectName")) {
                        listData.setAssignment_subject(jsonObject1.getString("subjectName"));
                    }
                    this.values.add(listData);
                }
            } else {
                findViewById(R.id.error).setVisibility(0);
            }
            this.listView.setAdapter(new ListViewAdapter(this, R.layout.item_layout_assignments, this.values));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getDateCurrentTimeZone(long timestamp) {
        try {
            Calendar calendar = Calendar.getInstance();
            TimeZone tz = TimeZone.getDefault();
            calendar.setTimeInMillis(1000 * timestamp);
            calendar.add(14, tz.getOffset(calendar.getTimeInMillis()));
            return new SimpleDateFormat("MM/dd/yyyy").format(calendar.getTime());
        } catch (Exception e) {
            return "";
        }
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
