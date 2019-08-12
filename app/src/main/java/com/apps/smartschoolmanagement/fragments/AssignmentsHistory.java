package com.apps.smartschoolmanagement.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.apps.smartschoolmanagement.R;
import com.apps.smartschoolmanagement.adapters.TeacherAssignmentsAdapter;
import com.apps.smartschoolmanagement.models.ListData;
import com.apps.smartschoolmanagement.utils.JsonFragment;
import com.apps.smartschoolmanagement.utils.OnClickDateListener;
import com.apps.smartschoolmanagement.utils.ProfileInfo;
import com.apps.smartschoolmanagement.utils.URLs;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.apps.smartschoolmanagement.fragments.StudentAttendance.stdid;

public class AssignmentsHistory extends JsonFragment {
    Spinner std;
    EditText date;
    String classid = null;
    ListView listView;
    Spinner div;
    View rootView;
    int divid;
    SharedPreferences sp;
    String channel;
    static Integer stdid;
    List<Integer> stdId = new ArrayList<>();
    List<String> stdname = new ArrayList<>();
    List<Integer> divId = new ArrayList<>();
    List<String> divName = new ArrayList<>();
    List<String> submissiondate = new ArrayList<>();
    List<String> techerid = new ArrayList<>();
    List<String> ass_description = new ArrayList<>();
    List<String> suubject = new ArrayList<>();

    ArrayList<ListData> values = new ArrayList();

    String dates, teachermaster, description, subject;

    class getStdApi implements JsonFragment.VolleyCallbackJSONArray {
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
            final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_dropdown_custom, stdname);
            std.setAdapter(spinnerArrayAdapter);
            Log.e("stdData", jsonArray.toString());
        }
    }

    class getDivsApi implements JsonFragment.VolleyCallbackJSONArray {
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
            final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_dropdown_custom, divName);
            div.setAdapter(spinnerArrayAdapter);
            Log.e("divData", jsonArray.toString());
        }
    }

    class getAssi implements VolleyCallbackJSONArray {
        @Override
        public void onSuccess(JSONArray jsonArray) {
            Log.e("respo", jsonArray.toString());
            values.clear();
            for (int i = 0; i< jsonArray.length(); i++) {
                try {
                    Log.e("all",">>"+jsonArray.length()) ;
                    rootView.findViewById(R.id.error).setVisibility(8);
                    ListData listData = new ListData();
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                    listData.setAssignment_submit_date(jsonObject1.getString("date"));
                    JSONObject jsonObject = jsonObject1.getJSONObject("teacherMaster");
                    listData.setAssignment_teacher(String.valueOf(jsonObject.getInt("id")));
                    listData.setAssignment_assignment(jsonObject1.getString("description"));
                    JSONObject jsonObject2 = jsonObject1.getJSONObject("subject");
                    listData.setAssignment_subject(jsonObject2.getString("name"));
                    values.add(listData);
                } catch (JSONException e) {
                    e.printStackTrace();
                    rootView.findViewById(R.id.error).setVisibility(0);
                }

            }
            listView.setAdapter(new TeacherAssignmentsAdapter(getActivity(), R.layout.item_layout_assignments, values));
        }
    }

    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.rootView = inflater.inflate(R.layout.listview, container, false);
        this.listView = (ListView) this.rootView.findViewById(R.id.listview);
        this.rootView.findViewById(R.id.layout_spnr_month).setVisibility(0);
        this.rootView.findViewById(R.id.layout_spnr_class).setVisibility(0);
        this.rootView.findViewById(R.id.date_layout).setVisibility(0);
        this.div = (Spinner) this.rootView.findViewById(R.id.spnr_month);
        this.std = (Spinner) this.rootView.findViewById(R.id.spnr_class);
        this.date = (EditText) this.rootView.findViewById(R.id.date);

        sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
        channel = (sp.getString("schoolid", ""));
        getJsonResponse(URLs.getStd + channel, rootView, new getStdApi());
        std.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                divName.removeAll(divName);
                divId.removeAll(divId);
                stdid = stdId.get(i);
                getJsonResponse(URLs.getDiv + stdid , rootView, new getDivsApi());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        div.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                divid = divId.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        this.date.setOnClickListener(new OnClickDateListener(
                this.date, getActivity(), "All"));


        this.date.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                Log.e("test", editable.toString() + stdid + "" + divid);
                getJsonResponse(URLs.getAssignment + stdid + "&div=" + divid + "&date=" + editable.toString(), rootView, new getAssi());
            }
        });
        return this.rootView;
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        getSpinnerData(getActivity(), this.rootView, URLs.class_codes, this.classes, new C13441());
    }

    public void loadData() {
        this.params.put("teacher_id", ProfileInfo.getInstance().getLoginData().get("userId"));
        // getJsonResponse(URLs.assignmentsListTeacher, this.rootView, new C13452());
    }

//    public void processJSONResult(JSONObject jsonObject) {
//        try {
//            this.values.clear();
//            JSONArray jsonArray = jsonObject.getJSONArray("Response");
//            if ("success".equals(jsonObject.getString(NotificationCompat.CATEGORY_STATUS))) {
//                this.rootView.findViewById(R.id.error).setVisibility(8);
//                for (int i = 0; i < jsonArray.length(); i++) {
//                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
//                    ListData listData = new ListData();
//                    if (!jsonObject1.isNull("submit_date")) {
//                        listData.setAssignment_submit_date(jsonObject1.getString("submit_date"));
//                    }
//                    if (!jsonObject1.isNull("id")) {
//                        listData.setAssignment_id(jsonObject1.getString("id"));
//                    }
//                    if (!jsonObject1.isNull("Assignment")) {
//                        listData.setAssignment_assignment(jsonObject1.getString("Assignment"));
//                    }
//                    if (!jsonObject1.isNull(NotificationCompat.CATEGORY_STATUS)) {
//                        listData.setStatus(jsonObject1.getString(NotificationCompat.CATEGORY_STATUS));
//                    }
//                    if (!jsonObject1.isNull("teacher_id")) {
//                        listData.setAssignment_teacher(jsonObject1.getString("teacher_id"));
//                    }
//                    if (!jsonObject1.isNull("subjectName")) {
//                        listData.setAssignment_subject(jsonObject1.getString("subjectName"));
//                    }
//                    this.values.add(listData);
//                }
//            } else {
//                this.rootView.findViewById(R.id.error).setVisibility(0);
//            }
//            this.listView.setAdapter(new TeacherAssignmentsAdapter(getActivity(), R.layout.item_layout_assignments, this.values));
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//    }
}
