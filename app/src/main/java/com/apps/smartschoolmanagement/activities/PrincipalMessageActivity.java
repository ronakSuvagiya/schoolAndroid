package com.apps.smartschoolmanagement.activities;

import android.os.Bundle;
import androidx.annotation.Nullable;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.apps.smartschoolmanagement.R;
import com.apps.smartschoolmanagement.activities.AddPhotoGalleryActivity.Utils;
import com.apps.smartschoolmanagement.utils.JsonClass;
import com.apps.smartschoolmanagement.utils.URLs;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PrincipalMessageActivity extends JsonClass {
    TextView name;
    TextView statement;

    /* renamed from: com.apps.smartschoolmanagement.activities.PrincipalMessageActivity$1 */
    class C12651 implements VolleyCallback {
        C12651() {
        }

        public void onSuccess(String result) {
            if (!TextUtils.isEmpty(result)) {
                PrincipalMessageActivity.this.processJsonResponse(result);
            }
        }
    }

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal_message);
        setTitle("Principal's Message");
        this.name = (TextView) findViewById(R.id.name);
        this.statement = (TextView) findViewById(R.id.statement);
       // getJsonResponse(URLs.principalstatement_view, this, new C12651());
        findViewById(R.id.layout_loading).setVisibility(8);
    }

    public void processJsonResponse(String result) {
        try {
            JSONArray jsonArray = new JSONObject(result).getJSONArray("Response");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                if (!jsonObject1.isNull(Utils.imageName)) {
                    this.name.setText(jsonObject1.getString(Utils.imageName));
                }
                if (!jsonObject1.isNull("statement")) {
                    this.statement.setText(jsonObject1.getString("statement").replace("<br>", ""));
                }
                if (!(jsonObject1.isNull("file_path") || isFinishing())) {
                    Glide.with(this).load(jsonObject1.getString("file_path")).override(150, 150).centerCrop().into((ImageView) findViewById(R.id.profilePic));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
