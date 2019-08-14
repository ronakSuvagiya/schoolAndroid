package com.apps.smartschoolmanagement.activities;

import android.content.SharedPreferences;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffColorFilter;
import android.os.Bundle;
import androidx.annotation.Nullable;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.apps.smartschoolmanagement.R;
import com.apps.smartschoolmanagement.adapters.MaterialAdapter;
import com.apps.smartschoolmanagement.models.OnlineMaterial;
import com.apps.smartschoolmanagement.utils.JsonClass;
import com.apps.smartschoolmanagement.utils.ProfileInfo;
import com.apps.smartschoolmanagement.utils.URLs;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.apps.smartschoolmanagement.activities.StudentProfileActivity.stdid;

public class OnlineMaterialActivity extends JsonClass {
    Spinner classes;
    String classid = null;
    HorizontalScrollView horizontalScrollView;
    LinearLayout layout_horizontal;
    ListView listView;
    ArrayList<OnlineMaterial> materialvalues = new ArrayList();
    Spinner subject;
    String subjectid = null;
    ArrayList<OnlineMaterial> tempList = new ArrayList();
    ArrayList<View> views = new ArrayList();
    String channel, stdd,subb;
    SharedPreferences sp;
    List<String> stdname = new ArrayList<>();
    List<Integer> stdId = new ArrayList<>();
    List<String> subName = new ArrayList<>();
    List<Integer> subID = new ArrayList<>();
    int subjects;

    /* renamed from: com.apps.smartschoolmanagement.activities.OnlineMaterialActivity$1 */
//    class C12481 implements VolleyCallback {
//
//        /* renamed from: com.apps.smartschoolmanagement.activities.OnlineMaterialActivity$1$1 */
//        class C12471 implements VolleyCallback {
//            C12471() {
//            }
//
//            public void onSuccess(String result) {
//                try {
//                    OnlineMaterialActivity.this.processJSONResult(new JSONObject(result));
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        }

//        C12481() {
//        }

//        public void onSuccess(String result) {
//            if (result != null) {
//                OnlineMaterialActivity.this.subjectid = result;
//                OnlineMaterialActivity.this.params.put("subject_id", OnlineMaterialActivity.this.subjectid);
//                OnlineMaterialActivity.this.params.put("student_id", ProfileInfo.getInstance().getLoginData().get("userId"));
//                //   OnlineMaterialActivity.this.getJsonResponse(URLs.online_material, OnlineMaterialActivity.this, new C12471());
//            }
//        }
//    }

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_material);
        setTitle("Online Material");
        KProgressHUD progressHUD = KProgressHUD.create(this).setLabel("Loading");
        this.listView = (ListView) findViewById(R.id.listview);
        this.classes = (Spinner) findViewById(R.id.spnr_class);
        this.subject = (Spinner) findViewById(R.id.spnr_subject);
        sp = PreferenceManager.getDefaultSharedPreferences(OnlineMaterialActivity.this);
        channel = (sp.getString("schoolid", ""));
        getJsonResponse("http://quickedu.co.in/finstdBySchoolId?school=" + channel, getApplicationContext(), new OnlineMaterialActivity.getStdssApi());

        this.horizontalScrollView = (HorizontalScrollView) findViewById(R.id.horizontalScrollView);
        this.layout_horizontal = (LinearLayout) findViewById(R.id.layout_horizontal);
