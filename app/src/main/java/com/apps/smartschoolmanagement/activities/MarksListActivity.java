package com.apps.smartschoolmanagement.activities;

import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.apps.smartschoolmanagement.R;
import com.apps.smartschoolmanagement.models.Marks;
import com.apps.smartschoolmanagement.models.UserStaticData;
import com.apps.smartschoolmanagement.utils.AnimationSlideUtil;
import com.apps.smartschoolmanagement.utils.JsonClass;
import com.apps.smartschoolmanagement.utils.ProfileInfo;
import com.apps.smartschoolmanagement.utils.URLs;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MarksListActivity extends JsonClass {
    BarChart barChart;
    BarDataSet bardataset;
    String classid = null;
    Spinner cls;
    ArrayList<BarEntry> entries = new ArrayList();
    String examid = null;
    Spinner exm;
    ViewGroup footer;
    ViewGroup header;
    TableLayout horizontalTable;
    ListView listView;
    ArrayList<String> marks = new ArrayList();
    HorizontalScrollView root;
    Spinner std;
    String studentid = null;
    ArrayList<String> subjects = new ArrayList();

    /* renamed from: com.apps.smartschoolmanagement.activities.MarksListActivity$1 */
    class C12381 implements VolleyCallback {

        /* renamed from: com.apps.smartschoolmanagement.activities.MarksListActivity$1$1 */
        class C12371 implements VolleyCallback {
            C12371() {
            }

            public void onSuccess(String result) {
                try {
                    MarksListActivity.this.processJSONResult(new JSONObject(result));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        C12381() {
        }

        public void onSuccess(String result) {
            if (result != null) {
                MarksListActivity.this.examid = result;
                MarksListActivity.this.params.put("exam_id", MarksListActivity.this.examid);
                MarksListActivity.this.params.put("student_id", ProfileInfo.getInstance().getLoginData().get("userId"));
                MarksListActivity.this.getJsonResponse(URLs.marks, MarksListActivity.this, new C12371());
            }
        }
    }

    /* renamed from: com.apps.smartschoolmanagement.activities.MarksListActivity$2 */
    class C12402 implements VolleyCallback {

        /* renamed from: com.apps.smartschoolmanagement.activities.MarksListActivity$2$1 */
        class C12391 implements VolleyCallback {
            C12391() {
            }

            public void onSuccess(String result) {
                try {
                    MarksListActivity.this.processJSONResult(new JSONObject(result));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        C12402() {
        }

        public void onSuccess(String result) {
            if (result != null) {
                MarksListActivity.this.examid = result;
                MarksListActivity.this.params.put("exam_id", MarksListActivity.this.examid);
                if (MarksListActivity.this.classid != null) {
                    MarksListActivity.this.params.put("class_id", MarksListActivity.this.classid);
                    if (MarksListActivity.this.studentid != null) {
                        MarksListActivity.this.params.put("student_id", MarksListActivity.this.studentid);
                        MarksListActivity.this.getJsonResponse(URLs.marks, MarksListActivity.this, new C12391());
                        return;
                    }
                    Toast.makeText(MarksListActivity.this, "Select Student", 0).show();
                    return;
                }
                Toast.makeText(MarksListActivity.this, "Choose Class", 0).show();
            }
        }
    }

    /* renamed from: com.apps.smartschoolmanagement.activities.MarksListActivity$3 */
    class C12423 implements VolleyCallback {

        /* renamed from: com.apps.smartschoolmanagement.activities.MarksListActivity$3$1 */
        class C12411 implements VolleyCallback {
            C12411() {
            }

            public void onSuccess(String result) {
                if (result != null) {
                    MarksListActivity.this.studentid = result;
                }
            }
        }

        C12423() {
        }

        public void onSuccess(String result) {
            if (result != null) {
                MarksListActivity.this.classid = result;
            }
            MarksListActivity.this.getSpinnerData(MarksListActivity.this, URLs.student_codes, MarksListActivity.this.std, "class_id," + MarksListActivity.this.classid, new C12411());
        }
    }

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if (UserStaticData.user_type == 0) {
            setTheme(R.style.AppTheme1);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marks);
        setTitle("Marks");
        this.barChart = (BarChart) findViewById(R.id.barchart);
        KProgressHUD progressHUD = KProgressHUD.create(this).setLabel("Loading");
        this.horizontalTable = (TableLayout) findViewById(R.id.table_layout);
        this.listView = (ListView) findViewById(R.id.listview);
        LayoutInflater inflater = getLayoutInflater();
        this.header = (ViewGroup) inflater.inflate(R.layout.layout_marks_titles, this.listView, false);
        this.footer = (ViewGroup) inflater.inflate(R.layout.layout_marks_total, this.listView, false);
        this.root = (HorizontalScrollView) findViewById(R.id.marks);
        if (UserStaticData.user_type == 0) {
            findViewById(R.id.layout_candidate_selection).setVisibility(8);
        } else {
            findViewById(R.id.layout_candidate_selection).setVisibility(0);
        }
        this.std = (Spinner) findViewById(R.id.spnr_student);
        this.cls = (Spinner) findViewById(R.id.spnr_class);
        this.exm = (Spinner) findViewById(R.id.spnr_semester);
        findViewById(R.id.btn_search).setVisibility(8);
        if (UserStaticData.user_type == 0) {
            findViewById(R.id.layout_candidate_selection).setVisibility(8);
//            getSpinnerData(this, URLs.exam_codes, this.exm, new C12381());
            return;
        }
//        getSpinnerData(this, URLs.exam_codes, this.exm, new C12402());
        findViewById(R.id.layout_candidate_selection).setVisibility(0);
//        getSpinnerData(this, URLs.class_codes, this.cls, new C12423());
    }

    public void processJSONResult(JSONObject jsonObject) {
        try {
            JSONObject jsonObject1;
            ArrayList<Marks> values_marks = new ArrayList();
            values_marks.clear();
            this.horizontalTable.removeAllViews();
            JSONArray jsonArray = jsonObject.getJSONArray("Response");
            if (jsonArray.length() > 0) {
                this.barChart.setVisibility(0);
                this.horizontalTable.addView(this.header);
                findViewById(R.id.error).setVisibility(8);
                this.entries.clear();
                this.subjects.clear();
                for (int i = 0; i < jsonArray.length(); i++) {
                    jsonObject1 = jsonArray.getJSONObject(i);
                    Marks listData = new Marks();
                    if (!jsonObject1.isNull("mark_id")) {
                        listData.setMarks_id(jsonObject1.getString("mark_id"));
                    }
                    if (!jsonObject1.isNull("studentName")) {
                        listData.setStudent_name(jsonObject1.getString("studentName"));
                    }
                    if (!jsonObject1.isNull("subjectName")) {
                        listData.setSubject_name(jsonObject1.getString("subjectName"));
                        this.subjects.add(jsonObject1.getString("subjectName").substring(0, 2).toUpperCase());
                    }
                    if (!jsonObject1.isNull("className")) {
                        listData.setClass_name(jsonObject1.getString("className"));
                    }
                    if (!jsonObject1.isNull("examName")) {
                        listData.setExam_name(jsonObject1.getString("examName"));
                    }
                    if (!jsonObject1.isNull("mark_obtained")) {
                        listData.setObtained(jsonObject1.getString("mark_obtained"));
                        this.marks.add(jsonObject1.getString("mark_obtained"));
                        this.entries.add(new BarEntry((float) Integer.parseInt(jsonObject1.getString("mark_obtained")), i));
                    }
                    if (!jsonObject1.isNull("mark_total")) {
                        listData.setTotal(jsonObject1.getString("mark_total"));
                    }
                    if (!jsonObject1.isNull("cgpa")) {
                        listData.setCgpa(jsonObject1.getString("cgpa"));
                    }
                    if (!jsonObject1.isNull("result")) {
                        listData.setResult(jsonObject1.getString("result"));
                    }
                    values_marks.add(listData);
                    View view = getLayoutInflater().inflate(R.layout.layout_marks_english, null);
                    ((TextView) view.findViewById(R.id.subject)).setText(listData.getSubject_name());
                    ((TextView) view.findViewById(R.id.obtained)).setText(listData.getObtained());
                    ((TextView) view.findViewById(R.id.result)).setText(listData.getResult());
                    ((TextView) view.findViewById(R.id.grade)).setText(listData.getCgpa());
                    this.horizontalTable.addView(view);
                }
                AnimationSlideUtil.fadeIn(this, this.horizontalTable);
                this.barChart.setVisibility(0);
                this.bardataset = new BarDataSet(this.entries, "Subjects");
                this.barChart.setData(new BarData((IBarDataSet) this.subjects, this.bardataset));
//                this.barChart.setDescription("");
                this.barChart.getAxisLeft().setDrawGridLines(false);
                this.barChart.getXAxis().setDrawGridLines(false);
                this.bardataset.setColors(ColorTemplate.COLORFUL_COLORS);
                this.barChart.animateY(3000);
            } else {
                findViewById(R.id.error).setVisibility(0);
                this.barChart.setVisibility(8);
            }
            JSONArray jsonArray1 = jsonObject.getJSONArray("ToatalMarks");
            if (jsonArray.length() > 0) {
                this.horizontalTable.addView(this.footer);
                jsonObject1 = jsonArray1.getJSONObject(0);
                if (!jsonObject1.isNull("ObtainedMarks")) {
                    ((TextView) this.footer.findViewById(R.id.obtained_marks)).setText(jsonObject1.getString("ObtainedMarks"));
                }
                if (!jsonObject1.isNull("TotalMarks")) {
                    ((TextView) this.footer.findViewById(R.id.total_marks)).setText(jsonObject1.getString("TotalMarks"));
                }
                if (!jsonObject1.isNull("fail")) {
                    if (Integer.parseInt(jsonObject1.getString("fail")) > 1) {
                        ((TextView) this.footer.findViewById(R.id.total_result)).setText("FAIL");
                        ((TextView) this.footer.findViewById(R.id.total_result)).setTextColor(-65536);
                    } else {
                        ((TextView) this.footer.findViewById(R.id.total_result)).setText("PASS");
                        ((TextView) this.footer.findViewById(R.id.total_result)).setTextColor(-16711936);
                    }
                }
                float percent = (Float.parseFloat(jsonObject1.getString("ObtainedMarks")) / Float.parseFloat(jsonObject1.getString("TotalMarks"))) * 100.0f;
                ((TextView) this.footer.findViewById(R.id.total_grade)).setText("" + String.format("%.2f", new Object[]{Float.valueOf(percent)}) + "%");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
