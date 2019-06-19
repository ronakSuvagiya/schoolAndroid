package com.apps.smartschoolmanagement.activities;

import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.apps.smartschoolmanagement.R;
import com.apps.smartschoolmanagement.models.UserStaticData;
import com.apps.smartschoolmanagement.utils.JsonClass;
import com.apps.smartschoolmanagement.utils.OnClickDateListener;
import com.apps.smartschoolmanagement.utils.ProfileInfo;
import com.apps.smartschoolmanagement.utils.URLs;

public class LeaveApplyActivity extends JsonClass {
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_apply_leave);
        setTitle("Apply Leave");
        KProgressHUD progressHUD = KProgressHUD.create(this);
        final EditText date = (EditText) findViewById(R.id.date);
        date.setOnClickListener(new OnClickDateListener(date, this, "past"));
        findViewById(R.id.btn_apply).setOnClickListener(new OnClickListener() {

            /* renamed from: com.apps.smartschoolmanagement.activities.LeaveApplyActivity$1$1 */
            class C12191 implements VolleyCallback {
                C12191() {
                }

                public void onSuccess(String result) {
                    Toast.makeText(LeaveApplyActivity.this, "Leave Applied", 0).show();
                    LeaveApplyActivity.this.finish();
                }
            }

            public void onClick(View view) {
                if (date.getText().toString().length() <= 5 || ((EditText) LeaveApplyActivity.this.findViewById(R.id.edit_leave_reason)).getText().toString().equalsIgnoreCase("")) {
                    LeaveApplyActivity.this.findViewById(R.id.error).setVisibility(0);
                    return;
                }
                LeaveApplyActivity.this.findViewById(R.id.error).setVisibility(8);
                LeaveApplyActivity.this.params.put("create_timestamp", date.getText().toString());
                LeaveApplyActivity.this.params.put("days", ((EditText) LeaveApplyActivity.this.findViewById(R.id.days)).getText().toString());
                LeaveApplyActivity.this.params.put("leaverequest", ((EditText) LeaveApplyActivity.this.findViewById(R.id.edit_leave_reason)).getText().toString());
                if (UserStaticData.user_type == 1) {
                    LeaveApplyActivity.this.params.put("teacher_id", ProfileInfo.getInstance().getLoginData().get("userId"));
                } else if (UserStaticData.user_type == 0) {
                    LeaveApplyActivity.this.params.put("student_id", ProfileInfo.getInstance().getLoginData().get("userId"));
                }
           //     LeaveApplyActivity.this.getJsonResponse(URLs.applyleave, LeaveApplyActivity.this, new C12191());
            }
        });
    }
}