//        getSpinnerData(this, URLs.subject_codes, this.subject, new C12481());
        findViewById(R.id.btn_search).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                getJsonResponse(URLs.getOnlineMaterial + channel + "/" + stdd + "/" + subb, getApplicationContext(), new OnlineMaterialActivity.getMaterial());
            }
        });

        subject.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                subjects = subID.get(i);
                subb = String.valueOf(subjects);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        classes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                stdid = stdId.get(i);
                stdd = String.valueOf(stdid);
                subName.removeAll(subName);
                subID.removeAll(subID);
                getJsonResponse(URLs.getSubject + stdid + "&school=" + channel, OnlineMaterialActivity.this, new OnlineMaterialActivity.getSubjectApi());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }

    class getSubjectApi implements VolleyCallbackJSONArray {
        @Override
        public void onSuccess(JSONArray jsonArray) {
            for (int i = 0; i < jsonArray.length(); i++) {
                try {
                    JSONObject obj = jsonArray.getJSONObject(i);
                    subName.add(obj.getString("name"));
                    subID.add(obj.getInt("id"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(OnlineMaterialActivity.this, R.layout.spinner_dropdown_custom, subName);
            subject.setAdapter(spinnerArrayAdapter);
        }
    }

    class getStdssApi implements VolleyCallbackJSONArray {
        @Override
        public void onSuccess(JSONArray jsonArray) {
            for (int i = 0; i < jsonArray.length(); i++) {
                try {
                    JSONObject obj = jsonArray.getJSONObject(i);
                    stdname.add(obj.getString("stdName"));
                    stdId.add(obj.getInt("id"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(OnlineMaterialActivity.this, R.layout.spinner_dropdown_custom, stdname);
            classes.setAdapter(spinnerArrayAdapter);
            Log.e("stdData", jsonArray.toString());
        }
    }

    public boolean isListContainMethod(String date) {
        Iterator it = this.materialvalues.iterator();
        while (it.hasNext()) {
            OnlineMaterial str = (OnlineMaterial) it.next();
            if (str.getPosteddate().toLowerCase().split(" ")[0].equals(date)) {
                this.tempList.add(str);
                return true;
            }
        }
        return false;
    }


    class getMaterial implements VolleyCallbackJSONArray {

        @Override
        public void onSuccess(JSONArray jsonArray) {
            try {
                materialvalues.clear();
                tempList.clear();
                views.clear();
                layout_horizontal.removeAllViews();
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                    JSONObject subname = jsonObject1.getJSONObject("sub");
                    OnlineMaterial listData = new OnlineMaterial();
                    if (!subname.isNull("name")) {
                        listData.setSubjectName(subname.getString("name"));
                    }
//                if (!jsonObject1.isNull("className")) {
//                    listData.setClassName(jsonObject1.getString("className"));
//                }
//                if (!jsonObject1.isNull("file")) {
//                    listData.setFileName(jsonObject1.getString("file"));
//                }
                    if (!jsonObject1.isNull("doc_name")) {
                        listData.setFileName(jsonObject1.getString("doc_name"));
                    }
//                    if (!jsonObject1.isNull("created_on")) {
//                        listData.setPosteddate(jsonObject1.getString("created_on"));
//                        View view = LayoutInflater.from(this).inflate(R.layout.item_date, null);
//                        TextView date = (TextView) view.findViewById(R.id.date_text);
//                        date.setText(jsonObject1.getString("created_on").split(" ")[0]);
//                        if (i == 0) {
//                            tempList.add(listData);
//                            date.getBackground().setColorFilter(new PorterDuffColorFilter(-16711936, Mode.SRC_IN));
//                        }
//                        if (layout_horizontal.getChildCount() > 0) {
//                            for (int j = 0; j < layout_horizontal.getChildCount(); j++) {
//                                View child = layout_horizontal.getChildAt(j);
//                                if (child instanceof ViewGroup) {
//                                    View child1 = ((ViewGroup) child).getChildAt(0);
//                                    if (child1 instanceof TextView) {
//                                        if (((TextView) child1).getText().toString().equals(date.getText().toString())) {
//                                            tempList.add(listData);
//                                        } else {
//                                            views.add(view);
//                                        }
//                                    }
//                                }
//                            }
//                        } else {
//                            layout_horizontal.addView(view);
//                        }
//                        final TextView et = (TextView) view.findViewById(R.id.date_text);
//                        et.setOnClickListener(new OnClickListener() {
//                            public void onClick(View v) {
//                                if (OnlineMaterialActivity.this.layout_horizontal.getChildCount() > 0) {
//                                    for (int j = 0; j < OnlineMaterialActivity.this.layout_horizontal.getChildCount(); j++) {
//                                        View child = OnlineMaterialActivity.this.layout_horizontal.getChildAt(j);
//                                        if (child instanceof ViewGroup) {
//                                            View child1 = ((ViewGroup) child).getChildAt(0);
//                                            if (child1 instanceof TextView) {
//                                                TextView t = (TextView) child1;
//                                                if (t.getText().toString().equals(et.getText().toString())) {
//                                                    t.getBackground().setColorFilter(new PorterDuffColorFilter(-16711936, Mode.SRC_IN));
//                                                } else {
//                                                    t.getBackground().setColorFilter(new PorterDuffColorFilter(OnlineMaterialActivity.this.getResources().getColor(17170451), Mode.SRC_IN));
//                                                }
//                                            }
//                                        }
//                                    }
//                                }
//                                OnlineMaterialActivity.this.tempList.clear();
//                                et.getBackground().setColorFilter(new PorterDuffColorFilter(-16711936, Mode.SRC_IN));
//                                Iterator it = OnlineMaterialActivity.this.materialvalues.iterator();
//                                while (it.hasNext()) {
//                                    OnlineMaterial str = (OnlineMaterial) it.next();
//                                    if (str.getPosteddate().toLowerCase().split(" ")[0].equals(et.getText().toString())) {
//                                        OnlineMaterialActivity.this.tempList.add(str);
//                                    }
//                                }
//                                OnlineMaterialActivity.this.listView.setAdapter(new MaterialAdapter(OnlineMaterialActivity.this, R.layout.item_layout_online_material, OnlineMaterialActivity.this.tempList));
//                            }
//                        });
//                    }
                    materialvalues.add(listData);
                }
                addViews(views);
                if (jsonArray.length() < 1) {
                    findViewById(R.id.error).setVisibility(0);
                    tempList.clear();
                } else {
                    findViewById(R.id.error).setVisibility(8);
                }
                listView.setAdapter(new MaterialAdapter(OnlineMaterialActivity.this, R.layout.item_layout_online_material, materialvalues));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

        public void addViews(ArrayList<View> v) {
            Iterator it = v.iterator();
            while (it.hasNext()) {
                layout_horizontal.addView((View) it.next());
            }
        }

        private void addValues(String key, String value) {
            ArrayList tempList;
            if (ProfileInfo.getInstance().getDates().containsKey(key)) {
                tempList = (ArrayList) ProfileInfo.getInstance().getDates().get(key);
                if (tempList == null) {
                    tempList = new ArrayList();
                }
                tempList.add(value);
            } else {
                tempList = new ArrayList();
                tempList.add(value);
            }
            ProfileInfo.getInstance().getDates().put(key, tempList);
        }
    }

