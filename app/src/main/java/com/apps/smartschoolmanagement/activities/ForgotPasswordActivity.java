package com.apps.smartschoolmanagement.activities;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;
import com.apps.smartschoolmanagement.R;
import com.apps.smartschoolmanagement.utils.AnimationSlideUtil;
import com.apps.smartschoolmanagement.utils.ProfileInfo;
import com.apps.smartschoolmanagement.utils.URLs;
import com.apps.smartschoolmanagement.utils.basehelpers.BaseActivity;
import org.json.JSONException;
import org.json.JSONObject;

public class ForgotPasswordActivity extends BaseActivity {
    EditText email;

    /* renamed from: com.apps.smartschoolmanagement.activities.ForgotPasswordActivity$1 */
    class C12131 implements OnClickListener {

        /* renamed from: com.apps.smartschoolmanagement.activities.ForgotPasswordActivity$1$1 */
        class C12121 implements VolleyCallback {
            C12121() {
            }

            public void onSuccess(String result) {
                ForgotPasswordActivity.this.findViewById(R.id.btn_continue).setEnabled(true);
                try {
                    if (new JSONObject(result).getInt(NotificationCompat.CATEGORY_STATUS) == 1) {
                        ForgotPasswordActivity.this.startActivity(new Intent(ForgotPasswordActivity.this, ResetPasswordActivity.class));
                        AnimationSlideUtil.activityZoom(ForgotPasswordActivity.this);
                        return;
                    }
                    Toast.makeText(ForgotPasswordActivity.this, result, 0).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        C12131() {
        }

        public void onClick(View view) {
            if (ForgotPasswordActivity.this.isValid(ForgotPasswordActivity.this.email)) {
                ForgotPasswordActivity.this.params.put("email", ForgotPasswordActivity.this.email.getText().toString());
                ProfileInfo.getInstance().getLoginData().clear();
                ForgotPasswordActivity.this.findViewById(R.id.btn_continue).setEnabled(false);
                ForgotPasswordActivity.this.getJsonResponse(URLs.sendotp, ForgotPasswordActivity.this, new C12121());
            }
        }
    }

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        setTitle("Forgot Password");
        this.email = (EditText) findViewById(R.id.email);
        findViewById(R.id.btn_continue).setOnClickListener(new C12131());
    }
}
