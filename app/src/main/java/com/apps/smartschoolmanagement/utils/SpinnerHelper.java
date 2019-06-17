package com.apps.smartschoolmanagement.utils;

import android.content.Context;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Spinner;
import com.apps.smartschoolmanagement.activities.AddPhotoGalleryActivity.Utils;
import com.apps.smartschoolmanagement.adapters.SpinnerrAdapter;

import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SpinnerHelper extends JsonClass {
    ArrayList<CodeValue> _CodeValues = new ArrayList();
    public Spinner _spinner;
    Context context;
    CodeValue mCodeValue;
    ArrayList<CodeValue> values = new ArrayList();
    View view;

    /* renamed from: com.apps.smartschoolmanagement.utils.SpinnerHelper$2 */
    class C14482 implements OnItemSelectedListener {
        C14482() {
        }

        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
            if (position > 0) {
                SpinnerHelper.this.mCodeValue = (CodeValue) SpinnerHelper.this._spinner.getItemAtPosition(position);
            }
        }

        public void onNothingSelected(AdapterView<?> adapterView) {
        }
    }

    /* renamed from: com.apps.smartschoolmanagement.utils.SpinnerHelper$4 */
    class C14504 implements OnItemSelectedListener {
        C14504() {
        }

        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
            if (position > 0) {
                SpinnerHelper.this.mCodeValue = (CodeValue) SpinnerHelper.this._spinner.getItemAtPosition(position);
            }
        }

        public void onNothingSelected(AdapterView<?> adapterView) {
        }
    }

    public interface SpinnerSelectionCallBack {
        void onSuccess(String str);
    }

    public SpinnerHelper(Context context, int resource, String url) {
        this.context = context;
        this.view = ((AppCompatActivity) context).getWindow().getDecorView();
        this._spinner = (Spinner) this.view.findViewById(resource);
        getData(url);
    }

    public SpinnerHelper(Spinner spinner, SpinnerrAdapter adapter, final VolleyCallback callback) {
        this._spinner = spinner;
        this._spinner.setAdapter(adapter);
        this._spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                if (position > 0) {
                    SpinnerHelper.this.mCodeValue = (CodeValue) SpinnerHelper.this._spinner.getItemAtPosition(position);
                    callback.onSuccess(SpinnerHelper.this.mCodeValue.getCodeID());
                }
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    public void getData(final String url) {
        if (ProfileInfo.getInstance().hasCodes(url)) {
            this._CodeValues = ProfileInfo.getInstance().getCodeValues(url);
//            this._spinner.setAdapter(new SpinnerrAdapter(this.context, 17367049, this._CodeValues));
            this._spinner.setOnItemSelectedListener(new C14482());
            return;
        }
        getJsonResponse(url, new VolleyCallbackJSONObject() {
            public void onSuccess(JSONObject result) {
                SpinnerHelper.this.processJSONResult(result, url);
            }
        });
    }

    public void processJSONResult(JSONObject jsonObject, String url) {
        try {
            CodeValue c = new CodeValue();
            c.setCodeValue(jsonObject.getString("title"));
            this.values.add(c);
            JSONArray jsonArray = jsonObject.getJSONArray("ResponseList");
            for (int i = 0; i < jsonArray.length(); i++) {
                String id = jsonArray.getJSONObject(i).optString("id").toString();
                String value = jsonArray.getJSONObject(i).optString(Utils.imageName).toString();
                CodeValue code = new CodeValue();
                code.setCodeID(id);
                code.setCodeValue(value);
                this.values.add(code);
            }
            if (jsonArray.length() > 0) {
                ProfileInfo.getInstance().addCodes(url, this.values);
            }
//            this._spinner.setAdapter(new SpinnerrAdapter(this.context, 17367049, this.values));
            this._spinner.setOnItemSelectedListener(new C14504());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getmCodeId() {
        return this.mCodeValue.getCodeID();
    }
}
