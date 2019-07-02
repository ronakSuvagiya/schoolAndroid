package com.apps.smartschoolmanagement.fragments;

import android.content.SharedPreferences;
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
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import com.apps.smartschoolmanagement.R;
import com.apps.smartschoolmanagement.activities.StudentProfileActivity;
import com.apps.smartschoolmanagement.utils.JsonClass;
import com.apps.smartschoolmanagement.utils.JsonFragment;
import com.apps.smartschoolmanagement.utils.OnClickDateListener;
import com.apps.smartschoolmanagement.utils.ProfileInfo;
import com.apps.smartschoolmanagement.utils.SpinnerHelper;
import com.apps.smartschoolmanagement.utils.URLs;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.apps.smartschoolmanagement.fragments.StudentAttendance.stdid;

public class PostAssignment extends JsonFragment {
    SpinnerHelper classes;
    String classid = null;
    Spinner cls;
    EditText date;
    View rootView;
    Spinner subj;
    Spinner std;
    SpinnerHelper subject;
    String subjectid = null;
    SharedPreferences sp;

    List<String> stdname = new ArrayList<>();
    List<Integer> stdId = new ArrayList<>();
    List<String> divName = new ArrayList<>();
    List<Integer> divId = new ArrayList<>();
    List<String> StudentName = new ArrayList<>();
    List<Integer> StudentRollNo  = new ArrayList<>();
    /* renamed from: com.apps.smartschoolmanagement.fragments.PostAssignment$1 */
    class C13561 implements VolleyCallback {
        C13561() {
        }

        public void onSuccess(String result) {
            if (result != null) {
                PostAssignment.this.classid = result;
                PostAssignment.this.params.put("class", PostAssignment.this.classid);
            }
        }
    }

    /* renamed from: com.apps.smartschoolmanagement.fragments.PostAssignment$2 */
    class C13572 implements VolleyCallback {
        C13572() {
        }

        public void onSuccess(String result) {
            if (result != null) {
                PostAssignment.this.subjectid = result;
                PostAssignment.this.params.put("subject", PostAssignment.this.subjectid);
            }
        }
    }

    /* renamed from: com.apps.smartschoolmanagement.fragments.PostAssignment$3 */
    class C13593 implements OnClickListener {

        /* renamed from: com.apps.smartschoolmanagement.fragments.PostAssignment$3$1 */
        class C13581 implements VolleyCallback {
            C13581() {
            }

            public void onSuccess(String result) {
                Toast.makeText(PostAssignment.this.getActivity(), "Assignment Posted", 1).show();
            }
        }

        C13593() {
        }

        public void onClick(View view) {
            if (PostAssignment.this.date.getText().toString().length() <= 5 || ((EditText) PostAssignment.this.rootView.findViewById(R.id.edit_post_assignment)).getText().toString().equalsIgnoreCase("")) {
                PostAssignment.this.rootView.findViewById(R.id.error).setVisibility(0);
                return;
            }
            PostAssignment.this.rootView.findViewById(R.id.error).setVisibility(8);
            PostAssignment.this.params.put("teacher_id", ProfileInfo.getInstance().getLoginData().get("userId"));
            PostAssignment.this.params.put("date", PostAssignment.this.date.getText().toString());
            PostAssignment.this.params.put("assignment", ((EditText) PostAssignment.this.rootView.findViewById(R.id.edit_post_assignment)).getText().toString());
           // PostAssignment.this.getJsonResponse(URLs.assignment_post, PostAssignment.this.rootView, new C13581());
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
    class getDivApi implements JsonFragment.VolleyCallbackJSONArray {
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
            cls.setAdapter(spinnerArrayAdapter);
            Log.e("divData", jsonArray.toString());
        }
    }

    class getSubjectApi implements JsonFragment.VolleyCallbackJSONArray {
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
            cls.setAdapter(spinnerArrayAdapter);
            Log.e("divData", jsonArray.toString());
        }
    }

    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.rootView = inflater.inflate(R.layout.fragment_post_assignments, container, false);
        this.std = (Spinner) this.rootView.findViewById(R.id.spnr_std);
        this.subj = (Spinner) this.rootView.findViewById(R.id.spnr_subject);
        this.cls = (Spinner) this.rootView.findViewById(R.id.spnr_class);
        this.date = (EditText) this.rootView.findViewById(R.id.date);
        sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String channel = (sp.getString("schoolid", ""));
        getJsonResponse(URLs.getStd + channel,rootView, new getStdApi());
        std.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                divName.removeAll(divName);
                divId.removeAll(divId);
                stdid = stdId.get(i);
                getJsonResponse(URLs.getDiv + stdid + "&school=" + channel, rootView, new getDivApi());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        cls.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                StudentName.removeAll(StudentName);
                StudentRollNo.removeAll(StudentRollNo);
                Integer divid = divId.get(i);
                getJsonResponse(URLs.getStudentByStdAndDiv + stdid + "&div=" + divid + "&School=" + channel, rootView, new getSubjectApi());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        subj.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                StudentName.removeAll(StudentName);
                StudentRollNo.removeAll(StudentRollNo);
                Integer divid = divId.get(i);
                getJsonResponse(URLs.getSubject + stdid + "&div=" + divid + "&School=" + channel, rootView, new getSubjectApi());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        this.date.setOnClickListener(new OnClickDateListener(this.date, getActivity(), "past"));
        return this.rootView;
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        getSpinnerData(getActivity(), this.rootView, URLs.class_codes, this.cls, new C13561());
//        getSpinnerData(getActivity(), this.rootView, URLs.subject_codes, this.subj, new C13572());
        this.rootView.findViewById(R.id.btn_post).setOnClickListener(new C13593());
    }
}
