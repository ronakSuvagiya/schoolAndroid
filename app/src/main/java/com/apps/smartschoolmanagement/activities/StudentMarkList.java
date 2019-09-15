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
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.apps.smartschoolmanagement.R;
import com.apps.smartschoolmanagement.utils.AnimationSlideUtil;
import com.apps.smartschoolmanagement.utils.JsonClass;
import com.apps.smartschoolmanagement.utils.URLs;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class StudentMarkList extends JsonClass {
    Spinner exam;
    TableLayout horizontalTable;
    String total;
    ViewGroup footer;
    ViewGroup header;
    int examid;
    List<String> Examname = new ArrayList<>();
    List<Integer> ExamId = new ArrayList<>();
    SharedPreferences sp;
    String stdid, studentid;
    HorizontalScrollView root;
    String SubjectsName;
    ListView listView;
    String[] items;
    int sum;
    ArrayList<String> subjects = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_mark_list);
        this.exam = (Spinner) findViewById(R.id.spnr_exam);
        sp = PreferenceManager.getDefaultSharedPreferences(this);
        stdid = (sp.getString("stdId", ""));
        studentid = (sp.getString("studentId", ""));
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
        getJsonResponse(URLs.getExam, this, new StudentMarkList.getExamApi());
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
}
