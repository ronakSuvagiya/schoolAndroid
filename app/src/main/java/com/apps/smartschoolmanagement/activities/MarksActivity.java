package com.apps.smartschoolmanagement.activities;

import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.apps.smartschoolmanagement.R;
import com.apps.smartschoolmanagement.models.UserStaticData;
import com.apps.smartschoolmanagement.utils.JsonClass;
import com.apps.smartschoolmanagement.utils.URLs;
import java.util.HashMap;
import java.util.Iterator;
import org.json.JSONArray;
import org.json.JSONException;

public class MarksActivity extends JsonClass {
    String classid = null;
    Spinner cls;
    String examid = null;
    Spinner exm;
    HorizontalScrollView root;
    Spinner std;
    String studentid = null;

    /* renamed from: com.apps.smartschoolmanagement.activities.MarksActivity$1 */
    class C12321 implements VolleyCallback {

        /* renamed from: com.apps.smartschoolmanagement.activities.MarksActivity$1$1 */
        class C12311 implements VolleyCallback {
            C12311() {
            }

            public void onSuccess(String result) {
                MarksActivity.this.processJsonResponse(result);
            }
        }

        C12321() {
        }

        public void onSuccess(String result) {
            if (result != null) {
                MarksActivity.this.examid = result;
                MarksActivity.this.params.put("exam_id", MarksActivity.this.examid);
             //   MarksActivity.this.getJsonResponse(URLs.marks, MarksActivity.this, new C12311());
            }
        }
    }

    /* renamed from: com.apps.smartschoolmanagement.activities.MarksActivity$2 */
    class C12342 implements VolleyCallback {

        /* renamed from: com.apps.smartschoolmanagement.activities.MarksActivity$2$1 */
        class C12331 implements VolleyCallback {
            C12331() {
            }

            public void onSuccess(String result) {
                if (result != null) {
                    MarksActivity.this.studentid = result;
                }
            }
        }

        C12342() {
        }

        public void onSuccess(String result) {
            if (result != null) {
                MarksActivity.this.classid = result;
            }
            MarksActivity.this.getSpinnerData(MarksActivity.this, URLs.student_codes, MarksActivity.this.std, "class_id," + MarksActivity.this.classid, new C12331());
        }
    }

    /* renamed from: com.apps.smartschoolmanagement.activities.MarksActivity$3 */
    class C12363 implements OnClickListener {

        /* renamed from: com.apps.smartschoolmanagement.activities.MarksActivity$3$1 */
        class C12351 implements VolleyCallback {
            C12351() {
            }

            public void onSuccess(String result) {
                MarksActivity.this.processJsonResponse(result);
            }
        }

        C12363() {
        }

        public void onClick(View view) {
            MarksActivity.this.params.put("student_id", MarksActivity.this.studentid);
            MarksActivity.this.params.put("exam_id", MarksActivity.this.examid);
        //    MarksActivity.this.getJsonResponse(URLs.marks, MarksActivity.this, new C12351());
        }
    }

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marks);
        setTitle("Marks List");
        KProgressHUD progressHUD = KProgressHUD.create(this).setLabel("Loading");
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
//        getSpinnerData(this, URLs.exam_codes, this.exm, new C12321());
        findViewById(R.id.btn_search).setVisibility(8);
        if (UserStaticData.user_type != 0) {
            findViewById(R.id.btn_search).setVisibility(0);
//            getSpinnerData(this, URLs.class_codes, this.cls, new C12342());
            findViewById(R.id.btn_search).setOnClickListener(new C12363());
        }
    }

    public void processJsonResponse(String response) {
        if (response == null || response.equalsIgnoreCase("")) {
            Toast.makeText(this, "No Record Found", 0).show();
            return;
        }
        this.responseParams.clear();
        try {
            JSONArray jsonArray = new JSONArray(response);
            Iterator<String> keysIterator = jsonArray.getJSONObject(0).keys();
            while (keysIterator.hasNext()) {
                String keyStr = (String) keysIterator.next();
                this.responseParams.put(keyStr, jsonArray.getJSONObject(0).getString(keyStr));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        this.root.setVisibility(0);
        loadData(this.root, this.responseParams);
    }

    public void loadData(ViewGroup parent, HashMap<String, String> returnData) {
        if (returnData != null) {
            for (int i = 0; i < parent.getChildCount(); i++) {
                View child = parent.getChildAt(i);
                if (child instanceof ViewGroup) {
                    loadData((ViewGroup) child, returnData);
                } else if (parent.getChildAt(i) instanceof TextView) {
                    TextView et = (TextView) parent.getChildAt(i);
                    if (et.getId() > 0 && returnData.containsKey(getResources().getResourceEntryName(et.getId()))) {
                        et.setText((CharSequence) returnData.get(getResources().getResourceEntryName(et.getId())));
                    }
                }
            }
            return;
        }
        Toast.makeText(this, "No data Saved Previously", 0).show();
    }
}
