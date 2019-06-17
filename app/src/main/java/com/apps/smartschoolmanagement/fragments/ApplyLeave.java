package com.apps.smartschoolmanagement.fragments;

import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;
import com.apps.smartschoolmanagement.R;
import com.apps.smartschoolmanagement.models.UserStaticData;
import com.apps.smartschoolmanagement.utils.JsonFragment;
import com.apps.smartschoolmanagement.utils.KeyboardUtil;
import com.apps.smartschoolmanagement.utils.OnClickDateListener;
import com.apps.smartschoolmanagement.utils.ProfileInfo;
import com.apps.smartschoolmanagement.utils.URLs;

public class ApplyLeave extends JsonFragment {
    EditText date;
    View view;

    /* renamed from: com.apps.smartschoolmanagement.fragments.ApplyLeave$1 */
    class C13401 implements OnClickListener {

        /* renamed from: com.apps.smartschoolmanagement.fragments.ApplyLeave$1$1 */
        class C13391 implements VolleyCallback {
            C13391() {
            }

            public void onSuccess(String result) {
                Toast.makeText(ApplyLeave.this.getActivity(), "Leave Apply Success", 1).show();
                KeyboardUtil.hideSoftKeyboard(ApplyLeave.this.getActivity());
            }
        }

        C13401() {
        }

        public void onClick(View v) {
            if (ApplyLeave.this.date.getText().toString().length() <= 5 || ((EditText) ApplyLeave.this.view.findViewById(R.id.edit_leave_reason)).getText().toString().equalsIgnoreCase("")) {
                ApplyLeave.this.view.findViewById(R.id.error).setVisibility(0);
                return;
            }
            if (UserStaticData.user_type == 1) {
                ApplyLeave.this.params.put("teacher_id", ProfileInfo.getInstance().getLoginData().get("userId"));
            }
            if (UserStaticData.user_type == 0) {
                ApplyLeave.this.params.put("student_id", ProfileInfo.getInstance().getLoginData().get("userId"));
            }
            ApplyLeave.this.params.put("create_timestamp", ApplyLeave.this.date.getText().toString());
            ApplyLeave.this.params.put("leaverequest", ((EditText) ApplyLeave.this.view.findViewById(R.id.edit_leave_reason)).getText().toString());
            ApplyLeave.this.params.put("leavedays", ((EditText) ApplyLeave.this.view.findViewById(R.id.days)).getText().toString());
//            ApplyLeave.this.getJsonResponse(URLs.applyleave, ApplyLeave.this.view, new C13391());
        }
    }

    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.layout_apply_leave, container, false);
        this.view.findViewById(R.id.layout_teacher).setVisibility(8);
        this.date = (EditText) this.view.findViewById(R.id.date);
        this.date.setOnClickListener(new OnClickDateListener(this.date, getActivity(), "past"));
        this.view.findViewById(R.id.btn_apply).setBackgroundResource(R.drawable.student_button);
        this.view.findViewById(R.id.btn_apply).setOnClickListener(new C13401());
        return this.view;
    }
}
