package com.apps.smartschoolmanagement.fragments;

import android.app.Activity;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.TextView;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;
import com.apps.smartschoolmanagement.R;
import com.apps.smartschoolmanagement.utils.DateDecorator;
import com.apps.smartschoolmanagement.utils.JsonFragment;
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

public class TeacherAttendance extends JsonFragment implements OnMonthChangedListener {
    TextView _Absent;
    TextView _Holidays;
    TextView _Percentage;
    TextView _Present;
    TextView _totalPresent;
    TextView _totalWorking;
    MaterialCalendarView materialCalendarView;
    private FragmentActivity myContext;
    View rootView;
    String teacherid = null;
    Spinner teachers;

    /* renamed from: com.apps.smartschoolmanagement.fragments.TeacherAttendance$1 */
    class C13761 implements VolleyCallback {
        C13761() {
        }

        public void onSuccess(String result) {
            TeacherAttendance.this.processJSONResult(result);
        }
    }

    /* renamed from: com.apps.smartschoolmanagement.fragments.TeacherAttendance$2 */
    class C13772 implements VolleyCallback {
        C13772() {
        }

        public void onSuccess(String result) {
            if (!TextUtils.isEmpty(result)) {
                TeacherAttendance.this.processJSONResult(result);
            }
        }
    }

    /* renamed from: com.apps.smartschoolmanagement.fragments.TeacherAttendance$3 */
    class C13783 implements DayViewDecorator {
        C13783() {
        }

        public boolean shouldDecorate(CalendarDay day) {
//            if (day.getCalendar().get(7) == 1) {
//                return true;

            return false;
        }

        public void decorate(DayViewFacade view) {
            view.addSpan(new ForegroundColorSpan(-65536));
        }
    }

    public void onAttach(Activity activity) {
        this.myContext = (FragmentActivity) activity;
        super.onAttach(activity);
    }

    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.rootView = inflater.inflate(R.layout.layout_staff_attendance, container, false);
        this.materialCalendarView = (MaterialCalendarView) this.rootView.findViewById(R.id.calendarView);
        this.materialCalendarView.setDynamicHeightEnabled(true);
//        this.materialCalendarView.setArrowColor(getResources().getColor(R.color.colorPrimary));
        this.materialCalendarView.setHeaderTextAppearance(R.style.month_style);
        this.materialCalendarView.setOnMonthChangedListener(this);
        this._Present = (TextView) this.rootView.findViewById(R.id.present);
        this._Absent = (TextView) this.rootView.findViewById(R.id.absent);
        this._Percentage = (TextView) this.rootView.findViewById(R.id.annual_percentage);
        this._Holidays = (TextView) this.rootView.findViewById(R.id.holidays);
        this._totalWorking = (TextView) this.rootView.findViewById(R.id.working_days);
        this._totalPresent = (TextView) this.rootView.findViewById(R.id.present_days);
        this._Present.setText("");
        this._Absent.setText("");
        this._Percentage.setText("");
        this._Holidays.setText("");
        this._totalWorking.setText("");
        this._totalPresent.setText("");
        return this.rootView;
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.rootView.findViewById(R.id.layout_candidate_selection).setVisibility(0);
        this.teachers = (Spinner) this.rootView.findViewById(R.id.spnr_teacher);
        this.rootView.findViewById(R.id.layout_candidate_selection).setVisibility(8);
        this.params.put("teacher_id", ProfileInfo.getInstance().getLoginData().get("userId"));
        Date today = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(today);
        this.params.put("month", "" + (cal.get(2) + 1));
      //  getJsonResponse(URLs.attendance, this.rootView, new C13761());
        this.rootView.findViewById(R.id.btn_search).setVisibility(8);
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
        addDefaultDecorators();
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
                this.materialCalendarView.addDecorator(new DateDecorator(getActivity(), -16711936, getResources().getDrawable(R.drawable.circle_green), presentdays));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        it = absents.iterator();
        while (it.hasNext()) {
            try {
                Date date = sdf.parse((String) it.next());
                Calendar cal = sdf.getCalendar();
                cal.setTime(date);
//                absentdays.add(CalendarDay.from(cal));
                this.materialCalendarView.addDecorator(new DateDecorator(getActivity(), -65536, getResources().getDrawable(R.drawable.circle_red), absentdays));
            } catch (ParseException e2) {
                e2.printStackTrace();
            }
        }
    }

    public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {
        this.params.put("student_id", ProfileInfo.getInstance().getLoginData().get("userId"));
        this.params.put("month", "" + (date.getMonth() + 1));
       // getJsonResponse(URLs.attendance, this.rootView, new C13772());
    }

    public void addDefaultDecorators() {
//        this.materialCalendarView.setSelectedDate(CalendarDay.today());
        this.materialCalendarView.addDecorator(new C13783());
    }
}
