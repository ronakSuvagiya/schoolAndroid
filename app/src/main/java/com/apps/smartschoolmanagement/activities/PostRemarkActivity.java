package com.apps.smartschoolmanagement.activities;

import android.os.Bundle;
import androidx.annotation.IdRes;
import androidx.annotation.Nullable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Spinner;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.apps.smartschoolmanagement.R;
import com.apps.smartschoolmanagement.utils.JsonClass;
import com.apps.smartschoolmanagement.utils.ProfileInfo;
import com.apps.smartschoolmanagement.utils.SpinnerHelper;
import com.apps.smartschoolmanagement.utils.URLs;

public class PostRemarkActivity extends JsonClass {
    SpinnerHelper classes;
    String classid = null;
    Spinner cls;
    Spinner std;
    SpinnerHelper student;
    String studentid = null;
    RadioGroup type;

    /* renamed from: com.apps.smartschoolmanagement.activities.PostRemarkActivity$1 */
    class C12611 implements VolleyCallback {

        /* renamed from: com.apps.smartschoolmanagement.activities.PostRemarkActivity$1$1 */
        class C12601 implements VolleyCallback {
            C12601() {
            }

            public void onSuccess(String result) {
                if (result != null) {
                    PostRemarkActivity.this.studentid = result;
                }
            }
        }

        C12611() {
        }

        public void onSuccess(String result) {
            if (result != null) {
                PostRemarkActivity.this.classid = result;
            }
            PostRemarkActivity.this.getSpinnerData(PostRemarkActivity.this, URLs.student_codes, PostRemarkActivity.this.std, "class_id," + PostRemarkActivity.this.classid, new C12601());
        }
    }

    /* renamed from: com.apps.smartschoolmanagement.activities.PostRemarkActivity$2 */
    class C12622 implements OnCheckedChangeListener {
        C12622() {
        }

        public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
            if (radioGroup.getCheckedRadioButtonId() == R.id.positive) {
                PostRemarkActivity.this.params.put("type", "positive");
            } else {
                PostRemarkActivity.this.params.put("type", "negative");
            }
        }
    }

    /* renamed from: com.apps.smartschoolmanagement.activities.PostRemarkActivity$3 */
    class C12643 implements OnClickListener {

        /* renamed from: com.apps.smartschoolmanagement.activities.PostRemarkActivity$3$1 */
        class C12631 implements VolleyCallback {
            C12631() {
            }

            public void onSuccess(String result) {
                PostRemarkActivity.this.finish();
            }
        }

        C12643() {
        }

        public void onClick(View view) {
            if (PostRemarkActivity.this.std.getSelectedItemPosition() <= 0 || PostRemarkActivity.this.cls.getSelectedItemPosition() <= 0) {
                PostRemarkActivity.this.findViewById(R.id.error).setVisibility(0);
                return;
            }
            PostRemarkActivity.this.findViewById(R.id.error).setVisibility(8);
            PostRemarkActivity.this.params.put("student_id", PostRemarkActivity.this.studentid);
            PostRemarkActivity.this.params.put("teacher_id", ProfileInfo.getInstance().getLoginData().get("userId"));
            PostRemarkActivity.this.params.put("remark", ((EditText) PostRemarkActivity.this.findViewById(R.id.edit_post_remard)).getText().toString());
           // PostRemarkActivity.this.getJsonResponse(URLs.remarks_post, PostRemarkActivity.this, new C12631());
        }
    }

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_remarks);
        setTitle("Post Remark");
        KProgressHUD progressHUD = KProgressHUD.create(this);
        this.std = (Spinner) findViewById(R.id.spnr_student);
        this.cls = (Spinner) findViewById(R.id.spnr_class);
//        getSpinnerData(this, URLs.class_codes, this.cls, new C12611());
        this.type = (RadioGroup) findViewById(R.id.remark_type);
        this.type.setOnCheckedChangeListener(new C12622());
        findViewById(R.id.btn_post).setOnClickListener(new C12643());
    }
}
