package com.apps.smartschoolmanagement.activities;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.apps.smartschoolmanagement.R;
import com.apps.smartschoolmanagement.adapters.AttendancesAdapter;
import com.apps.smartschoolmanagement.fragments.StudentAttendance;
import com.apps.smartschoolmanagement.utils.AnimationSlideUtil;
import com.apps.smartschoolmanagement.utils.JsonClass;
import com.apps.smartschoolmanagement.utils.JsonFragment;
import com.apps.smartschoolmanagement.utils.URLs;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class StudentMarkList extends JsonClass {
    Spinner std,exam,div,student;
    TableLayout horizontalTable;
    String total;
    ViewGroup footer;
    ViewGroup header;
    int examid;

    List<String> stdname = new ArrayList<>();
    List<Integer> stdId = new ArrayList<>();
    ArrayList<String> subjects = new ArrayList<>();
    List<String> Examname = new ArrayList<>();
    List<Integer> ExamId = new ArrayList<>();
    List<String> divName = new ArrayList<>();
    List<Integer> divId = new ArrayList<>();
    List<String> StudentName = new ArrayList<>();
    List<Integer> StudentID = new ArrayList<>();
    SharedPreferences sp;
    String stdid, studentid,usertype,channel;
    HorizontalScrollView root;
    String SubjectsName;
    ListView listView;
    String[] items;
    LinearLayout teacherdata;
    int sum;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_mark_list);
        this.std = (Spinner) findViewById(R.id.spnr_std);
        this.div = (Spinner) findViewById(R.id.spnr_div);
        this.student = (Spinner) findViewById(R.id.spnr_student);
        this.exam = (Spinner) findViewById(R.id.spnr_exam);
        this.teacherdata = findViewById(R.id.layout_candidate_selection);
        sp = PreferenceManager.getDefaultSharedPreferences(this);
        usertype = sp.getString("usertype", "");
        channel = (sp.getString("schoolid", ""));

        if (usertype != null) {
            if ("student".equals(usertype)) {
                setTitle("Marks");
                getJsonResponse(URLs.getExam + stdid, this, new StudentMarkList.getExamApi());
                stdid = (sp.getString("stdId", ""));
                studentid = (sp.getString("studentId", ""));
            } else {
                setTitle("Student Marks");
                teacherdata.setVisibility(View.VISIBLE);
                getJsonResponse(URLs.getStd + channel,StudentMarkList.this, new StudentMarkList.getStudentData());
            }
        }
        this.listView = (ListView) findViewById(R.id.listview);
        this.horizontalTable = (TableLayout) findViewById(R.id.table_layout);
        LayoutInflater inflater = getLayoutInflater();
        this.header = (ViewGroup) inflater.inflate(R.layout.layout_marks_titles, this.listView, false);
        this.footer = (ViewGroup) inflater.inflate(R.layout.layout_marks_total, this.listView, false);
        this.root = (HorizontalScrollView) findViewById(R.id.marks);
        findViewById(R.id.btn_search).setOnClickListener(new View.OnClickListener() {
                                                             @Override
                                                             public void onClick(View view) {
                                                                 getJsonResponse(URLs.getSubjectFormat + stdid, StudentMarkList.this, new StudentMarkList.getSubjectFormatApi());
                                                             }
                                                         }
        );
        std.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                divName.removeAll(divName);
                divId.removeAll(divId);
                stdid = String.valueOf(stdId.get(i));
                getJsonResponse(URLs.getDiv + stdid, StudentMarkList.this, new StudentMarkList.divApiCall());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        div.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                StudentName.removeAll(StudentName);
                StudentID.removeAll(StudentID);
                Integer divid = divId.get(i);
                getJsonResponse(URLs.getStudentByStdAndDiv + stdid + "&div=" + divid + "&School=" + channel, StudentMarkList.this, new StudentMarkList.StudentApiCall());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        student.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Examname.removeAll(Examname);
                ExamId.removeAll(ExamId);
                studentid = String.valueOf(divId.get(i));
                getJsonResponse(URLs.getExam + stdid, StudentMarkList.this, new StudentMarkList.getExamApi());
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        exam.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                examid = ExamId.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    class getMarksFormatApi implements VolleyCallbackJSONObject {
        @Override
        public void onSuccess(JSONObject jSONObject) {
            try {
                horizontalTable.removeAllViews();
                horizontalTable.addView(header);

                String format = jSONObject.getString("studentMarks");
                String[] items = format.split(",");
                int size = items.length;
                int[] arr = new int[size];
                for (int i = 0; i < items.length; i++) {

                    View view = getLayoutInflater().inflate(R.layout.layout_marks_english, null);
                    ((TextView) view.findViewById(R.id.subject)).setText(subjects.get(i));
                    ((TextView) view.findViewById(R.id.obtained)).setText(items[i]);
                    arr[i] = Integer.parseInt(items[i]);
                    sum += arr[i];
                    horizontalTable.addView(view);
                }
                horizontalTable.addView(footer);
                ((TextView) footer.findViewById(R.id.obtained_marks)).setText(sum + "");
                ((TextView) footer.findViewById(R.id.total_marks)).setText("200");

                AnimationSlideUtil.fadeIn(StudentMarkList.this, horizontalTable);
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(StudentMarkList.this, e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }

    class getSubjectFormatApi implements VolleyCallbackJSONObject {
        @Override
        public void onSuccess(JSONObject jSONObject) {
            try {
                String format = null;
                format = jSONObject.getString("fromat");
                items = format.split(",");
                for (String item : items) {
                    subjects.add(item);
                }
                AnimationSlideUtil.fadeIn(StudentMarkList.this, horizontalTable);
                getJsonResponse(URLs.getMarksFormat + examid + "&studentRoll=" + studentid, StudentMarkList.this, new StudentMarkList.getMarksFormatApi());

            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(StudentMarkList.this, e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }

    class getExamApi implements JsonClass.VolleyCallbackJSONArray {
        @Override
        public void onSuccess(JSONArray jsonArray) {
            for (int i = 0; i < jsonArray.length(); i++) {
                try {
                    JSONObject obj = jsonArray.getJSONObject(i);
                    Examname.add(obj.getString("title"));
                    ExamId.add(obj.getInt("id"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(StudentMarkList.this, R.layout.spinner_dropdown_custom, Examname);
            exam.setAdapter(spinnerArrayAdapter);
            Log.e("stdData", jsonArray.toString());
        }
    }
    class getStudentData implements JsonClass.VolleyCallbackJSONArray {
        @Override
        public void onSuccess(JSONArray jSONObject) {
            for (int i = 0; i < jSONObject.length(); i++) {
                try {
                    JSONObject obj = jSONObject.getJSONObject(i);
                    stdname.add(obj.getString("stdName"));
                    stdId.add(obj.getInt("id"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(StudentMarkList.this, R.layout.spinner_dropdown_custom, stdname);
            std.setAdapter(spinnerArrayAdapter);
        }
    }

    class divApiCall implements JsonClass.VolleyCallbackJSONArray {

        @Override
        public void onSuccess(JSONArray jsonArray) {
            for (int i = 0; i < jsonArray.length(); i++) {
                try {
                    JSONObject obj = jsonArray.getJSONObject(i);
                    divName.add(obj.getString("name"));
                    divId.add(obj.getInt("id"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(StudentMarkList.this, R.layout.spinner_dropdown_custom, divName);
            div.setAdapter(spinnerArrayAdapter);
        }
    }
    class StudentApiCall implements JsonClass.VolleyCallbackJSONArray {

        @Override
        public void onSuccess(JSONArray jsonArray) {
            for (int i = 0; i < jsonArray.length(); i++) {
                try {
                    JSONObject obj = jsonArray.getJSONObject(i);
                    StudentName.add(obj.getString("name"));
                    StudentID.add(obj.getInt("id"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(StudentMarkList.this, R.layout.spinner_dropdown_custom, StudentName);
            student.setAdapter(spinnerArrayAdapter);
            //final ArrayAdapter<String> listArrayAdapter = new ArrayAdapter<String>(getActivity().getApplicationContext(),R.layout.list_with_toggal_button,R.id.tv_item,StudentName);

        }
    }
}
