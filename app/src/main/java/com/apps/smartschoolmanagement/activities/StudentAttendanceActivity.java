package com.apps.smartschoolmanagement.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.apps.smartschoolmanagement.R;
import com.apps.smartschoolmanagement.models.UserStaticData;
import com.apps.smartschoolmanagement.utils.DateDecorator;
import com.apps.smartschoolmanagement.utils.JsonClass;
import com.apps.smartschoolmanagement.utils.URLs;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.threeten.bp.LocalDate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
    String schoolids,studentsid,divid;
    int rollno;
    SharedPreferences sp;
    String school = null;
    String std = null;
    String div = null;
    int roll;
    ArrayList<String> adate = new ArrayList<>();
    int count = 0;

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

    class atte implements VolleyCallbackJSONArray {
        @Override
        public void onSuccess(JSONArray jsonArray) {
            for (int i = 0; i < jsonArray.length(); i++) {
                try {
                    JSONObject obj = jsonArray.getJSONObject(i);
                    String noList[] = obj.getString("rollNo").split(",");
                    List<String> numberList = Arrays.asList(noList);
                    if (numberList.contains(String.valueOf(roll))) {
                        adate.add(obj.getString("date"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            addDecorators(null, adate);
            Log.e("date", adate.toString());
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
        setContentView(R.layout.layout_student_attendance2);
        setTitle("Student's Attendance");
        this.materialCalendarView = (MaterialCalendarView) findViewById(R.id.calendarView);
        this.materialCalendarView.setDynamicHeightEnabled(true);
//        this.materialCalendarView.setArrowColor(getResources().getColor(R.color.colorPrimary));
        this.materialCalendarView.setAllowClickDaysOutsideCurrentMonth(false);
        KProgressHUD progressHUD = KProgressHUD.create(this);
        /*this._Present = (TextView) findViewById(R.id.present);
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
        this._totalPresent.setText("");*/

        sp = PreferenceManager.getDefaultSharedPreferences(this);
        String usertype = sp.getString("usertype","");
        if(usertype.equals("student")) {
            this.std = (sp.getString("stdId", ""));
            this.div = (sp.getString("DivId", ""));
            this.school = (sp.getString("schoolid", ""));
            this.roll = (sp.getInt("roll", 0));
            Log.e("rollbun", String.valueOf(roll));
        }

        this.std = getIntent().getStringExtra("studentid");
        this.school = getIntent().getStringExtra("schoolid");
        this.roll = Integer.parseInt(getIntent().getStringExtra("rollno"));
        this.div = getIntent().getStringExtra("divid");
//
//        this.std = (sp.getString(studentsid,""));
//        this.div = (sp.getString(schoolids, ""));
//        this.school = (sp.getString(divid, ""));
//        this.roll =((int) sp.getInt(String.valueOf(rollno), 0));

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, 0);
        calendar.set(Calendar.DATE, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        Date nextMonthFirstDay = calendar.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String start = sdf.format(nextMonthFirstDay);
        calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        Date nextMonthLastDay = calendar.getTime();
        String end = sdf.format(nextMonthLastDay);
        getJsonResponse(URLs.getAttends + div + "&std=" + std + "&school=" + school + "&startingDate=" + start + "&endDate=" + end, this, new atte());
        this.materialCalendarView.setOnMonthChangedListener(this);
        if (UserStaticData.user_type == 0) {
            setTitle("My Attendance");
            //   findViewById(R.id.layout_candidate_selection).setVisibility(8);
            //   this.params.put("student_id", ProfileInfo.getInstance().getLoginData().get("userId"));
            Date today = new Date();
            Calendar cal = Calendar.getInstance();
            cal.setTime(today);
            //     this.params.put("month", "" + (cal.get(2) + 1));
            //  getJsonResponse(URLs.attendance, this, new C12981());
        } else if (UserStaticData.user_type == 1) {
            //       setTitle("Student's Attendance");
            //      findViewById(R.id.layout_candidate_selection).setVisibility(0);
            //        this.classes = (Spinner) findViewById(R.id.spnr_class);
            //         this.student = (Spinner) findViewById(R.id.spnr_student);
//            getSpinnerData(this, URLs.class_codes, this.classes, new C13002());
            //           findViewById(R.id.btn_search).setOnClickListener(new C13023());
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
       /* Iterator it = presents.iterator();
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
        }*/
        for (int i = 0; i < absents.size(); i++) {
            absentdays.add(CalendarDay.from(LocalDate.parse(absents.get(i))));
            Log.e("abc", absentdays.toString());
        }
            this.materialCalendarView.addDecorator(new DateDecorator(this, -65536, getResources().getDrawable(R.drawable.circle_red), absentdays));
    }

//    public void ddl_month_valueChange(ValueChangeEvent event) {
//        int v_month = Integer.parseInt(event.getNewValue().toString()) - 1;
//        java.util.Calendar c1 = java.util.Calendar.getInstance();
//        c1.set(2011, v_month, 1);
//        Date d_set_att_from = c1.getTime();
//        cal_att_from_date.setValue(d_set_att_from);
//        c1.add(java.util.Calendar.MONTH, 1);
//        c1.add(java.util.Calendar.DATE, -1);
//        Date d_set_att_to = c1.getTime();
//        cal_att_to_date.setValue(d_set_att_to);
//    }

    public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {

        Calendar calendar = Calendar.getInstance();
        calendar.set(date.getYear(), date.getMonth() - 1, 1);
        calendar.add(Calendar.MONTH, 0);
        calendar.set(Calendar.DATE, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        Date nextMonthFirstDay = calendar.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String start = sdf.format(nextMonthFirstDay);
        calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        Date nextMonthLastDay = calendar.getTime();
        String end = sdf.format(nextMonthLastDay);
        getJsonResponse(URLs.getAttends + div + "&std=" + std + "&school=" + school + "&startingDate=" + start + "&endDate=" + end, this, new atte());
        // getJsonResponse(URLs.attendance, this, new C13034());
    }


//    public void addDefaultDecorators() {
//        this.materialCalendarView.setSelectedDate(CalendarDay.today());
//        this.materialCalendarView.addDecorator(new C13045());
//    }
}
