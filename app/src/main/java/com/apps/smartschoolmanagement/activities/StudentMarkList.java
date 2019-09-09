package com.apps.smartschoolmanagement.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
import java.util.Arrays;
import java.util.List;

public class StudentMarkList extends JsonClass {
    Spinner exam;
    TableLayout horizontalTable;

    ViewGroup footer;
    ViewGroup header;
    int examid;
    List<String> Examname = new ArrayList<>();
    List<Integer> ExamId = new ArrayList<>();
    SharedPreferences sp;
    String stdid,studentid;
    String SubjectsName,aa;
    ListView listView;
    String[] itemss;
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
        getJsonResponse(URLs.getExam , this, new StudentMarkList.getExamApi());
        exam.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                examid = ExamId.get(i);
                getJsonResponse(URLs.getSubjectFormat + stdid, StudentMarkList.this, new StudentMarkList.getSubjectFormatApi());
                getJsonResponse(URLs.getMarksFormat + examid + "&studentRoll=" + studentid , StudentMarkList.this, new StudentMarkList.getMarksFormatApi());

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
                horizontalTable.addView(header);
//                StudentMarkList.this.findViewById(R.id.layout_loading).setVisibility(0);
                String format = jSONObject.getString("studentMarks");
                String[] items = format.split(",");
                for (String itemas : itemss) {
                 aa = itemas;
                }
                    for (String item : items)
                {
                    View view = getLayoutInflater().inflate(R.layout.layout_marks_english, null);
                    ((TextView) view.findViewById(R.id.subject)).setText(aa);
                    ((TextView) view.findViewById(R.id.obtained)).setText(item);
                    horizontalTable.addView(view);
                }
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
//                horizontalTable.addView(header);
//                StudentMarkList.this.findViewById(R.id.layout_loading).setVisibility(0);
                String format = jSONObject.getString("fromat");
                itemss = format.split(",");
//                for (String item : items)
//                {
//                    SubjectsName = item;
//                    View view = getLayoutInflater().inflate(R.layout.layout_marks_english, null);
//                    horizontalTable.addView(view);
//                    View view = getLayoutInflater().inflate(R.layout.layout_marks_english, null);
//                    ((TextView) view.findViewById(R.id.subject)).setText(item);
//                    horizontalTable.addView(view);
//                }
                AnimationSlideUtil.fadeIn(StudentMarkList.this, horizontalTable);
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
