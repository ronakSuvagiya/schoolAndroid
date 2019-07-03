package com.apps.smartschoolmanagement.fragments;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

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
    static Integer stdid ;
    List<Integer> stdId = new ArrayList<>();
    List<String> stdname = new ArrayList<>();
    List<Integer> divId = new ArrayList<>();
    List<String> divName = new ArrayList<>();
    /* renamed from: com.apps.smartschoolmanagement.fragments.AssignmentsHistory$1 */
    class C13441 implements VolleyCallback {

        /* renamed from: com.apps.smartschoolmanagement.fragments.AssignmentsHistory$1$1 */
        class C13431 implements OnItemSelectedListener {
            C13431() {
            }

            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i > 0) {
                    AssignmentsHistory.this.params.put("month", "" + AssignmentsHistory.this.div.getSelectedItemPosition());
                    if (AssignmentsHistory.this.classid != null) {
                        AssignmentsHistory.this.loadData();
                    } else {
                        Toast.makeText(AssignmentsHistory.this.getActivity(), "Select Class", 0).show();
                    }
                }
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        }

        C13441() {
        }

        public void onSuccess(String result) {
            if (result != null) {
                AssignmentsHistory.this.classid = result;
                AssignmentsHistory.this.params.put("class_id", AssignmentsHistory.this.classid);
            }
            AssignmentsHistory.this.div.setOnItemSelectedListener(new C13431());
        }
    }

    /* renamed from: com.apps.smartschoolmanagement.fragments.AssignmentsHistory$2 */
    class C13452 implements VolleyCallback {
        C13452() {
        }

        public void onSuccess(String result) {
            try {
                AssignmentsHistory.this.processJSONResult(new JSONObject(result));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

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
    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.rootView = inflater.inflate(R.layout.listview, container, false);
        this.listView = (ListView) this.rootView.findViewById(R.id.listview);
        this.rootView.findViewById(R.id.layout_spnr_month).setVisibility(0);
        this.rootView.findViewById(R.id.layout_spnr_class).setVisibility(0);
        this.div = (Spinner) this.rootView.findViewById(R.id.spnr_month);
        this.std = (Spinner) this.rootView.findViewById(R.id.spnr_class);
        this.date = (EditText) this.rootView.findViewById(R.id.date);
        getJsonResponse(URLs.getAssignment + stdid , rootView, new getStdApi());
        std.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                stdid = stdId.get(i);
                getJsonResponse(URLs.getAssignment + stdid , rootView, new getStdApi());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        div.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                divid = divId.get(i);
                getJsonResponse(URLs.getAssignment + stdid + "&div=" + divId, rootView, new getDivsApi());

            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
//        this.date.setOnClickListener(new OnClickDateListener(this.date, getActivity(), "past"));

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

    public void processJSONResult(JSONObject jsonObject) {
        try {
            this.values.clear();
            JSONArray jsonArray = jsonObject.getJSONArray("Response");
            if ("success".equals(jsonObject.getString(NotificationCompat.CATEGORY_STATUS))) {
                this.rootView.findViewById(R.id.error).setVisibility(8);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                    ListData listData = new ListData();
                    if (!jsonObject1.isNull("submit_date")) {
                        listData.setAssignment_submit_date(jsonObject1.getString("submit_date"));
                    }
                    if (!jsonObject1.isNull("id")) {
                        listData.setAssignment_id(jsonObject1.getString("id"));
                    }
                    if (!jsonObject1.isNull("Assignment")) {
                        listData.setAssignment_assignment(jsonObject1.getString("Assignment"));
                    }
                    if (!jsonObject1.isNull(NotificationCompat.CATEGORY_STATUS)) {
                        listData.setStatus(jsonObject1.getString(NotificationCompat.CATEGORY_STATUS));
                    }
                    if (!jsonObject1.isNull("teacher_id")) {
                        listData.setAssignment_teacher(jsonObject1.getString("teacher_id"));
                    }
                    if (!jsonObject1.isNull("subjectName")) {
                        listData.setAssignment_subject(jsonObject1.getString("subjectName"));
                    }
                    this.values.add(listData);
                }
            } else {
                this.rootView.findViewById(R.id.error).setVisibility(0);
            }
            this.listView.setAdapter(new TeacherAssignmentsAdapter(getActivity(), R.layout.item_layout_assignments, this.values));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
