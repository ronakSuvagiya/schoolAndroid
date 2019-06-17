package com.apps.smartschoolmanagement.fragments;

import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import com.apps.smartschoolmanagement.R;
import com.apps.smartschoolmanagement.utils.JsonFragment;
import com.apps.smartschoolmanagement.utils.OnClickDateListener;
import com.apps.smartschoolmanagement.utils.ProfileInfo;
import com.apps.smartschoolmanagement.utils.SpinnerHelper;
import com.apps.smartschoolmanagement.utils.URLs;

public class PostAssignment extends JsonFragment {
    SpinnerHelper classes;
    String classid = null;
    Spinner cls;
    EditText date;
    View rootView;
    Spinner subj;
    SpinnerHelper subject;
    String subjectid = null;

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

    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.rootView = inflater.inflate(R.layout.fragment_post_assignments, container, false);
        this.subj = (Spinner) this.rootView.findViewById(R.id.spnr_subject);
        this.cls = (Spinner) this.rootView.findViewById(R.id.spnr_class);
        this.date = (EditText) this.rootView.findViewById(R.id.date);
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
