package com.apps.smartschoolmanagement.activities;

import android.os.Bundle;
import androidx.annotation.Nullable;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Spinner;
import android.widget.TextView;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;
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

public class StudentAttendanceActivity extends JsonClass implements OnMonthChangedListener {
    TextView _Absent;
    TextView _Holidays;
    TextView _Percentage;
    TextView _Present;
    TextView _totalPresent;
    TextView _totalWorking;
    Spinner classes;
    String classid = null;
    MaterialCalendarView materialCalendarView;
    Spinner student;
    String studentid = null;
    Calendar cal;
    /* renamed from: com.apps.smartschoolmanagement.activities.StudentAttendanceActivity$1 */
    class C12981 implements VolleyCallback {
        C12981() {
        }

        public void onSuccess(String result) {
            if (!TextUtils.isEmpty(result)) {
                StudentAttendanceActivity.this.processJSONResult(result);
            }
        }
    }

    /* renamed from: com.apps.smartschoolmanagement.activities.StudentAttendanceActivity$2 */
    class C13002 implements VolleyCallback {

        /* renamed from: com.apps.smartschoolmanagement.activities.StudentAttendanceActivity$2$1 */
        class C12991 implements VolleyCallback {
            C12991() {
            }

            public void onSuccess(String result) {
                if (result != null) {
                    StudentAttendanceActivity.this.studentid = result;
                }
            }
        }

        C13002() {
        }

        public void onSuccess(String result) {
            if (result != null) {
                StudentAttendanceActivity.this.classid = result;
            }
            StudentAttendanceActivity.this.getSpinnerData(StudentAttendanceActivity.this, URLs.student_codes, StudentAttendanceActivity.this.student, "class_id," + StudentAttendanceActivity.this.classid, new C12991());
        }
    }

    /* renamed from: com.apps.smartschoolmanagement.activities.StudentAttendanceActivity$3 */
    class C13023 implements OnClickListener {

        /* renamed from: com.apps.smartschoolmanagement.activities.StudentAttendanceActivity$3$1 */
        class C13011 implements VolleyCallback {
            C13011() {
            }

            public void onSuccess(String result) {
                if (!TextUtils.isEmpty(result)) {
                    StudentAttendanceActivity.this.processJSONResult(result);
                }
            }
        }

        C13023() {
        }

        public void onClick(View view) {
            if (StudentAttendanceActivity.this.classes.getSelectedItemPosition() > 0) {
                StudentAttendanceActivity.this.params.put("class_id", StudentAttendanceActivity.this.classid);
                StudentAttendanceActivity.this.params.put("student_id", StudentAttendanceActivity.this.studentid);
                StudentAttendanceActivity.this.getJsonResponse(URLs.attendance, StudentAttendanceActivity.this, new C13011());
            }
        }
    }

    /* renamed from: com.apps.smartschoolmanagement.activities.StudentAttendanceActivity$4 */
    class C13034 implements VolleyCallback {
        C13034() {
        }

        public void onSuccess(String result) {
            if (!TextUtils.isEmpty(result)) {
                StudentAttendanceActivity.this.processJSONResult(result);
            }
        }
    }

    /* renamed from: com.apps.smartschoolmanagement.activities.StudentAttendanceActivity$5 */
    class C13045 implements DayViewDecorator {
        C13045() {
        }

        public boolean shouldDecorate(CalendarDay day) {
//            if (day.getDate().get(7) == 1) {
//                return true;
//            }
            return false;
        }

        public void decorate(DayViewFacade view) {
            view.addSpan(new ForegroundColorSpan(-65536));
        }
    }

    public static int getMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(2);
    }

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if (UserStaticData.user_type == 0) {
            setTheme(R.style.AppTheme1);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_student_attendance);
        setTitle("Student's Attendance");
        this.materialCalendarView = (MaterialCalendarView) findViewById(R.id.calendarView);
        this.materialCalendarView.setDynamicHeightEnabled(true);
//        this.materialCalendarView.setArrowColor(getResources().getColor(R.color.colorPrimary));
        this.materialCalendarView.setAllowClickDaysOutsideCurrentMonth(false);
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
        this.materialCalendarView.setOnMonthChangedListener(this);
        if (UserStaticData.user_type == 0) {
            setTitle("My Attendance");
            findViewById(R.id.layout_candidate_selection).setVisibility(8);
            this.params.put("student_id", ProfileInfo.getInstance().getLoginData().get("userId"));
            Date today = new Date();
            Calendar cal = Calendar.getInstance();
            cal.setTime(today);
            this.params.put("month", "" + (cal.get(2) + 1));
            getJsonResponse(URLs.attendance, this, new C12981());
        } else if (UserStaticData.user_type == 1) {
            setTitle("Student's Attendance");
            findViewById(R.id.layout_candidate_selection).setVisibility(0);
            this.classes = (Spinner) findViewById(R.id.spnr_class);
            this.student = (Spinner) findViewById(R.id.spnr_student);
//            getSpinnerData(this, URLs.class_codes, this.classes, new C13002());
            findViewById(R.id.btn_search).setOnClickListener(new C13023());
        }
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
        this.materialCalendarView.removeDecorators();
//        addDefaultDecorators();
        ArrayList<CalendarDay> presentdays = new ArrayList();
        ArrayList<CalendarDay> absentdays = new ArrayList();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Iterator it = presents.iterator();
        while (it.hasNext()) {
            try {
                Date date = sdf.parse((String) it.next());
                Calendar cal = sdf.getCalendar();
                cal.setTime(date);
//                boolean add = presentdays.add(CalendarDay.from(cal));
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

    public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {
        this.params.put("student_id", ProfileInfo.getInstance().getLoginData().get("userId"));
        this.params.put("month", "" + (date.getMonth() + 1));
        getJsonResponse(URLs.attendance, this, new C13034());
    }

//    public void addDefaultDecorators() {
//        this.materialCalendarView.setSelectedDate(CalendarDay.today());
//        this.materialCalendarView.addDecorator(new C13045());
//    }
}
