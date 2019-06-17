package com.apps.smartschoolmanagement.activities;

import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.apps.smartschoolmanagement.R;
import com.apps.smartschoolmanagement.utils.JsonClass;
import com.apps.smartschoolmanagement.utils.URLs;
import com.apps.smartschoolmanagement.utils.WordUtils;
import java.util.HashMap;
import java.util.Iterator;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class StaffPayRollActivity extends JsonClass {
    Spinner dlg;
    TableLayout root;
    String teacherid = null;

    /* renamed from: com.apps.smartschoolmanagement.activities.StaffPayRollActivity$1 */
    class C12951 implements VolleyCallback {

        /* renamed from: com.apps.smartschoolmanagement.activities.StaffPayRollActivity$1$1 */
        class C12941 implements VolleyCallback {
            C12941() {
            }

            public void onSuccess(String result) {
                StaffPayRollActivity.this.processJsonResponse(result);
            }
        }

        C12951() {
        }

        public void onSuccess(String result) {
            if (result != null) {
                StaffPayRollActivity.this.teacherid = result;
                if (StaffPayRollActivity.this.dlg.getSelectedItemPosition() > 0) {
                    StaffPayRollActivity.this.params.put("teacher_id", StaffPayRollActivity.this.teacherid);
                    StaffPayRollActivity.this.getJsonResponse(URLs.staffpayrolls, StaffPayRollActivity.this, new C12941());
                    return;
                }
                Toast.makeText(StaffPayRollActivity.this, "Please Select both Class and Teacher", 0).show();
            }
        }
    }

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_payrolls);
        setTitle("Staff Payrolls");
        KProgressHUD progressHUD = KProgressHUD.create(this);
        this.root = (TableLayout) findViewById(R.id.table_layout);
        this.dlg = (Spinner) findViewById(R.id.spnr_teacher);
//        getSpinnerData(this, URLs.all_teacher_codes, this.dlg, new C12951());
    }

    public void processJsonResponse(String response) {
        if (response != null && !response.equalsIgnoreCase("")) {
            this.responseParams.clear();
            try {
                JSONArray jsonArray = new JSONObject(response).getJSONArray("Response");
                Iterator<String> keysIterator = jsonArray.getJSONObject(0).keys();
                while (keysIterator.hasNext()) {
                    String keyStr = (String) keysIterator.next();
                    this.responseParams.put(keyStr, jsonArray.getJSONObject(0).getString(keyStr));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            findViewById(R.id.table_layout).setVisibility(0);
            loadData(this.root, this.responseParams);
        }
    }

    public void loadData(ViewGroup parent, HashMap<String, String> returnData) {
        if (returnData != null) {
            for (int i = 0; i < parent.getChildCount(); i++) {
                View child = parent.getChildAt(i);
                if (child instanceof ViewGroup) {
                    loadData((ViewGroup) child, returnData);
                } else if (parent.getChildAt(i) instanceof TextView) {
                    TextView et = (TextView) parent.getChildAt(i);
                    if (et.getId() > 0) {
                        if (returnData.containsKey(getResources().getResourceEntryName(et.getId()))) {
                            et.setText(WordUtils.capitalize((String) returnData.get(getResources().getResourceEntryName(et.getId()))));
                        } else {
                            et.setText("");
                        }
                    }
                }
            }
            return;
        }
        Toast.makeText(this, "No record found", 0).show();
    }
}
