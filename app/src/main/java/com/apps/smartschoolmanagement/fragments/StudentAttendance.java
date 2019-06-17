package com.apps.smartschoolmanagement.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.apps.smartschoolmanagement.activities.BusesListActivity;
import com.apps.smartschoolmanagement.adapters.AttendancesAdapter;
import com.apps.smartschoolmanagement.utils.URLs;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;
import com.apps.smartschoolmanagement.R;
import com.apps.smartschoolmanagement.utils.JsonFragment;
import com.apps.smartschoolmanagement.utils.ProfileInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;


public class StudentAttendance extends JsonFragment implements OnMonthChangedListener {

    String rollnumber="";
    Spinner classes;
    MaterialCalendarView materialCalendarView;
    private FragmentActivity myContext;
    View rootView;
    Spinner student;
    SharedPreferences sp;
    ListView studentList;
    List<String> stdname = new ArrayList<>();
    List<Integer> stdId = new ArrayList<>();
    List<String> divName = new ArrayList<>();
    List<Integer> divId = new ArrayList<>();
    List<String> StudentName = new ArrayList<>();
    List<Integer> StudentRollNo  = new ArrayList<>();
    static Integer stdid ;
    Button submit;

    class serviceCall implements VolleyCallbackJSONArray{
        @Override
        public void onSuccess(JSONArray jSONObject) {
           for (int i=0;i<jSONObject.length();i++){
               try {
                   JSONObject obj = jSONObject.getJSONObject(i);
                   stdname.add(obj.getString("stdName"));
                   stdId.add(obj.getInt("id"));
               } catch (JSONException e) {
                   e.printStackTrace();
               }
           }
            final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getActivity().getApplicationContext(),R.layout.spinner_dropdown_custom,stdname);
            classes.setAdapter(spinnerArrayAdapter);
        }
    }

    class addAttendance implements VolleyCallbackJSONObject{

        @Override
        public void onSuccess(JSONObject jSONObject) {
            try {
                Toast.makeText(getActivity().getApplicationContext(), (CharSequence) jSONObject.get("message"),Toast.LENGTH_LONG).show();
            } catch (JSONException e) {
                Toast.makeText(getActivity().getApplicationContext(),e.getMessage() ,Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        }
    }

    class divApiCall implements  VolleyCallbackJSONArray{

        @Override
        public void onSuccess(JSONArray jsonArray) {
            for (int i=0;i<jsonArray.length();i++){
                try {
                    JSONObject obj = jsonArray.getJSONObject(i);
                    divName.add(obj.getString("name"));
                    divId.add(obj.getInt("id"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getActivity().getApplicationContext(),R.layout.spinner_dropdown_custom,divName);
            student.setAdapter(spinnerArrayAdapter);
        }
    }

    class  StudentApiCall implements VolleyCallbackJSONArray{

        @Override
        public void onSuccess(JSONArray jsonArray) {
            for (int i=0;i<jsonArray.length();i++){
                try {
                    JSONObject obj = jsonArray.getJSONObject(i);
                    StudentName.add(""+ obj.getInt("rollNo") + ": " + obj.getString("name"));
                    StudentRollNo.add(obj.getInt("rollNo"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            //final ArrayAdapter<String> listArrayAdapter = new ArrayAdapter<String>(getActivity().getApplicationContext(),R.layout.list_with_toggal_button,R.id.tv_item,StudentName);
            AttendancesAdapter attendancesAdapter = new AttendancesAdapter(getActivity().getApplicationContext(),R.layout.list_with_toggal_button,StudentName,StudentAttendance.this);
            studentList.setAdapter(attendancesAdapter);
        }
    }

    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.rootView = inflater.inflate(R.layout.layout_student_attendance, container, false);
        this.materialCalendarView = (MaterialCalendarView) this.rootView.findViewById(R.id.calendarView);
        this.materialCalendarView.setDynamicHeightEnabled(true);
//        this.materialCalendarView.setArrowColor(getResources().getColor(R.color.colorPrimary));
        this.materialCalendarView.setHeaderTextAppearance(R.style.month_style);
        this.materialCalendarView.setOnMonthChangedListener(this);
        materialCalendarView.setSelectedDate(CalendarDay.today());
        this.classes = (Spinner) this.rootView.findViewById(R.id.spnr_class);
        this.student = (Spinner) this.rootView.findViewById(R.id.spnr_student);
        this.studentList = this.rootView.findViewById(R.id.listview1);
     //   Switch onoff = this.rootView.findViewById(R.id.switch1);
        sp = PreferenceManager.getDefaultSharedPreferences(rootView.getContext());
        String channel = (sp.getString("schoolid", ""));
        Log.e("schoolIdLog",channel);

        studentList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.e("listclick",Integer.toString(i));
                Switch onoff = view.findViewById(R.id.switch1);
                if(onoff.isChecked()){
                    onoff.setChecked(false);
                }
                else
                {
                    onoff.setChecked(true);
                }
            }
        });



        submit = this.rootView.findViewById(R.id.btn_submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             String std = String.valueOf(stdId.get(classes.getSelectedItemPosition()));
             String div = String.valueOf(divId.get(student.getSelectedItemPosition()));
             int day = materialCalendarView.getSelectedDate().getDay();
             int year = materialCalendarView.getSelectedDate().getYear();
             int month = materialCalendarView.getSelectedDate().getMonth();
             String date = "";
                if(month<10 & day<10) {
                    date = year + "-0" + month + "-0" + day;
             }
             else if(month<10){
                  date = year + "-0" + month + "-" + day;
             }
             else if(day<10) {
                  date = year + "-" + month + "-0" + day;
             }
             else{
                    date = year + "-" + month + "-" + day;
             }
                if(!rollnumber.isEmpty() && rollnumber.endsWith(",")) {
                    rollnumber = rollnumber.substring(0, rollnumber.length() - 1);

                    JSONObject sendData = new JSONObject();
                    try {
                        sendData.put("rollNo",rollnumber);
                        sendData.put("date",date);
                        sendData.put("school",Integer.parseInt(channel));
                        sendData.put("std",Integer.parseInt(std));
                        sendData.put("div",Integer.parseInt(div));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Log.e("json",sendData.toString());
                    postJsonResponse(URLs.addAttendce,sendData,rootView,new StudentAttendance.addAttendance());

                }
            }
        });





        //class api call
        getJsonResponse(URLs.getStd+channel,rootView, new StudentAttendance.serviceCall());

        classes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                divName.removeAll(divName);
                divId.removeAll(divId);
                stdid = stdId.get(i);
                getJsonResponse(URLs.getDiv+stdid+"&school="+channel,rootView,new StudentAttendance.divApiCall());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        student.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                StudentName.removeAll(StudentName);
                StudentRollNo.removeAll(StudentRollNo);
                Integer divid = divId.get(i);
                getJsonResponse(URLs.getStudentByStdAndDiv+stdid+"&div="+divid+"&School="+channel ,rootView, new StudentAttendance.StudentApiCall() );
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        return this.rootView;
    }

    public void myClickHandler(int postion,Boolean onoff )
    {
        if(onoff==true)
        {
            Log.e("test",postion + "");
            rollnumber = rollnumber + "" + StudentRollNo.get(postion) + ",";
        }
        else
        {
            if(rollnumber.endsWith(",")) {
                rollnumber = rollnumber.substring(0, rollnumber.length() - 1);
            }if(rollnumber.contains("" + StudentRollNo.get(postion))){
             List a= new LinkedList(Arrays.asList(rollnumber.split(",")));
             int i = a.indexOf(StudentRollNo.get(postion).toString());
             a.remove(i);
             StringBuilder sb = new StringBuilder();
             for(int j=0;j<a.size();j++){
                 sb.append(a.get(j)+",");
             }
             rollnumber = sb.toString();
            }
        }
        Log.e("roll",rollnumber);
    }


    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.rootView.findViewById(R.id.layout_candidate_selection).setVisibility(0);
//        getSpinnerData(getActivity(), this.rootView, URLs.class_codes, this.classes, new C13651());
    }
    public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {
        this.params.put("student_id", ProfileInfo.getInstance().getLoginData().get("userId"));
        this.params.put("month", "" + (date.getMonth() + 1));
//        getJsonResponse(URLs.attendance, this.rootView, new C13662());
    }

}
