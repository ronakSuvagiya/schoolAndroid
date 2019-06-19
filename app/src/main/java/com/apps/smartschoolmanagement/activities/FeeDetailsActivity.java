package com.apps.smartschoolmanagement.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.apps.smartschoolmanagement.R;
import com.apps.smartschoolmanagement.activities.AddPhotoGalleryActivity.Utils;
import com.apps.smartschoolmanagement.models.UserStaticData;
import com.apps.smartschoolmanagement.utils.CircleDisplay;
import com.apps.smartschoolmanagement.utils.JsonClass;
import com.apps.smartschoolmanagement.utils.ProfileInfo;
import com.apps.smartschoolmanagement.utils.URLs;
import java.util.HashMap;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class FeeDetailsActivity extends JsonClass {
    Spinner classes;
    String classid = null;
    TextView due;
    TextView name;
    TextView paid;
    TextView roll;
    LinearLayout root;
    Spinner student;
    String studentid = null;
    TextView total;

    /* renamed from: com.apps.smartschoolmanagement.activities.FeeDetailsActivity$1 */
    class C12071 implements VolleyCallback {
        C12071() {
        }

        public void onSuccess(String result) {
            try {
                FeeDetailsActivity.this.processJSONResult(new JSONArray(result));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    /* renamed from: com.apps.smartschoolmanagement.activities.FeeDetailsActivity$2 */
    class C12102 implements VolleyCallback {

        /* renamed from: com.apps.smartschoolmanagement.activities.FeeDetailsActivity$2$1 */
        class C12091 implements VolleyCallback {

            /* renamed from: com.apps.smartschoolmanagement.activities.FeeDetailsActivity$2$1$1 */
            class C12081 implements VolleyCallback {
                C12081() {
                }

                public void onSuccess(String result) {
                    try {
                        JSONArray jsonArray = new JSONArray(result);
                        if (jsonArray.length() > 0) {
                            FeeDetailsActivity.this.findViewById(R.id.layout_fields).setVisibility(0);
                            FeeDetailsActivity.this.processJSONResult(jsonArray);
                            return;
                        }
                        FeeDetailsActivity.this.findViewById(R.id.layout_fields).setVisibility(8);
                        FeeDetailsActivity.this.findViewById(R.id.error).setVisibility(0);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            C12091() {
            }

            public void onSuccess(String result) {
                if (result != null) {
                    FeeDetailsActivity.this.studentid = result;
                    if (FeeDetailsActivity.this.student.getSelectedItemPosition() > 0) {
                        FeeDetailsActivity.this.params.put("student_id", FeeDetailsActivity.this.studentid);
                       // FeeDetailsActivity.this.getJsonResponse(URLs.feedetails, FeeDetailsActivity.this, new C12081());
                    }
                }
            }
        }

        C12102() {
        }

        public void onSuccess(String result) {
            if (result != null) {
                FeeDetailsActivity.this.classid = result;
            }
            FeeDetailsActivity.this.getSpinnerData(FeeDetailsActivity.this, URLs.student_codes, FeeDetailsActivity.this.student, "class_id," + FeeDetailsActivity.this.classid, new C12091());
        }
    }

    /* renamed from: com.apps.smartschoolmanagement.activities.FeeDetailsActivity$3 */
    class C12113 implements OnClickListener {
        C12113() {
        }

        public void onClick(View view) {
            FeeDetailsActivity.this.startActivity(new Intent(FeeDetailsActivity.this, PreviewActivity.class));
        }
    }

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if (UserStaticData.user_type == 0) {
            setTheme(R.style.AppTheme1);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fee_details);
        setTitle("Fee Details");
        KProgressHUD progressHUD = KProgressHUD.create(this).setLabel("Loading");
        this.paid = (TextView) findViewById(R.id.paid_fee);
        this.total = (TextView) findViewById(R.id.total_fee);
        this.due = (TextView) findViewById(R.id.due_fee);
        this.name = (TextView) findViewById(R.id.name);
        this.roll = (TextView) findViewById(R.id.roll);
        this.root = (LinearLayout) findViewById(R.id.root_layout);
        this.classes = (Spinner) findViewById(R.id.spnr_class);
        this.student = (Spinner) findViewById(R.id.spnr_student);
        if (UserStaticData.user_type == 0) {
            findViewById(R.id.layout_action).setVisibility(8);
            findViewById(R.id.layout_fields).setVisibility(0);
            this.params.put("student_id", ProfileInfo.getInstance().getLoginData().get("userId"));
          //  getJsonResponse(URLs.feedetails, this, new C12071());
        } else {
            findViewById(R.id.layout_pay).setVisibility(8);
//            getSpinnerData(this, URLs.class_codes, this.classes, new C12102());
            findViewById(R.id.btn_search).setVisibility(8);
        }
        CircleDisplay mCircleDisplay1 = (CircleDisplay) findViewById(R.id.circleDisplay1);
        mCircleDisplay1.setAnimDuration(0);
        mCircleDisplay1.setValueWidthPercent(15.0f);
        mCircleDisplay1.setDimAlpha(100);
        mCircleDisplay1.setColorinner(Color.parseColor("#1D89E4"));
        mCircleDisplay1.setColorArc(Color.parseColor("#1976D3"));
        mCircleDisplay1.showValue(100.0f, 100.0f, true);
        findViewById(R.id.btn_pay).setOnClickListener(new C12113());
    }

    public void processJSONResult(JSONArray jsonObject) {
        if (jsonObject.length() > 0) {
            findViewById(R.id.error).setVisibility(8);
            try {
                this.responseParams.clear();
                JSONObject jsonObject1 = jsonObject.getJSONObject(0);
                this.responseParams.put("total_amount", jsonObject1.getString("total_amount"));
                this.responseParams.put("pending_amount", jsonObject1.getString("pending_amount"));
                this.responseParams.put("paid_amount", jsonObject1.getString("paid_amount"));
                this.responseParams.put("roll", jsonObject1.getString("roll"));
                this.responseParams.put(Utils.imageName, jsonObject1.getString(Utils.imageName));
                loadData(this.responseParams);
                return;
            } catch (JSONException e1) {
                e1.printStackTrace();
                return;
            }
        }
        findViewById(R.id.error).setVisibility(0);
    }

    public void loadData(HashMap<String, String> returnData) {
        if (returnData != null) {
            this.paid.setText(getString(R.string.Rs) + " " + ((String) returnData.get("paid_amount")));
            this.due.setText(getString(R.string.Rs) + " " + ((String) returnData.get("pending_amount")));
            this.total.setText(getString(R.string.Rs) + " " + ((String) returnData.get("total_amount")));
            this.name.setText((CharSequence) returnData.get(Utils.imageName));
            this.roll.setText((CharSequence) returnData.get("roll"));
            return;
        }
        Toast.makeText(this, "Error loading Data from Server", 0).show();
    }
}
