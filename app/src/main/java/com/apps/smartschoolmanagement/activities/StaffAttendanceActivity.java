package com.apps.smartschoolmanagement.activities;

import android.os.Bundle;
import androidx.annotation.Nullable;
import android.text.TextUtils;
import android.widget.Spinner;
import android.widget.TextView;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.apps.smartschoolmanagement.R;
import com.apps.smartschoolmanagement.models.UserStaticData;
import com.apps.smartschoolmanagement.utils.DateDecorator;
import com.apps.smartschoolmanagement.utils.JsonClass;
import com.apps.smartschoolmanagement.utils.ProfileInfo;
import com.apps.smartschoolmanagement.utils.URLs;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class StaffAttendanceActivity extends JsonClass {
    TextView _Absent;
    TextView _Holidays;
    TextView _Percentage;
    TextView _Present;
    TextView _totalPresent;
    TextView _totalWorking;
    MaterialCalendarView materialCalendarView;
    String teacherid = null;
    Spinner teachers;
    Calendar cal;
    /* renamed from: com.apps.smartschoolmanagement.activities.StaffAttendanceActivity$1 */
    class C12911 implements VolleyCallback {
        C12911() {
        }

        public void onSuccess(String result) {
            StaffAttendanceActivity.this.processJSONResult(result);
        }
    }

    /* renamed from: com.apps.smartschoolmanagement.activities.StaffAttendanceActivity$2 */
    class C12932 implements VolleyCallback {

        /* renamed from: com.apps.smartschoolmanagement.activities.StaffAttendanceActivity$2$1 */
        class C12921 implements VolleyCallback {
            C12921() {
            }

            public void onSuccess(String result) {
                if (!TextUtils.isEmpty(result)) {
                    StaffAttendanceActivity.this.processJSONResult(result);
                }
            }
        }

        C12932() {
        }

        public void onSuccess(String result) {
            if (result != null) {
                StaffAttendanceActivity.this.teacherid = result;
                if (StaffAttendanceActivity.this.teachers.getSelectedItemPosition() > 0) {
                    StaffAttendanceActivity.this.params.put("teacher_id", StaffAttendanceActivity.this.teacherid);
                    Date today = new Date();
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(today);
                    StaffAttendanceActivity.this.params.put("month", "" + (cal.get(2) + 1));
                    StaffAttendanceActivity.this.getJsonResponse(URLs.attendance, StaffAttendanceActivity.this, new C12921());
                }
            }
        }
    }

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_staff_attendance);
        this.materialCalendarView = (MaterialCalendarView) findViewById(R.id.calendarView);
        this.materialCalendarView.setDynamicHeightEnabled(true);
        KProgressHUD progressHUD = KProgressHUD.create(this);
        this._Present = (TextView) findViewById(R.id.present);
        this._Absent = (TextView) findViewById(R.id.absent);
        this._Percentage = (TextView) findViewById(R.id.annual_percentage);
        this._Holidays = (TextView) findViewById(R.id.holidays);
        this._totalWorking = (TextView) findViewById(R.id.working_days);
        this._totalPresent = (TextView) findViewById(R.id.present_days);
        this._Present.setText("");
        this._Absent.setText("");
        this._Percentage.setText("");
        this._Holidays.setText("");
        this._totalWorking.setText("");
        this._totalPresent.setText("");
        if (UserStaticData.user_type == 1) {
            setTitle("My Attendance");
            findViewById(R.id.layout_candidate_selection).setVisibility(8);
            this.params.put("teacher_id", ProfileInfo.getInstance().getLoginData().get("userId"));
            getJsonResponse(URLs.attendance, this, new C12911());
            return;
        }
        setTitle("Staff Attendance");
        findViewById(R.id.layout_candidate_selection).setVisibility(0);
        this.teachers = (Spinner) findViewById(R.id.spnr_teacher);
//        getSpinnerData(this, URLs.all_teacher_codes, this.teachers, new C12932());
        findViewById(R.id.btn_search).setVisibility(8);
    }

    public void processJSONResult(String jsonResult) {
        try {
            int i;
            JSONObject jsonObject1;
            String Present;
            String Absent;
            this.values.clear();
            ArrayList<String> absentLIst = new ArrayList();
            ArrayList<String> presentLIst = new ArrayList();
            JSONObject jsonObject = new JSONObject(jsonResult);
            JSONArray jsonArray = jsonObject.getJSONArray("Response");
            for (i = 0; i < jsonArray.length(); i++) {
                jsonObject1 = jsonArray.getJSONObject(i);
                Present = jsonObject1.getString("Present");
                Absent = jsonObject1.getString("Absent");
                this._Present.setText(Present);
                this._Absent.setText(Absent);
            }
            JSONArray jsonArray1 = jsonObject.getJSONArray("yearAttendance");
            for (i = 0; i < jsonArray1.length(); i++) {
                jsonObject1 = jsonArray1.getJSONObject(i);
                Present = jsonObject1.getString("Present");
                Absent = jsonObject1.getString("Absent");
                this._Present.setText(Present);
                this._Absent.setText(Absent);
            }
            JSONArray jsonArray2 = jsonObject.getJSONArray("absentList");
            for (i = 0; i < jsonArray2.length(); i++) {
                absentLIst.add(jsonArray2.getJSONObject(i).getString("attendenceDate").split(" ")[0]);
            }
            JSONArray jsonArray3 = jsonObject.getJSONArray("presentList");
            for (i = 0; i < jsonArray3.length(); i++) {
                presentLIst.add(jsonArray3.getJSONObject(i).getString("attendenceDate").split(" ")[0]);
            }
            this._Percentage.setText(jsonObject.getString("percentage"));
            addDecorators(presentLIst, absentLIst);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void addDecorators(ArrayList<String> presents, ArrayList<String> absents) {
        ArrayList<CalendarDay> presentdays = new ArrayList();
        ArrayList<CalendarDay> absentdays = new ArrayList();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Iterator it = presents.iterator();
        while (it.hasNext()) {
            try {
                Date date = sdf.parse((String) it.next());
                Calendar cal = sdf.getCalendar();
                cal.setTime(date);
//                presentdays.add(CalendarDay.from(cal));
                this.materialCalendarView.addDecorator(new DateDecorator(this, -16711936, getResources().getDrawable(R.drawable.circle_green), presentdays));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        it = absents.iterator();
        while (it.hasNext()) {
            try {
                Date date = sdf.parse((String) it.next());
                cal = sdf.getCalendar();
                cal.setTime(date);
//                boolean add = absentdays.add(CalendarDay.from(cal));
                this.materialCalendarView.addDecorator(new DateDecorator(this, -65536, getResources().getDrawable(R.drawable.circle_red), absentdays));
            } catch (ParseException e2) {
                e2.printStackTrace();
            }
        }
    }
}
